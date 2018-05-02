/**
 * Created by Lucio on 17/11/30.
 */

package halo.kotlin.extentions

/**
 * @param printStack 异常时，是否调用printStackTrace方法
 */
inline fun <T> Try(printStack: Boolean = false, block: () -> T) = try {
    block()
} catch (e: Throwable) {
    if (printStack) {
        e.printStackTrace()
    }
    null
}

inline fun <T> Try(block: () -> T,
                   noinline exception: ((Throwable) -> T)? = null,
                   noinline finally: (() -> Unit)? = null)
        = try {
    block()
} catch (e: Throwable) {
    exception?.invoke(e)
} finally {
    finally?.invoke()
}
