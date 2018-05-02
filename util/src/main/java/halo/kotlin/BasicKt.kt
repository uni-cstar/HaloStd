package halo.kotlin

/**
 * Created by Lucio on 17/7/12.
 * 基础变量扩展方法
 */

fun Boolean?.orDefault(def: Boolean = false): Boolean = this ?: def

fun Int?.orDefault(def: Int = 0) = this ?: def

fun Float?.orDefault(def: Float = 0f) = this ?: def

fun Double?.orDefault(def: Double = 0.0) = this ?: def

fun Long?.orDefault(def: Long = 0) = this ?: def

inline fun <T> T?.orDefault(initializer: () -> T) = this ?: initializer()

fun <T> List<T>?.isNullOrEmpty() = this == null || this.isEmpty()

fun <K, V> Map<K, V>?.isNullOrEmpty() = this == null || this.isEmpty()
