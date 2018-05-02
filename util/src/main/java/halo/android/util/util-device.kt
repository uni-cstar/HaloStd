package halo.android.util

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.ResultReceiver
import android.support.annotation.RequiresPermission
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import halo.android.content.connectivityManager
import halo.android.content.inputMethodManager
import halo.android.content.powerManager
import halo.android.content.telephonyManager
import halo.kotlin.Caveat
import java.io.UnsupportedEncodingException
import java.security.NoSuchAlgorithmException

/**
 * Created by Lucio on 18/1/22.
 */


/**
 * 网络是否已连接
 */
@RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
fun isNetworkConnected(ctx: Context): Boolean {

    val networkInfo = ctx.connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

/**
 *  WIFI是否已连接
 */
@RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
fun isWifiConnected(ctx: Context): Boolean {
    return isNetworkConnected(ctx, ConnectivityManager.TYPE_WIFI)
}

/**
 * 指定类型网络是否已连接
 * @param type
 * @see [android.net.ConnectivityManager.TYPE_WIFI] wifi type
 * @see [android.net.ConnectivityManager.TYPE_MOBILE] mobile type
 * @see [android.net.ConnectivityManager] type value
 */
@RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
fun isNetworkConnected(ctx: Context, type: Int): Boolean {
    val manager = ctx.connectivityManager
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        val networkInfo = manager.getNetworkInfo(type)
        return networkInfo != null && networkInfo.isConnected
    } else {
        val networks = manager.allNetworks
        networks?.forEach {
            val networkInfo = ctx.connectivityManager.getNetworkInfo(it)
            if (networkInfo != null && networkInfo.type == type && networkInfo.isConnected)
                return true
        }
        return false
    }
}


/**
 * 是否亮屏
 * @param ctx
 * @return true:亮屏 false:锁屏
 * 屏幕亮屏和锁屏的时候，系统会发出对应通知
 * @see android.content.Intent#ACTION_SCREEN_ON
 * @see android.content.Intent#ACTION_SCREEN_OFF
 */
fun isScreenOn(ctx: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH) {
        return ctx.powerManager.isScreenOn
    } else {
        return ctx.powerManager.isInteractive
    }
}

/**
 * 软键盘是否打开
 * @param context
 * @param target the given view whether is the currently active view
 * @return 如果target不为空，则判断当前软键盘是否打开，并且激活软键盘的view是对应的target
 */
@Caveat("此方法不是很有效，经常不管软键盘显示与否，都是返回的true")
@Deprecated("")
fun isSoftInputShow(ctx: Context, target: View? = null): Boolean {
    if (target == null) {
        return ctx.inputMethodManager.isActive
    } else {
        return ctx.inputMethodManager.isActive(target)
    }
}

/**
 * 切换软键盘
 */
fun toggleSoftInput(ctx: Context) {
    ctx.inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
}

/**
 * 强制隐藏输入法
 * @param context
 * @param receiver
 */
@Caveat("receiver可能造成内存泄漏，因为它是一个长生命周期对象，因此receiver最好是使用弱引用，即WeakReference。" +
        "原文：Caveat: ResultReceiver instance passed to this method can be a long-lived object, because it may not be garbage-collected until all the corresponding ResultReceiver objects transferred to different processes get garbage-collected. Follow the general patterns to avoid memory leaks in Android. Consider to use WeakReference so that application logic objects such as Activity and Context can be garbage collected regardless of the lifetime of ResultReceiver.")
fun hideSoftInput(ctx: Context, receiver: ResultReceiver? = null) {
    if (ctx !is Activity)
        return
    val view = ctx.currentFocus ?: return
    ctx.inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0, receiver)
}

/**
 * 隐藏输入法
 * @param view 当前激活软键盘的view，通常是EditText
 */
fun hideSoftInput(view: View) {
    view.context.inputMethodManager.hideSoftInputFromInputMethod(view.windowToken, 0)
}

/**
 * 强制显示输入法
 * @param view 用于关联输入法的view
 */
fun showSoftInput(ctx: Context, view: View) {
    view.requestFocus()//先让view获取焦点
    ctx.inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

/**
 * 对话框显示时总是显示输入法
 */
fun Dialog.showSoftInputAlways(): Dialog {
    showSoftInputAlwaysForDialog(this)
    return this
}

/**
 * 对话框显示时,总是显示输入法
 */
fun showSoftInputAlwaysForDialog(dialog: Dialog) {
    dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}

/**
 * 对话框显示时,总是隐藏输入法
 */
fun hideSoftInputAlwaysForDialog(dialog: Dialog) {
    dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

//TODO 关于设备唯一ID https://developer.android.com/training/articles/user-data-ids.html
/**
 * 获取标志手机设备的唯一UUID(自定义方法，并不是具有权威性)
 * DeviceId + androidId + sim卡序列号 + SubscriberId + Build.SERIAL
 * @param ctx
 * @return
 * @throws NoSuchAlgorithmException
 * @throws UnsupportedEncodingException
 */
@RequiresPermission(value = Manifest.permission.READ_PHONE_STATE)
@Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
fun getUUID(ctx: Context): String {
    val tm = ctx.telephonyManager
    val sb = StringBuilder()

    // 设备ID
    val deviceId = tm.deviceId
    if (!deviceId.isNullOrEmpty())
        sb.append(deviceId)

    // androidId
    val androidId = android.provider.Settings.Secure.getString(ctx.contentResolver, android.provider.Settings.Secure.ANDROID_ID)
    if (!androidId.isNullOrEmpty())
        sb.append(androidId)

    // sim卡序列号
    val simSerialNumber = tm.simSerialNumber
    if (!simSerialNumber.isNullOrEmpty())
        sb.append(simSerialNumber)

    val subScriberID = tm.subscriberId
    if (!subScriberID.isNullOrEmpty())
        sb.append(subScriberID)

    val serialNo = Build.SERIAL
    if (!serialNo.isNullOrEmpty())
        sb.append(serialNo)
    return sb.toString()
}