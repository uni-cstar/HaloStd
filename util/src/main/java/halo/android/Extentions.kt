package halo.android

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat

/**
 * Created by Lucio on 17/12/10.
 */
fun Throwable.alertMessage(ctx: Context) {
    ctx.alert(Appcompat, this.message.orEmpty()).show()
}

inline fun <T> Fragment.TryAndAlert(printStack: Boolean = false, block: () -> T) = try {
    block()
} catch (e: Throwable) {
    if (printStack) {
        e.printStackTrace()
    }
    e.alertMessage(this.activity)
}


inline fun <T> Activity.TryAndAlert(printStack: Boolean = false, block: () -> T) = try {
    block()
} catch (e: Throwable) {
    if (printStack) {
        e.printStackTrace()
    }
    e.alertMessage(this)
}