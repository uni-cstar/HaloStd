package halo.android.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.FragmentActivity
import android.support.v4.content.PermissionChecker
import halo.android.util.ApkUtil

/**
 * 权限入口类
 */
object LitePermission {

    /**
     * 创建权限处理帮助类
     */
    fun create(ctx: FragmentActivity): LitePermissionAction {
        return LitePermissionActionImpl(ctx)
    }

    /**
     * 创建权限
     */
    fun createPermission(value: String, granted: Boolean = false): Permission {
        return Permission(value, granted)
    }

    /**
     * 是否需要进行权限处理（目标版本是否是23及以上）
     */
    fun isPermissionTargetVersion(ctx: Context): Boolean {
        return ApkUtil.getTargetSdkVersion(ctx) >= Build.VERSION_CODES.M
    }

    /**
     * 权限是否被允许
     * ps:因为方法可能被很多地方调用，因此不用内联，否则可能增加编译代码
     */
    fun isPermissionGranted(ctx: Context, permission: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // targetSdkVersion >= Android M, we can use Context#checkSelfPermission
            return if (isPermissionTargetVersion(ctx)) {
                ctx.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                PermissionChecker.checkSelfPermission(ctx, permission) == PermissionChecker.PERMISSION_GRANTED
            }
        }
        // For Android < Android M, self permissions are always granted.
        return true
    }

    /**
     * [ps: 创建此方法主要是为了对Activity的shouldShowRequestPermissionRationale做更好的说明]
     * 如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
     * 如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
     * 如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
     * @param permission 权限
     * @exception IllegalArgumentException 如果传递的permission是当前设备无法识别的权限则会抛出 java.lang.IllegalArgumentException: Unknown permission: [permission]
     */
    @Throws(IllegalArgumentException::class)
    fun shouldShowRequestPermissionRationale(activity: Activity, permission: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.shouldShowRequestPermissionRationale(permission)
        }
        // For Android < Android M, you should not show UI with rationale for requesting a permission
        return false
    }

    /**
     * Checks whether a particular permissions has been unable for a
     * package by policy. Typically the device owner or the profile owner
     * may apply such a policy. The user cannot grant policy unable
     * permissions, hence the only way for an app to get such a permission
     * is by a policy change.
     *
     * @return 权限是否被政策限制: Whether the permission is restricted by policy.
     */
    @Deprecated(message = "不知道具体使用场景")
    fun isPermissionRevoked(ctx: Context,permission: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ctx.applicationContext.packageManager.isPermissionRevokedByPolicy(permission, ctx.packageName)
        }
        // For Android < Android M, 默认权限没有被政策限制
        return false
    }

}