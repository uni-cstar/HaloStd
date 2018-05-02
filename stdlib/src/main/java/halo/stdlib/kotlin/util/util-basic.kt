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

package halo.stdlib.kotlin.util

import java.util.concurrent.locks.Lock

/**
 * Created by Lucio on 17/7/12.
 * 基础变量扩展方法
 */

fun Boolean?.orDefault(def: Boolean = false): Boolean = this ?: def

fun Int?.orDefault(def: Int = 0) = this ?: def

fun Float?.orDefault(def: Float = 0f) = this ?: def

fun Long?.orDefault(def: Long = 0) = this ?: def

inline fun <T> T?.orDefault(initializer: () -> T) = this ?: initializer()

fun <T> List<T>?.isNullOrEmpty() = this == null || this.isEmpty()

fun <K, V> Map<K, V>?.isNullOrEmpty() = this == null || this.isEmpty()


inline fun <T> lock(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}


