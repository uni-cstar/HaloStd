package halo.kotlin.udp

import android.util.Log
import halo.kotlin.orDefault
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress

/**
 * Created by Lucio on 17/7/12.
 * UDP读数据
 */
class UDPReader {

    private var mThread: ReadThread? = null

    fun startRead(port: Int, bufSize: Int = 1024, listener: UDPReadListener) {
        stopRead()
        mThread = ReadThread(port, bufSize, listener)
        mThread?.readFlag = true
        mThread?.start()
    }

    /**
     * 停止监听
     */
    fun stopRead() {
        mThread?.stopRead()
        mThread = null
    }

    private class ReadThread(val port: Int, val bufSize: Int = 1024, val listener: UDPReadListener) : Thread() {

        private var datagramSocket: DatagramSocket? = null

        var readFlag = false

        override fun run() {
            super.run()
            startListening()
        }


        /**
         * 开始监听
         */
        fun startListening() {
            try {
                try {
                    datagramSocket = DatagramSocket(null)
                    datagramSocket!!.reuseAddress = true
                    datagramSocket!!.bind(InetSocketAddress(port))
                } catch (ex: Exception) {
                    datagramSocket = DatagramSocket()
                }

                datagramSocket?.broadcast = true
                // 接收的字节大小，客户端发送的数据不能超过这个大小
                val message = ByteArray(bufSize)
                val datagramPacket = DatagramPacket(message, message.size)

                listener.onUDPReadListening()
                while (readFlag) {
                    // 准备接收数据
                    datagramSocket?.receive(datagramPacket)
                    val strMsg = String(datagramPacket.data).trim { it <= ' ' }
                    if (strMsg.isEmpty())
                        return
                    Log.d("udp_data", strMsg)
                    listener.onUDPReadMessage(strMsg)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                stopRead()
                listener.onUDPReadFinish()
            }
        }

        fun stopRead() {
            readFlag = false
            interrupt()
            if (!datagramSocket?.isClosed.orDefault(true))
                datagramSocket?.close()
        }

    }

    interface UDPReadListener {
        fun onUDPReadListening()
        fun onUDPReadMessage(content: String)
        fun onUDPReadFinish()
    }
}
