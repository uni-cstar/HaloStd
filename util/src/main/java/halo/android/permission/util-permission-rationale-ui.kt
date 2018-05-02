/**
 * Created by Lucio on 18/1/31.
 * 对于 RationalPermission请求前的友好交互提示
 */
package halo.android.permission

/**
 * RationalPermission 点击下一步的回调
 */
interface OnUnGrantedPermissionGuideViewClickListener {

    fun onUnGrantedPermissionGuideViewNextClick()

    fun onUnGrantedPermissionGuideViewCancelClick()
}

/**
 * 用于在请求权限前向用户说明原因
 */
interface UnGrantedPermissionGuideView {
    /**
     * @param permissions
     */
    fun show(permissions: List<Permission>, next: OnUnGrantedPermissionGuideViewClickListener)
}
