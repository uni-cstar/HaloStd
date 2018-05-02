/**
 * Created by Lucio on 17/11/23.
 */

package halo.kotlin

import java.text.DecimalFormat


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
