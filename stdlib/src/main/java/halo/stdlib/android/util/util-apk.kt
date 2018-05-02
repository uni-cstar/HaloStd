package halo.stdlib.android.util

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import halo.stdlib.kotlin.BestPerformance
import halo.stdlib.kotlin.util.isNullOrEmpty
import halo.stdlib.android.content.activityManager
import halo.stdlib.android.content.startActivitySafely
import java.io.File
import java.io.FileNotFoundException


/**
 * 当前应用是否处于前台
 * @Note 据百度上某些网友说在某些机型上，始终返回的是ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
 * @link [halo.android.content.AppState] 在api14上建议使用此方法
 */
@BestPerformance("Note: this method is only intended for debugging or building a user-facing process management UI.")
fun isAppForeground(ctx: Context): Boolean {
    val processes = ctx.activityManager.runningAppProcesses
    val pkgName = ctx.packageName
    processes?.forEach {
        if (it.processName == pkgName)
            return true
    }
    return false
}

/**
 * 判断指定服务是否正在后台运行
 * @param
 */
fun Context.isServiceRunning(serviceClass: Class<out Service>): Boolean {
    return isServiceRunning(serviceClass.name)
}

/**
 * 判断指定className的service是否正在后台运行
 * @param className 服务的class name，可以使用Class.getName获取
 * @return
 */
fun Context.isServiceRunning(className: String): Boolean {
    val serviceList = activityManager.getRunningServices(100)
    if (serviceList.isNullOrEmpty())
        return false

    serviceList?.forEach {
        if (it.service.className == className)
            return true
    }
    return false
}

/**
 * 是否安装了指定的软件
 * @param pkgName 应用包名
 * @return
 */
fun Context.isAppInstalled(pkgName: String): Boolean {
    val pkgInfos = packageManager.getInstalledPackages(0)// 获取所有已安装程序的包信息
    pkgInfos?.forEach {
        if (it.packageName == pkgName)
            return true
    }
    return false
}

/**
 * 安装apk
 * @param path apk文件路径
 */
fun Context.installApp(path: String) {
    val file = File(path)
    if (file.isDirectory || !file.exists())
        throw FileNotFoundException("the file is not exists or it is a directory.")
    installApp(Uri.fromFile(file))
}

/**
 * 安装APK
 * @param uri apk文件对应uri
 */
fun Context.installApp(uri: Uri) {
    val it = Intent(Intent.ACTION_VIEW)
    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    it.setDataAndType(uri, MIME_TYPE_APK)
    startActivitySafely(it)
}

/**
 * 卸载一个app
 * @param packageName 包名
 */
fun Context.uninstallApp(packageName: String) {
    val uri = Uri.fromParts("package", packageName, null)
    val intent = Intent(Intent.ACTION_DELETE, uri)
    startActivitySafely(intent)
}

/**
 * 查看应用设置信息
 */
fun Context.viewAppSettings() = startIntentForAppDetailSetting(packageName)

/**
 * 获取清单文件中指定的meta-data
 * @param key     MetaData对应的Key
 * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
 */
inline fun Context.getMetaData(key: String): String? {
    val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
    return applicationInfo?.metaData?.getString(key)
}

/**
 * 获得渠道号
 * @param channelKey 指定渠道Key
 * @return
 */
inline fun Context.getChannelNo(channelKey: String): String? = getMetaData(channelKey)

/**
 * 获取程序版本名称
 * @return
 */
inline fun Context.getVersionName(): String {
    val packInfo = packageManager.getPackageInfo(packageName, 0)//0代表是获取版本信息
    return packInfo.versionName
}

/**
 * 获取版本号
 * @return
 */
inline fun Context.getVersionCode(): Int {
    val packInfo = packageManager.getPackageInfo(packageName, 0)
    return packInfo.versionCode
}

/**
 * 获取程序第一次安装时间
 */
inline fun Context.getFirstInstallTime(): Long {
    val packInfo = packageManager.getPackageInfo(packageName, 0)//0代表是获取版本信息
    return packInfo.firstInstallTime
}

/**
 * 获取程序上次更新时间
 */
inline fun Context.getLastUpdateTime(): Long {
    val packInfo = packageManager.getPackageInfo(packageName, 0)//0代表是获取版本信息
    return packInfo.lastUpdateTime
}

/**
 * 获取目标版本
 */
inline fun getTargetSdkVersion(ctx: Context): Int {
    val packInfo = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
    return packInfo.applicationInfo.targetSdkVersion
}
