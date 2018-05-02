package halo.android.content

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Message
import halo.android.util.isNetworkConnected


/**
 * 网络改变广播接收器
 */
class NetworkChangedReceiver private constructor(val listener: OnNetworkChangedListener? = null)
    : BroadcastReceiver() {

    /**
     * 网络改变回调
     */
    interface OnNetworkChangedListener {
        fun onNetworkLost()
        fun onNetworkConnected()
    }

    private val handler = Handler {
        msg ->
        when (msg.what) {
            WHAT_NETWORK_CONNECTED -> listener?.onNetworkConnected()
            WHAT_NETWORK_LOST -> listener?.onNetworkLost()
        }
        true
    }

    override fun onReceive(ctx: Context, intent: Intent) {

        // 不是网络状态变化的不做处理
        if (intent.action != ConnectivityManager.CONNECTIVITY_ACTION)
            return

        val isConnected = isNetworkConnected(ctx)
        //移除原有的消息
        this.handler.removeMessages(WHAT_NETWORK_CONNECTED)
        this.handler.removeMessages(WHAT_NETWORK_LOST)
        val msg: Message = this.handler.obtainMessage(if (isConnected) WHAT_NETWORK_CONNECTED else WHAT_NETWORK_LOST)

        //延迟发送消息
        this.handler.sendMessageDelayed(msg, 500)
    }

    companion object {

        private const val WHAT_NETWORK_LOST = 404
        private const val WHAT_NETWORK_CONNECTED = 100

        /**
         * 注册广播

         * @param activity
         * *
         * @param listener
         * *
         * @return
         */
        fun registerReceiver(acty: Activity, listener: OnNetworkChangedListener): NetworkChangedReceiver {
            val netReceiver = NetworkChangedReceiver(listener)
            val mFilter = IntentFilter()
            mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            acty.registerReceiver(netReceiver, mFilter)
            return netReceiver
        }

        /**
         * 解除广播

         * @param activity
         * *
         * @param receiver
         */
        fun unregisterReceiver(activity: Activity, receiver: BroadcastReceiver) {
            activity.unregisterReceiver(receiver)
        }
    }
}
