package halo.android.util

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import halo.android.content.activityManager
import halo.kotlin.BestPerformance
import halo.kotlin.isNullOrEmpty
import java.io.File
import java.io.FileNotFoundException


/**
 * Created by Lucio on 17/12/19.
 */
object ApkUtil {

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
    fun isServiceRunning(ctx: Context, serviceClass: Class<out Service>): Boolean {
        return isServiceRunning(ctx, serviceClass.name)
    }

    /**
     * 判断指定className的service是否正在后台运行
     * @param ctx
     * @param className 服务的class name，可以使用Class.getName获取
     * @return
     */
    fun isServiceRunning(ctx: Context, className: String): Boolean {
        val serviceList = ctx.activityManager.getRunningServices(100)
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
     * @param ctx
     * @param pkgName 应用包名
     * @return
     */
    fun isInstalled(ctx: Context, pkgName: String): Boolean {
        val pkgInfos = ctx.packageManager.getInstalledPackages(0)// 获取所有已安装程序的包信息
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
    fun install(ctx: Context, path: String) {
        val file = File(path)
        if (file.isDirectory || !file.exists())
            throw FileNotFoundException("the file is not exists or it is a directory.")
        install(ctx, Uri.fromFile(file))
    }

    /**
     * 安装APK
     * @param ctx
     * @param uri apk文件对应uri
     */
    fun install(ctx: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(uri, MIME_TYPE_APK)
        ctx.startActivity(intent)
    }

    /**
     * 卸载一个app
     * @param ctx
     * @param packageName 包名
     */
    fun uninstall(ctx: Context, packageName: String) {
        val uri = Uri.fromParts("package", packageName, null)
        val intent = Intent(Intent.ACTION_DELETE, uri)
        ctx.startActivity(intent)
    }

    /**
     * 查看应用设置信息
     */
    fun viewAppSettings(ctx: Context) {
        ctx.startIntentForAppDetailSetting(ctx.packageName)
    }

    /**
     * 获取清单文件中指定的meta-data
     * @param context
     * @param key     MetaData对应的Key
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    fun getMetaData(ctx: Context, key: String): String? {
        val applicationInfo = ctx.packageManager.getApplicationInfo(ctx.packageName, PackageManager.GET_META_DATA)
        return applicationInfo?.metaData?.getString(key)
    }

    /**
     * 获得渠道号
     * @param context
     * @param channelKey 指定渠道Key
     * @return
     */
    fun getChannelNo(context: Context, channelKey: String): String? {
        return getMetaData(context, channelKey)
    }

    /**
     * 获取程序版本名称
     * @param context
     * @return
     */
    fun getVersionName(ctx: Context): String {
        val packInfo = ctx.packageManager.getPackageInfo(ctx.packageName, 0)//0代表是获取版本信息
        return packInfo.versionName
    }

    /**
     * 获取程序第一次安装时间
     */
    fun getFirstInstallTime(ctx: Context): Long {
        val packInfo = ctx.packageManager.getPackageInfo(ctx.packageName, 0)//0代表是获取版本信息
        return packInfo.firstInstallTime
    }

    /**
     * 获取程序上次更新时间
     */
    fun getLastUpdateTime(ctx: Context): Long {
        val packInfo = ctx.packageManager.getPackageInfo(ctx.packageName, 0)//0代表是获取版本信息
        return packInfo.lastUpdateTime
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    fun getVersionCode(ctx: Context): Int {
        val packInfo = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
        return packInfo.versionCode
    }

    /**
     * 获取目标版本
     */
    fun getTargetSdkVersion(ctx: Context): Int {
        val packInfo = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
        return packInfo.applicationInfo.targetSdkVersion
    }

}