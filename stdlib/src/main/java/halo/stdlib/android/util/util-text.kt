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

package halo.stdlib.android.util

import android.graphics.Paint
import android.support.annotation.ColorInt
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView

/**
 * Created by Lucio on 18/1/19.
 */

/**
 * 添加删除线
 */
fun TextView.applyDeleteLine() {
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

/**
 * 添加下划线
 */
fun TextView.applyUnderLine() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

/**
 * 文字加粗
 */
fun TextView.applyBold() {
    this.paintFlags = this.paintFlags or Paint.FAKE_BOLD_TEXT_FLAG
}

/**
 * 高亮显示
 * @param start 渲染开始位置
 * @param end 渲染结束位置
 * @param color 颜色
 */
fun String.toHighLight(start: Int, end: Int, @ColorInt color: Int): CharSequence {
    val style = SpannableStringBuilder(this)
    if (start >= 0 && end >= 0 && end >= start) {
        style.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return style

}

/**
 * 高亮显示
 * @param tag 需要高亮的部分
 * @param color 渲染颜色
 * @return
 */
fun String.toHighLight(tag: String, color: Int): CharSequence {
    if (tag.isEmpty() || this.isEmpty())
        return this
    val start = this.indexOf(tag)
    val end = start + tag.length
    return this.toHighLight(start, end, color)
}