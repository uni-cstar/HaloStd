package halo.android.content

import android.content.SharedPreferences
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Lucio on 17/11/23.
 * 修饰本地变量用于与本地SharedPreferences同步
 */

class AutoPref<T : Any> : ReadWriteProperty<Any?, T> {

    private var mDefValue: T by Delegates.notNull<T>()
    private var mKey: String by Delegates.notNull<String>()
    private var mPref: SharedPreferences? = null
    private var mPrefProvider: DynamicPrefProvider? = null

    /**
     * 用于SharedPreferences 动态变换的情况。 i.e. 用户缓存数据
     * @param provider 动态获取SharedPreferences接口
     * @param key
     * @param def 默认值
     */
    constructor(provider: DynamicPrefProvider, key: String, def: T) {
        mPrefProvider = provider
        mKey = key
        mDefValue = def
    }

    /**
     * 用于存储的SharedPreferences不改变的情况。 i.e. 公共缓存
     * @param def 默认值
     */
    constructor(sp: SharedPreferences, key: String, def: T) {
        mPref = sp
        mKey = key
        mDefValue = def
    }

    /**
     * 获取有效的Pref
     */
    private fun getPriorityPref(): SharedPreferences? = if (mPrefProvider != null) mPrefProvider?.pref else mPref


    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val prefs = getPriorityPref() ?: return mDefValue
        return findPreference(prefs, mKey, mDefValue)
    }

    //获取值
    private fun findPreference(prefs: SharedPreferences, key: String, default: T): T {
        return prefs.let {
            val result: Any = when (default) {
                is Long -> it.getLong(key, default)
                is Int -> it.getInt(key, default)
                is Float -> it.getFloat(key, default)
                is Boolean -> it.getBoolean(key, default)
                is String -> it.getString(key, default)
                else -> throw  IllegalArgumentException("this type can not be saved into preference")
            }
            result as T
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        val prefs = getPriorityPref() ?: return
        putPreference(prefs, mKey, value)
    }

    //存放值
    private fun putPreference(sp: SharedPreferences, key: String, value: T) {
        sp.edit().apply {
            when (value) {
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                else -> throw  IllegalArgumentException("this type can not be saved into preference")
            }
        }.apply()
    }

}

/**
 * 提供动态的SharedPreference（比如根据特定条件创建的sp）
 */
interface DynamicPrefProvider {
    val pref: SharedPreferences
}