package halo.android.permission

import android.content.Context

/**
 * 权限的基本行为
 */
interface LitePermissionAction {

    val ctx: Context


    /**
     * 请求权限（直接请求权限，不管权限是否允许，禁止或不可用）
     * @param listener 回调，通知请求结果
     * @param permissions 权限结果
     */
    fun requestPermissions(listener: LitePermissionResultListener, vararg permissions: Permission)

    /**
     * 请求权限（直接请求权限，不管权限是否允许，禁止或不可用）
     * @param listener 回调，通知请求结果
     * @param permissions 权限结果
     */
    fun requestPermissions(listener: LitePermissionResultListener, vararg permissions: String)

    /**
     * 用户自定义引导view，用于引导用户授权 未被授权的权限
     */
    var customUnGrantedPermissionGuideView: UnGrantedPermissionGuideView?


}