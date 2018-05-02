/**
 * Created by Lucio on 18/2/1.
 */
package halo.stdlib.android.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.annotation.Nullable
import android.support.annotation.RequiresPermission
import halo.stdlib.android.content.startActivitySafely

/**
 * 回到系统主界面
 */
fun Context.startIntentForMainHome(){
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME)
    startActivitySafely(intent)
}

/**
 * 寻求打开网页的Intent
 */
fun Context.startIntentForUrl(url: String) {
    val uri = Uri.parse(url)
    startIntentForUri(uri)
}

/**
 * 使用系统浏览器打开网页
 */
fun Context.startIntentForUrlBySystemBrowser(url: String) {
    val uri = Uri.parse(url)
    try {
        val it = Intent(Intent.ACTION_VIEW)
        it.data = uri
        it.setClassName("com.android.browser", "com.android.browser.BrowserActivity")
        startActivitySafely(it)
    } catch (e: Exception) {
        e.printStackTrace()
        startIntentForUri(uri)
    }
}

/**
 * 寻求打开对应数据的Intent
 */
fun Context.startIntentForUri(uri: Uri) {
    val it = Intent(Intent.ACTION_VIEW)
    it.data = uri
    startActivitySafely(it)
}

/**
 * 调用拨号界面
 * @param tel 电话号码（可不传）
 */
@JvmOverloads
fun Context.startIntentForDial(@Nullable tel: String? = null) {
    val it = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel.orEmpty()))
    startActivitySafely(it)
}

/**
 * 拨打电话
 * @param tel 电话好吗
 * @RequiresPermission 需要危险权限[Manifest.permission.CALL_PHONE]
 */
@RequiresPermission(value = Manifest.permission.CALL_PHONE)
fun Context.startIntentForCall(tel: String) {
    val it = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel))
    startActivitySafely(it)
}

/**
 * 调用短信编辑界面
 * @param context
 * @param tel          电话号码
 * @param extraContent 预设的短信内容
 */
fun Context.startIntentForSMS(tel: String, extraContent: String = "") {
    val it = Intent(Intent.ACTION_VIEW)
            .putExtra("address", tel)
            .putExtra("sms_body", extraContent)
            .setType("vnd.android-dir/mms-sms")
    startActivitySafely(it)
}

/**
 * 发邮件
 * @param addrs   邮箱地址数组
 * @param subject      邮件主题
 * @param extraContent 预设的邮件内容
 */
fun Context.startIntentForMail(addrs: Array<String>, subject: String = "", extraContent: String = "") {
    val it = Intent(Intent.ACTION_SEND)
    // 设置对方邮件地址
    it.putExtra(Intent.EXTRA_EMAIL, addrs)
    // 设置标题内容
    if (!subject.isEmpty())
        it.putExtra(Intent.EXTRA_SUBJECT, subject)
    // 设置邮件文本内容
    if (!extraContent.isEmpty())
        it.putExtra(Intent.EXTRA_TEXT, extraContent)
    startActivitySafely(it)
}

/**
 * 调用手机设置界面
 * 具体设置见 [android.provider.Settings]的Action_xxxx定义
 */
fun Context.startIntentForSettings() {
    startIntentForSettings(android.provider.Settings.ACTION_SETTINGS)
}


/**
 * 查看应用设置信息
 */
fun Context.startIntentForAppDetailSetting(pkgName: String) {
    val uri = Uri.fromParts("package", pkgName, null)
    startIntentForSettings(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
}

/**
 * 运行到具体设置界面
 * @param action
 * @see [android.provider.Settings]的Action_xxxx定义
 */
private inline fun Context.startIntentForSettings(action: String, data: Uri? = null) {
    val it = Intent(action)
    if (data != null) {
        it.data = data
    }
    startActivitySafely(it)
}

