package com.easy


import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * Created by Lucio on 17/11/23.
 */

class KotlinUnitTest {


//
//    fun test(){
//        timemo
//    }

//
//    fun caculate(datas: Array<Triple<Int, Int, Int>>,
//                 start: Double,
//                 end: Double): Double {
//        var result = 0.0
//        datas.forEach {
//            val realStart = Math.max(it.first.toDouble(), start)
//            val realEnd = Math.min(it.second.toDouble(), end)
//            if (realEnd > realStart)
//                result += (realEnd - realStart).div(it.third)
//        }
//        return result
//    }
//    fun testCoroutine(){
//        ui{//ui主线程
//            bg {
//                //子线程
//                uiLazy {
//
//                }
//            }
//
//            bgLazy {
//
//            }
//
//            bgLaunch {
//
//            }
//        }
//    }
////    @Test
////    fun testEqual() {
////        val item = Image("1", "1")
////        val item2 = Image("1", "12")
////        assertTrue(item == item2)
////    }
//
//    //    @Test
//    fun testListCapacity() {
//        val list = ArrayList<String>(2)
//
//        for (i in 0..5) {
//            list.add("string $i")
//        }
//
//        list.forEach {
//            println(it)
//        }
//
//    }
//
//    //    @Test
//    fun testNowDate() {
//
//        bg {
//            for (i in 0..10) {
//                Thread.sleep(500)
//                println("线程1 输出i = $i")
//            }
//            println("线程1 结束执行")
//        }
//
//        println("创建线程2")
//        val task2 = bgLazy {
//            var result: Int = 0
//            for (i in 0..10) {
//                Thread.sleep(500)
//                println("线程2 输出i = $i")
//                result = i
//            }
//            println("线程2 结束执行")
//            result
//        }
//        println("开始线程2")
//        task2.start()
//
//
//        runBlocking {
//            val result = task2.await()
//            println("线程2 返回结果$result")
//        }
//    }
//
//    //    @Test
//    fun printToDecimal2() {
//
//        assertEquals("31", 31.1123123123254.toDecimal(0))
//        println("value 0 = ${31.1123125123254.toDecimal(-2)}")
//        assertEquals("31.1", 31.1123123123254.toDecimal(1))
//        println("value 1 = ${31.1123125123254.toDecimal(1)}")
//        assertEquals("31.11", 31.1123123123254.toDecimal(2))
//        println("value 2 = ${31.1123125123254.toDecimal(2)}")
//        assertEquals("31.11231", 31.1123123123254.toDecimal(5))
//        println("value 5 = ${31.1123125123254.toDecimal(5)}")
//        assertEquals("31.112313", 31.1123125123254.toDecimal(6))
//        println("value 6 = ${31.1123125123254.toDecimal(6)}")
//    }
//
//    //    @Test
//    fun testTry() {
//        Try {
//
//            println("test try[false]")
//            throw RuntimeException("test try[false]:RuntimeException")
//        }
//
//        Try(true) {
//            println("test try[true]")
//            throw RuntimeException("test try[true]:RuntimeException")
//        }
//
//        Try({
//            println("try 2 body")
//            throw RuntimeException("try 2 body :RuntimeException")
//        }, exception = {
//            println("try 2 exception")
//        })
//
//        Try(block = {
//            println("try 3 body")
//            throw RuntimeException("try 3 body :RuntimeException")
//        }, exception = {
//            println("try 3 exception")
//        }, finally = {
//            println("try 3 finally")
//        })
//
//    }
//
//    //    @Test
//    fun testHigherFunc() {
//        val target: Any? = null
//        target?.let {
//            println("null not support let")
//        }
//        target.let {
//            println("null support let")
//        }
//        runBlocking { }
//    }
//
//    //    @Test
//    fun testLock() {
//        val ct = CountDownLatch(2)
//
//        val readLock = ReentrantReadWriteLock().writeLock()
//
////        val pool = Executors.newFixedThreadPool(4)
//
////        pool.execute {
////            lock(readLock){
////                println("start")
////                Thread.sleep(5000)
////                println("sleep1 5 s" + System.currentTimeMillis())
////                ct.countDown()
////            }
////        }
////
////        pool.execute {
////            lock(readLock){
////                println("start 2")
////                Thread.sleep(5000)
////                println("sleep2 5 s" + System.currentTimeMillis())
////                ct.countDown()
////            }
////        }
//        val task1 = async(CommonPool, CoroutineStart.DEFAULT) {
//            lock(readLock) {
//                println("start")
//                Thread.sleep(5000)
//                println("sleep1 5 s" + System.currentTimeMillis())
//                ct.countDown()
//            }
//        }
//
//        val task2 = async(CommonPool, CoroutineStart.DEFAULT) {
//            lock(readLock) {
//                println("start 2")
//                Thread.sleep(5000)
//                println("sleep2 5 s" + System.currentTimeMillis())
//                ct.countDown()
//            }
//        }
//        ct.await()
////        task1.getCompleted()
////        task2.getCompleted()
//    }
}