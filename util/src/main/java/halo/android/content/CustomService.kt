package halo.android.content

import android.app.ActivityManager
import android.content.Context
import android.os.PowerManager
import android.telephony.TelephonyManager
import android.view.inputmethod.InputMethodManager

/**
 * Created by Lucio on 17/11/23.
 * android 常规服务
 */

/**
 * 震动服务
 */
//inline val Context.vibrator: android.os.Vibrator
//    get() = getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator

/**
 * 布局加载服务LayoutInflater
 */
inline val Context.layoutInflater: android.view.LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater

/**
 * 网络服务ConnectivityManager
 */
inline val Context.connectivityManager: android.net.ConnectivityManager
    get() = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager

/**
 * TelephonyManager
 */
inline val Context.telephonyManager: TelephonyManager
    get() = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

/**
 * ActivityManager
 */
inline val Context.activityManager: ActivityManager
    get() = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

/**
 * PowerManager
 */
inline val Context.powerManager: PowerManager
    get() = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager


/**
 * InputMethodManager
 */
inline val Context.inputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

