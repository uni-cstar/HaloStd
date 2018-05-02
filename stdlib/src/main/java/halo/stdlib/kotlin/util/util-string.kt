package halo.stdlib.kotlin.util

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


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
 * 获取后缀
 */
fun String.getSuffix(): String? {
    val index = lastIndexOf(".")
    if (index != -1) {
        return substring(index + 1).toLowerCase(Locale.US)
    } else {
        return null
    }
}

