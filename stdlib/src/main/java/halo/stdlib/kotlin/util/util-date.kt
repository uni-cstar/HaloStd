/**
 * Created by Lucio on 18/1/18.
 */
package halo.stdlib.kotlin.util

import java.text.SimpleDateFormat
import java.util.*


const val TIME_ONE_DAY = 86400000L
const val TIME_ONE_HOUR = 3600000L
const val TIME_TEN_MINUTE = 600000L


/**
 * utc 时间格式
 */
const val DATE_UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

/**
 * 国内常用的完整时间格式，yyyy-MM-dd HH:mm:ss
 */
const val DATE_CN_FORMAT = "yyyy-MM-dd HH:mm:ss"

/**
 * 星期几 格式
 */
const val DATE_WEEK_FORMAT = "E"

/**
 * utc时间格式化
 */
val UTC_DATE_FORMAT by lazy {
    SimpleDateFormat(DATE_UTC_FORMAT)
}

/**
 * 格式化成字符串
 */
fun Date.format(format: String): String {
    return SimpleDateFormat(format).format(this)
}

/**
 * 获取星期几
 */
val Date.week: String
    get() = this.format(DATE_WEEK_FORMAT)

/**
 * utc时间格式字符串
 */
val Date.utcString: String
    get() = UTC_DATE_FORMAT.format(this)

/**
 * 获取UTC时间
 * @return
 */
fun getUtcDate(): Date {
    // 1、取得本地时间：
    val cal = Calendar.getInstance()
    // 2、取得时间偏移量：
    val zoneOffset = cal.get(Calendar.ZONE_OFFSET)
    // 3、取得夏令时差：
    val dstOffset = cal.get(Calendar.DST_OFFSET)
    // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
    cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset))
    return cal.time
}

/**
 * utc 时间转换成本地时间
 */
fun Date.utcToLocal(): Date {
    // 1、取得本地时间：
    val cal = Calendar.getInstance()
    // 2、取得时间偏移量：
    val zoneOffset = cal.get(Calendar.ZONE_OFFSET)
    // 3、取得夏令时差：
    val dstOffset = cal.get(Calendar.DST_OFFSET)
    cal.time = this
    cal.add(Calendar.MILLISECOND, zoneOffset + dstOffset)
    return cal.time
}


var FRIENDLY_TODAY = "今天"
var FRIENDLY_YESTERDAY = "昨天"
var FRIENDLY_BEFORE_YESTERDAY = "前天"
var FRIENDLY_JUST_NOW = "刚刚"// 十分钟内

/**
 * 自定义最小时间
 */
private val MIN_DATE: Date = GregorianCalendar(1, 1, 1).time

/**
 * 转换成友好阅读的时间格式
 * @param date
 * @return 例如：今天 3:50 3小时前
 */
fun Date?.friendlyString(): String {
    if (this == null || this.before(MIN_DATE))
        return "-"
    val nowCal = Calendar.getInstance()
    nowCal.time = Date()

    val userCal = Calendar.getInstance()
    userCal.time = this

    val delta = nowCal.timeInMillis - userCal.timeInMillis

    if (delta < TIME_TEN_MINUTE) { // 十分钟内
        return FRIENDLY_JUST_NOW
    } else {
        val difDays: Int
        val isSameYear = nowCal.get(Calendar.YEAR) == userCal.get(Calendar.YEAR)
        if (isSameYear) {
            //如果是在同一年内的时间，则直接取出两个日期的天数间隔
            difDays = nowCal.get(Calendar.DAY_OF_YEAR) - userCal.get(Calendar.DAY_OF_YEAR)
        } else {
            difDays = Math.ceil(delta.toDouble().div(TIME_ONE_DAY)).toInt() //计算日期与当前日期相隔天数,向上取整，即0.1＝1 ，1.1=2
        }

        // 注：格式化字符串存在区分大小写
        // 对于创建SimpleDateFormat传入的参数：EEEE代表星期，如“星期四”；MMMM代表中文月份，如“十一月”；MM代表月份，如“11”；
        // yyyy代表年份，如“2010”；dd代表天，如“25”
        // date.getDay()
        when (difDays) {
            0 -> {
                //同一天内，则以"今天 12:11"形式格式
                return "${FRIENDLY_TODAY} ${this.format("HH:mm")}"
            }
            1 -> {
                //间隔一天内，则以"昨天 12:11"形式格式
                return "${FRIENDLY_YESTERDAY} ${this.format("HH:mm")}"
            }
            2 -> {
                //间隔两天内，则以"前天 12:11"形式格式
                return "${FRIENDLY_BEFORE_YESTERDAY} ${this.format("HH:mm")}"
            }
            else -> {
                if (isSameYear) {
                    //同一年，返回"月-日 时:分:秒"格式
                    return this.format("MM-dd HH:mm:ss")
                } else {
                    return this.format(DATE_CN_FORMAT)
                }
            }
        }
    }
}


