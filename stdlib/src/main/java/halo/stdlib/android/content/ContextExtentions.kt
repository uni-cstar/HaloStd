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

package halo.stdlib.android.content

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.support.v7.app.AlertDialog
import android.widget.Toast

/**
 * Created by Lucio on 17/11/23.
 */

/**
 * 检查是否能响应Intent,避免直接start引起app崩溃
 */
@Throws(ActivityNotFoundException::class)
fun Context.checkResolveActivity(it: Intent) {
    it.resolveActivity(packageManager)
            ?: throw ActivityNotFoundException("no activity can handle this intent $it")
}

/**
 * 运行intent之前，检查intent是否会被响应，避免app直接崩溃
 * @throws ActivityNotFoundException intent不能被响应
 */
fun Context.startActivitySafely(it: Intent) {
    checkResolveActivity(it)
    if (this !is Activity) {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(it)
//    startActivitySafely(it, { ctx, e ->
//        if (ctx is Activity) {
//            AlertDialog.Builder(ctx)
//                    .setMessage(e.message)
//                    .setPositiveButton("确定", null)
//                    .show()
//        } else {
//            Toast.makeText(ctx, e.message, Toast.LENGTH_SHORT).show()
//        }
//    })
}

fun Context.startActivitySafely(it: Intent, exceptionHandle: (ctx: Context, e: Exception) -> Unit) {
    try {
        checkResolveActivity(it)
        if (this !is Activity) {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(it)
    } catch (e: Exception) {
        exceptionHandle(this, e)
    }
}

/**
 * 状态栏高度
 */
inline val Context.statusBarHeight: Int
    get() = this.resources.statusBarHeight


/**
 * 状态栏高度
 */
inline val Resources.statusBarHeight: Int
    get() {
        var result = 0
        val resourceId = this.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = this.getDimensionPixelSize(resourceId)
        }
        return result
    }

/**
 * 根据资源名字获取资源id
 * @param name 资源名字
 * @param type 资源类型
 * @param pkg 包名
 */
fun Resources.getResourceId(name: String, type: String, pkg: String): Int {
    return this.getIdentifier(name, type, pkg)
}

