package halo.kotlin.util

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * Created by Lucio on 18/1/19.
 */

const val EMPTY = ""

/**
 * null或空字符串时取默认值
 */
fun String?.orDefaultIfNullOrEmpty(def: String = EMPTY): String = if (this.isNullOrEmpty()) def else this!!

/**
 * 首字母大写
 */
fun String.upperFirstLetter(): String {
    if (this.isEmpty() || this.isBlank())
        return this

    val c = this[0]
    //如果第一个字符不是字母或者已经是大写字母，则返回原字符串
    if (!Character.isLetter(c) || Character.isUpperCase(c)) {
        return this
    }

    return StringBuilder(this.length).append(Character.toUpperCase(c)).append(this.substring(1)).toString()
}

/**
 * 全拼转半拼
 * fullWidthToHalfWidth(null) = null;
 * fullWidthToHalfWidth("") = "";
 * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
 * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
 * @return
 */
fun String.fullWidthToHalfWidth(): String {
    if (this.isEmpty()) {
        return this
    }

    val source = this.toCharArray()
    for (i in source.indices) {
        if (source[i].toInt() == 12288) {
            source[i] = ' '
        } else if (source[i].toInt() in 65281..65374) {
            source[i] = (source[i].toInt() - 65248).toChar()
        } else {
            source[i] = source[i]
        }
    }
    return String(source)
}

/**
 * 半拼转全拼
 * halfWidthToFullWidth(null) = null;
 * halfWidthToFullWidth("") = "";
 * halfWidthToFullWidth(" ") = new String(new char[] {12288});
 * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
 * @param s
 * @return
 */
fun String.halfWidthToFullWidth(): String {
    if (this.isEmpty()) {
        return this
    }
    val source = this.toCharArray()
    for (i in source.indices) {
        if (source[i] == ' ') {
            source[i] = 12288.toChar()
        } else if (source[i].toInt() in 33..126) {
            source[i] = (source[i].toInt() + 65248).toChar()
        } else {
            source[i] = source[i]
        }
    }
    return String(source)
}


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
