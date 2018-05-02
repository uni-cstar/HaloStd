/**
 * Created by Lucio on 17/11/23.
 */

package halo.android.content.res

import android.content.res.Resources

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




