package halo.android.util

import android.os.Build
import android.util.Log

/**
 * Created by Lucio on 18/1/25.
 */

/**
 * 编译版本是否大于指定版本
 */
inline fun isBuildVersionAbove(version: Int): Boolean = Build.VERSION.SDK_INT > version

/**
 * 编译版本是否大于或等于指定版本
 */
inline fun isBuildVersionAboveOrEqual(version: Int): Boolean = Build.VERSION.SDK_INT >= version


fun String.logD(tag: String = "") {
    Log.d(tag, this)
}

fun String.logI(tag: String = "") {
    Log.i(tag, this)
}

fun String.logW(tag: String = "") {
    Log.w(tag, this)
}

fun String.logE(tag: String = "") {
    Log.e(tag, this)
}

