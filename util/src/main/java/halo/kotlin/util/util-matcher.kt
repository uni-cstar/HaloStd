package halo.kotlin.util

/**
 * Created by Lucio on 18/4/18.
 * 正则表达式
 */

object Matcher {


    /**
     * 是否是版本数字；eg. like 1 or 1.0  or 3.1.25
     */
    @JvmStatic
    fun isVersionNumber(content: String): Boolean {
        return content.matches(Regex("[\\d.]+"))
    }
}
