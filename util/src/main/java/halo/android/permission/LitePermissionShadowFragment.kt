package halo.android.permission

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import halo.android.util.logD

/**
 * 承担权限的真实请求和回调处理
 */
open class LitePermissionShadowFragment : Fragment() {

    private val TAG = "LitePermissionShadowFragment"

    private val REQUEST_CODE_FRAGMENT_PERMISSIONS = 400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    private var mListener: LitePermissionResultListener? = null
    private var mPermissions: List<Permission>? = null

    private val lock: Any = Any()

    /**
     * 请求权限
     */
    internal fun requestPermissions(permissions: List<Permission>, listener: LitePermissionResultListener) {

        synchronized(lock) {
            mListener = listener
            mPermissions = permissions

            val needRequest = permissions
                    .filter {
                        !it.granted
                    }
                    .map {
                        it.value
                    }.toTypedArray()
            requestPermissions(needRequest, REQUEST_CODE_FRAGMENT_PERMISSIONS)
        }
    }

    /**
     * 权限请求结果返回
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != REQUEST_CODE_FRAGMENT_PERMISSIONS) return

        synchronized(lock) {
            permissions.forEachIndexed { index, permission ->
                val granted = grantResults[index] == PackageManager.PERMISSION_GRANTED
                if (granted) {
                    val item = mPermissions?.find {
                        it.value == permission
                    }
                    item?.let {
                        it.granted = granted
                    }
                }
            }

            //回调处理结果
            if (mPermissions != null && mListener != null) {
                notifyRequestResult(mPermissions!!, mListener!!)
                mListener = null
                mPermissions = null
            }
        }
    }

    /**
     * 将[permissions]拆分并通知客户端权限处理请求结果
     *
     * @param permissions 请求的权限结果
     * @param listener 回调
     */
    internal fun notifyRequestResult(permissions: List<Permission>, listener: LitePermissionResultListener) {
        "通知客户端权限请求结果".logD(TAG)
        //没有需要再次请求的权限
        var allGranted: Boolean = true
        val granted: MutableList<Permission> = mutableListOf()
        val ungranted: MutableList<Permission> = mutableListOf()
        permissions.forEach {
            if (it.granted) {
                "被允许的权限 ${it.value}".logD(TAG)
                granted.add(it)
            } else {
                allGranted = false
                ungranted.add(it)
            }
        }
        listener.onLitePermissionRequestResult(allGranted, granted, ungranted)
    }

}