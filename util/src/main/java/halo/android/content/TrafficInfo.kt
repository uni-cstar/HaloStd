package halo.android.content

import android.content.Context
import android.net.TrafficStats
import android.util.Log

import java.math.BigDecimal
import java.util.Timer
import java.util.TimerTask

/**
 * Created by Lucio on 17/2/22.
 * 获取流量上传or下载速度信息
 * 计算规则：TrafficStats记录了对应应用或整个设备在当前时间总共上传或下载的数据总量，
 * 因此可以通过固定间隔时间读取记录的统计数据，两者做差便可得出在单位时间中上传或下载的数据总量，变可计算出速度
 * ps：以下完整实现了下载速度的处理和定时任务，上传速度同理
 * @param uid 应用程序uid
 * @see [TrafficInfo.getApplicationUid]获取应用程序uid
 * @param scheduleTime 回调间隔时间（即多少时间计算一次速度），默认1秒,最小500ms
 */
class TrafficInfo @JvmOverloads constructor(uid: Int = 0, scheduleTime: Long = 1000L) {

    //上一次读取的 接收（下载）数据大小
    private var preRxBytes: Long = 0L

    //上一次读取的 发送（上传）数据大小
    private var preTxBytes: Long = 0L

    //数据读取定时器
    private var mRxTimer: Timer? = null

    internal var mRxLister: OnTrafficInfoRxListener? = null

    private val uid: Int//0表示获取所有，>0 表示获取对应应用的流量速度
    private val scheduleTime: Long

    init {
        this.uid = Math.max(0, uid)
        this.scheduleTime = Math.max(500L, scheduleTime)
    }

    //文档中记录的接收数据大小（byte）
    private val rxBytes: Long
        get() = if (uid <= 0) TrafficStats.getTotalRxBytes() else TrafficStats.getUidRxBytes(uid)

    //文档中记录的发送数据大小（byte）
    private val txBytes: Long
        get() = if (uid <= 0) TrafficStats.getTotalTxBytes() else TrafficStats.getUidTxBytes(uid)

    /**
     * 两次接收数据之间的差值（单位b）
     */
    private val rxNetIntervalDifference: Long
        get() {
            val diff: Long
            var current = rxBytes
            Log.d(TAG, "采取当前数据:total=" + current)
            if (preRxBytes == 0L) {
                preRxBytes = current
                try {
                    Thread.sleep(200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                current = rxBytes
                Log.d(TAG, "采取当前数据(2次采取):total=" + current)
                diff = (current - preRxBytes) * 5
            } else {
                diff = current - preRxBytes
            }
            preRxBytes = current
            Log.d(TAG, "当前速度大小：speed" + diff)
            return diff
        }

    /**
     * 启动下载速度计算任务
     * @param listener 回调
     * @param scheduleTime 数据采取间隔时间，默认1s
     */
    fun startRxNetSpeedTask(listener: OnTrafficInfoRxListener) {
        stopRxNetSpeedTask()
        mRxLister = listener
        mRxTimer = Timer()//timer无法重用
        mRxTimer!!.schedule(object : TimerTask() {
            override fun run() {
                try {
                    val speed = rxNetIntervalDifference
                    if (mRxLister != null) {
                        mRxLister!!.onTrafficRxNetBack(speed, scheduleTime)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }, 500, scheduleTime) // 每秒更新一次
    }

    /**
     * 停止下载速度计算任务
     */
    fun stopRxNetSpeedTask() {
        preRxBytes = 0
        if (mRxTimer != null) {
            mRxTimer!!.cancel()
            mRxTimer = null
        }
    }

    val txNetSpeed: Long
        get() {
            val diff: Long
            var current = txBytes
            Log.d(TAG, "采取当前数据:total=" + current)
            if (preTxBytes == 0L) {
                preTxBytes = current
                try {
                    Thread.sleep(200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                current = txBytes
                Log.d(TAG, "采取当前数据(2次采取):total=" + current)
                diff = (current - preTxBytes) * 5
            } else {
                diff = current - preTxBytes
            }
            preTxBytes = current
            Log.d(TAG, "当前速度大小：result" + diff)

            return diff
        }


    /**
     * 接收数据回调
     */
    interface OnTrafficInfoRxListener {
        /**
         * @param scheduleTime 采取间隔时间
         * @param intervalDifference [scheduleTime]时间中接收的数据总量，单位byte
         */
        fun onTrafficRxNetBack(scheduleTime: Long, intervalDifference: Long)
    }

    /**
     * 上传数据回调
     */
    interface OnTrafficInfoTxListener {

        /**
         * @param scheduleTime 采取间隔时间
         * @param intervalDifference [scheduleTime]时间中接收的数据总量，单位byte
         */
        fun onTrafficTxNetBack(scheduleTime: Long, intervalDifference: Long)
    }

    companion object {

        private const val TAG = "TrafficInfo"

        /**
         * 获取context的程序uid
         * @param context
         * @return
         */
        fun getApplicationUid(context: Context): Int {
            return context.applicationInfo.uid
        }

        /**
         * 转换以秒为单位的对应速度文本
         * 如果在102 B/s以内，则直接返回
         * 如果在1024 K/s以内，则以K/s返回
         * 否则以 M/s返回
         * @param scheduleTime 采取间隔时间
         * @param intervalDifference [scheduleTime]时间中接收的数据总量，单位byte
         */
        fun formatNetSpeed(scheduleTime: Long, intervalDifference: Long): String {
//            if (intervalDifference < 102) {//102之前，直接以byte格式显示
//                return String.format("%.1fB/s", intervalDifference)
//            } else {
//                var kb = intervalDifference / 1024.0
//                Log.d(TAG, "KB：" + kb)
//                if (kb > 1024) {
//                    val mb = kb / 1024.toDouble()
//                    val bd = BigDecimal(mb)
//                    kb = bd.setScale(1, BigDecimal.ROUND_HALF_UP).toDouble()
//                    return kb.toString() + "M/s"
//                } else {
//                    val bd = BigDecimal(kb)
//                    kb = bd.setScale(1, BigDecimal.ROUND_HALF_UP).toDouble()
//                    return kb.toString() + "K/s"
//                }
//            }
            //先转换成 byte/s
            val byteSpeed = intervalDifference.toDouble().div(scheduleTime).times(1000L)
            if (byteSpeed < 102) {
                return String.format("%.1fB/s")
            } else {
                val kbSpeed = byteSpeed.div(1024)
                if (kbSpeed > 1024) {
                    val mb = kbSpeed.div(1024)
                    val bd = BigDecimal(mb)
                    val result = bd.setScale(1, BigDecimal.ROUND_HALF_UP).toDouble()
                    return result.toString() + "M/s"
                } else {
                    val bd = BigDecimal(kbSpeed)
                    val result = bd.setScale(1, BigDecimal.ROUND_HALF_UP).toDouble()
                    return result.toString() + "K/s"
                }
            }

        }
    }

}
