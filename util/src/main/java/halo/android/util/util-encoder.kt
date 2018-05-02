/**
 * Created by Lucio on 18/1/24.
 */
package halo.android.util

import android.net.Uri
import halo.kotlin.BestPerformance


/**
 * url编码
 * @see [BestPerformance]
 * @param allow set of additional characters to allow in the encoded form,
 *  null if no characters should be skipped
 * @return an encoded version of s suitable for use as a URI component,
 *  or null if s is null
 */
@BestPerformance("在url编码中，我门通常使用的java.net.URLEncoder.encode方法进行url编码，此方法存在一个问题就是会将空格转换成＋，导致iOS那边无法将＋解码成空格" +
        "详细请见：https://www.jianshu.com/p/4a7eb969235d")
fun String.urlEncode(allow: String? = null): String {
    return Uri.encode(this, allow)
}

/**
 * url解码
 */
fun String.urlDecode(): String {
    return Uri.decode(this)
}
