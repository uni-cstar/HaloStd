package halo.android.permission

import android.support.annotation.Nullable

/**
 * 权限请求结果回调
 */
interface LitePermissionResultListener {
    /**
     * 权限结果请求返回
     * @param allGranted true:所有权限都被允许，此时其他参数为null
     * @param granted 当[allGranted]为false，[granted]返回已经被允许的权限集合
     * @param ungranted 当[allGranted]为false，[ungranted]返回未被允许的权限集合
     */
    fun onLitePermissionRequestResult(allGranted: Boolean,
                                      @Nullable granted: List<Permission>?,
                                      @Nullable ungranted: List<Permission>?)
}