package halo.stdlib.android.content

import android.app.Activity

/**
 * Created by Lucio on 17/8/17.
 * 仿Activity堆栈管理
 */
@Deprecated("使用ActivityLifecycleCallbacks的实现会更好",replaceWith = ReplaceWith("halo.stdlib.android.content.AppState"))
object ActivityStackCompat {

    val lockObj = Any()

    val activityList: MutableList<Activity> by lazy {
        mutableListOf<Activity>()
    }

    fun add(acty: Activity) {
        synchronized(lockObj) {
            activityList.add(acty)
        }
    }

    fun remove(acty: Activity) {
        synchronized(lockObj) {
            activityList.remove(acty)
        }
    }

    fun getTop(): Activity? {
        synchronized(lockObj) {
            return activityList.lastOrNull()
        }
    }

    fun removeAll() {
        synchronized(lockObj) {
            activityList.clear()
        }
    }

    fun finishAll() {
        synchronized(lockObj) {
            activityList.forEach {
                it.finish()
            }
            activityList.clear()
        }

    }
}