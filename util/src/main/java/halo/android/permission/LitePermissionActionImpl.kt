package halo.android.permission

import android.os.Build
import android.support.v4.app.FragmentActivity
import halo.android.util.logD
import halo.android.util.logE
import halo.android.util.logI
import halo.android.util.logW

/**
 * 用于activity请求权限处理
 */
internal class LitePermissionActionImpl internal constructor(override val ctx: FragmentActivity,
                                                             override var customUnGrantedPermissionGuideView: UnGrantedPermissionGuideView? = null)
    : LitePermissionAction {

    private val TAG = "LitePermissionActionImpl"

    private val TAG_FRAGMENT_PERMISSIONS = "LitePermissionShadowFragment"

    //是否存在请求
    private var isExistsRequest = false

    //预先添加权限fragment
    private val permissionFragment by lazy {
        getRxPermissionsFragment(ctx)
    }

    //用户回调
    private var customListener: LitePermissionResultListener? = null

    private val mRationalViewConnection: UnGrantedPermissionGuideViewConnection by lazy {
        UnGrantedPermissionGuideViewConnection()
    }

    //包装回调，用于更改标志
    private val customListenerDispatcher = object : LitePermissionResultListener {
        //权限请求的最终出口方法
        override fun onLitePermissionRequestResult(allGranted: Boolean, granted: List<Permission>?, ungranted: List<Permission>?) {
            isExistsRequest = false
            customListener?.onLitePermissionRequestResult(allGranted, granted, ungranted)
            customListener = null
        }

        /**
         * 请求取消
         */
        fun onLitePermissionRequestCanceled() {
            isExistsRequest = false
            customListener = null
        }
    }

    private fun getRxPermissionsFragment(activity: FragmentActivity): LitePermissionShadowFragment {
        val fm = activity.supportFragmentManager
        var permissionFragment = fm.findFragmentByTag(TAG_FRAGMENT_PERMISSIONS) as? LitePermissionShadowFragment
        if (permissionFragment == null) {
            permissionFragment = LitePermissionShadowFragment()
            fm.beginTransaction()
                    .add(permissionFragment, TAG_FRAGMENT_PERMISSIONS)
                    .commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        return permissionFragment
    }

    @Synchronized
    override fun requestPermissions(listener: LitePermissionResultListener, vararg permissions: String) {
        val pBoxes = permissions.map {
            LitePermission.createPermission(it)
        }
        requestPermissions(listener, *(pBoxes.toTypedArray()))
    }

    @Synchronized
    override fun requestPermissions(listener: LitePermissionResultListener, vararg permissions: Permission) {
        if (permissions.isEmpty()) {
            "at least one input permission".logE(TAG)
            listener.onLitePermissionRequestResult(true, null, null)
            return
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            "build version is smaller than M(23)".logI(TAG)
            listener.onLitePermissionRequestResult(true, null, null)
            return
        }

        if (isExistsRequest) {
            "Can request only one set of permissions at a time".logW(TAG)
            return
        }

        isExistsRequest = true
        customListener = listener

        //权限去重
        val distinctPermission = permissions.distinctBy {
            it.value
        }

        //是否所有权限都已被允许
        var isAllGranted = true

        distinctPermission.forEach {
            it.granted = LitePermission.isPermissionGranted(ctx, it.value)
            //权限没有被允许
            if (!it.granted) {
                isAllGranted = false
            }

            "permission: name=${it.value} granted=${it.granted}".logD(TAG)
        }
        if (isAllGranted) {
            "所有权限已被允许，直接回调".logD(TAG)
            customListenerDispatcher.onLitePermissionRequestResult(true, null, null)
        } else {
            mRationalViewConnection.dispatch(distinctPermission)
        }
    }

    /**
     * 真实的发送权限请求
     */
    private fun requestPermissionsReal(permissions: List<Permission>) {
        "发送请求".logD(TAG)
        permissionFragment.requestPermissions(permissions, customListenerDispatcher)
    }

    /**
     * 连接 UnGrantedPermissionGuideView 与回调之间的关系
     */
    private inner class UnGrantedPermissionGuideViewConnection : OnUnGrantedPermissionGuideViewClickListener {

        var permissionsTemp: List<Permission>? = null

        fun dispatch(permissions: List<Permission>) {
            if (customUnGrantedPermissionGuideView != null) {
                permissionsTemp = permissions
                customUnGrantedPermissionGuideView?.let {

                    val requestValue = permissions.filter {
                        !it.granted
                    }

                    if (requestValue.isEmpty())
                        throw IllegalArgumentException("没有未被权限的权限需要处理")

                    "显示GuideView".logD(TAG)
                    it.show(requestValue, this)
                }
            } else {
                requestPermissionsReal(permissions)
            }
        }

        override fun onUnGrantedPermissionGuideViewNextClick() {
            permissionsTemp?.let {
                requestPermissionsReal(it)
            }
        }

        override fun onUnGrantedPermissionGuideViewCancelClick() {
            customListenerDispatcher.onLitePermissionRequestCanceled()
        }
    }

}