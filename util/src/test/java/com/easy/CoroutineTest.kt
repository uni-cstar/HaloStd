package com.easy

import halo.kotlin.bgLaunchLazy
import halo.kotlin.bgLazy
import halo.kotlin.util.utcString
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.coroutines.experimental.*

/**
 * Created by Lucio on 17/12/1.
 */

class CoroutineTest {


    /**
     * 是否是
     */
    private inline fun String.isVersionNumber(): Boolean {
        return this.matches(Regex("[\\d.]+"))
    }

    @Test
    fun time() {
        println("com.android.settings.Settings'$'AccessLockSummaryActivity")
        println("com.android.settings.Settings`$`AccessLockSummaryActivity")
    }

//    @Test
    fun calTest() {
        cal(5)

//        cal2(5)
//        cal2(10)
//        cal2(22)
    }

    fun cal(year: Int) {
        val month = 260
        val monthExtra = 40
        val scale = 0.04
        val total = 50000

        println("每月车位费：$month 车位物管费：$monthExtra 车位费：$total  年利率${scale}")

        println("假如以租的形式（五万车位费完全足够的情况，其他余钱存银行）")
        var zuMoney = total.toDouble()
        var monthSize = 0
        while (zuMoney > month + monthExtra) {
            zuMoney -= month + monthExtra //减去一个月租金
            monthSize++ //月份计数 ＋1
            zuMoney += zuMoney * scale.div(12)//余钱每个月产生的利息
        }
        println("总共可以租 $monthSize 个月 约等于 ${monthSize.times(1.0f).div(12)}年")

        println("假如以租的形式（实时支付）")
        var zuMoney2 = 0.0
        var monthSize2 = monthSize
        while (monthSize2 > 0) {
            monthSize2--
            zuMoney2 += month + monthExtra //减去一个月租金
        }
        println("租 $monthSize 个月 总共要花 $zuMoney2")

    }

    fun cal2(year: Int) {
        val cq = 70
        val total = 50000
        val per = total.times(1.0).div(cq) // 每年产权市值

        println("使用 $year 年，剩余产权值 ${per.times(cq - year)}")
        println("抵消本该支持的费用 ${260 * 12 * year}")

        println("剩余 ${total - 260 * 12 * year + per.times(cq - year)}")
    }

    //    @Test
    fun main2() {
        val ct = CountDownLatch(1)
        val task = bgLaunchLazy {
            val bgTask = bgLazy {
                println("time:${Date().utcString}")
                println("start bg task")

                Thread.sleep(3000)
                println("task end")
                println("time:${Date().utcString}")
                ct.countDown()
            }
            bgTask.await()
        }

        task.start()
        ct.await()
    }

    fun testCoroutine() {

        println("before coroutine")
        //启动我们的协程
        asyncCal("test.zip") {
            println("in coroutine. Before suspend.")
            //暂停我们的线程，并开始执行一段耗时操作
            val result: String = suspendCoroutine { continuation ->
                println("in suspend block.")
                val content = continuation.context.get(MyCoroutineContext)?.content as String
                content.forEach {
                    Thread.sleep(1000)
                    println("time sleep :$it")
                }
                continuation.resume("complete")
//                continuation.resumeWithException(RuntimeException("runtime exception"))
                println("after resume.")
            }
            println("in coroutine. After suspend. result = $result")
        }
        println("after coroutine")
    }

    class MyCoroutineContext<T>(val content: T) : AbstractCoroutineContextElement(MyCoroutineContext.Companion) {
        companion object : CoroutineContext.Key<MyCoroutineContext<*>> {

        }
    }

    fun asyncCal(content: String, block: suspend () -> Unit) {
        val continuation = object : Continuation<Unit> {
            override val context: CoroutineContext
                get() = MyCoroutineContext<String>(content)

            override fun resume(value: Unit) {
                println("my coroutine resume,value =$value")
            }

            override fun resumeWithException(exception: Throwable) {
                println("my coroutine resumeWithException,exception =$exception")
            }

        }

        block.startCoroutine(continuation)
    }

}