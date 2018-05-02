package halo.android.permission

import android.support.annotation.DrawableRes
import halo.kotlin.util.EMPTY

/**
 * 基础权限
 * @param value 权限内容
 * @param granted 是否拥有权限
 * @param name 权限名字（用户自定义）
 * @param icon 图标（用户自定义）
 */
open class Permission(val value: String,
                      var granted: Boolean = false,
                      val name: String = EMPTY,
                      @DrawableRes val icon: Int = 0)