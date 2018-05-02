/*
 * Copyright (C) 2018 Lucio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package halo.stdlib.kotlin.monitor

interface TimeMonitor {
    fun elapsedTime(): Long
    fun printElapsedTime(tag: String)
}

/**
 * 时间监控：用于检测开始和结束时间的时间差
 * 与扩展方法[timeMonitor]的区别是，此方法可以用于不在一个函数块的场景
 */
internal class TimeMonitorImpl : TimeMonitor {

    private val start: Long = System.currentTimeMillis()

    override fun elapsedTime(): Long {
        return System.currentTimeMillis() - start
    }

    override fun printElapsedTime(tag: String) {
        printElapsedTimeImpl(tag, start)
    }
}

fun printElapsedTimeImpl(tag: String, start: Long) {
    if (!Monitor.enableTimeMonitor)
        return
    val end = System.currentTimeMillis() - start
    if (end > 500) {
        System.out.println("[TimeMonitor]: $tag takes ${end / 1000.0} seconds")
    } else {
        System.out.println("[TimeMonitor]: $tag takes ${end} milliseconds")
    }
}

/**
 * 监控方法运行时间
 */
@JvmOverloads
inline fun timeMonitor(tag: String = Thread.currentThread().stackTrace[1].methodName, func: () -> Unit) {
    val start = System.currentTimeMillis()
    func()
    printElapsedTimeImpl(tag, start)
}
