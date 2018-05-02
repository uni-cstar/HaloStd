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

package halo.stdlib.kotlin

import halo.stdlib.kotlin.util.EMPTY
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by Lucio on 18/5/2.
 */

/**
 *  转换成MD5字符串（标准MD5加密算法）
 * @throws NoSuchAlgorithmException 加密异常
 * @throws UnsupportedEncodingException 不支持编码异常
 */
@Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
fun String.toMd5(): String {
    if (this.isNullOrEmpty())
        return EMPTY
    var bytes = this.toByteArray(Charsets.UTF_8)
    val md5 = MessageDigest.getInstance("MD5")
    bytes = md5.digest(bytes)

    val result = StringBuilder()
    for (item in bytes) {
        val hexStr = Integer.toHexString(0xFF and item.toInt())
        if (hexStr.length < 2) {
            result.append("0")
        }
        result.append(hexStr)
    }
    return result.toString()
}
