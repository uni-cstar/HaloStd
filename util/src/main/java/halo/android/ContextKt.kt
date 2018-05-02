package halo.android

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import halo.android.content.res.statusBarHeight

/**
 * Created by Lucio on 17/11/23.
 */

/**
 * 检查是否能响应Intent,避免直接start引起app崩溃
 */
@Throws(ActivityNotFoundException::class)
fun Context.checkResolveActivity(it: Intent) {
    it.resolveActivity(packageManager) ?: throw ActivityNotFoundException("no activity can handle this intent $it")
}

/**
 * 运行intent之前，检查intent是否会被响应，避免app直接崩溃
 * @throws ActivityNotFoundException intent不能被响应
 */
@Throws(ActivityNotFoundException::class)
fun Context.startActivitySafely(it: Intent) {
    checkResolveActivity(it)
    if (this !is Activity) {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(it)
}

/**
 * 状态栏高度
 */
inline val Context.statusBarHeight: Int
    get() = this.resources.statusBarHeight
