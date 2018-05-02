package halo.kotlin

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

/**
 * Created by Lucio on 17/12/1.
 */

fun <T> bg(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> T)
        = async(CommonPool, start, block = block)

fun <T> bgLazy(block: suspend CoroutineScope.() -> T)
        = bg(CoroutineStart.LAZY, block)

fun bgLaunch(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit)
        = launch(CommonPool, start, block = block)

fun bgLaunchLazy(block: suspend CoroutineScope.() -> Unit)
        = bgLaunch(CoroutineStart.LAZY, block)

/**
 * UI线程协程 无返回值
 */
fun ui(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit)
        = launch(UI, start, block)

/**
 * UI线程协程 延迟执行 无返回值
 */
fun uiLazy(block: suspend CoroutineScope.() -> Unit)
        = ui(CoroutineStart.LAZY, block)

/**
 * UI线程协程 有返回值
 */
fun <T> uiDeferred(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> T)
        = async(UI, start, block)






