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

import java.text.DecimalFormat

/**
 * Created by Lucio on 18/5/2.
 */

fun Double?.orDefault(def: Double = 0.0) = this ?: def

/**
 * 保留一位小数
 */
fun Double?.toDecimal1(): String {
    if (this == null)
        return "0"
    return decimalFormat1.format(this)
}

/**
 * 保留为两位小数
 */
fun Double?.toDecimal2(): String {
    if (this == null)
        return "0"
    return decimalFormat2.format(this)
}


/**
 * 保留小数位
 * @param cnt 保留小数点后多少位
 */
fun Double?.toDecimal(cnt: Int = 1): String {
    if (this == null)
        return "0"

    if (cnt <= 0) {
        return toInt().toString()
    } else if (cnt == 1) {
        return toDecimal1()
    } else if (cnt == 2) {
        return toDecimal2()
    } else {
        var de = cnt
        val pattern: StringBuilder = StringBuilder("0.")
        while (de > 0) {
            de--
            pattern.append("0")
        }
        return DecimalFormat(pattern.toString()).format(this)
    }
}

/**
 * 保留小数点后一位
 */
private val decimalFormat1 by lazy {
    DecimalFormat("0.0")
}

/**
 * 保留小数点后两位
 */
private val decimalFormat2 by lazy {
    DecimalFormat("0.00")
}