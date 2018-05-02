package halo.android.widget

import android.content.Context
import android.support.annotation.MenuRes
import android.support.annotation.NonNull
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.PopupMenu
import android.view.Gravity
import android.view.View

/**
 * Created by Lucio on 17/12/5.
 */

object MenuBuilder {

    /**
     * 通过反射，设置popupmenu显示图标
     */
    fun setForceShowIconCompat(popupMenu: PopupMenu, forceShowIcon: Boolean) {
        try {
            val field = popupMenu.javaClass.getDeclaredField("mPopup")
            field.isAccessible = true
            val mHelper = field.get(popupMenu) as MenuPopupHelper
            mHelper.setForceShowIcon(forceShowIcon)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 创建popupMenu
     * 关注知识点：menu
     * 参考文章：
     * ［Menu］ https://www.cnblogs.com/tonghao/p/5723534.html
     * ［popupMenu］http://blog.csdn.net/aqi00/article/details/52352745
     */
    fun buildPopupMenu(@NonNull ctx: Context,
                       @NonNull anchor: View,
                       @MenuRes menuRes: Int,
                       gravity: Int = Gravity.BOTTOM,
                       showIcon: Boolean = false): PopupMenu {
        val popupMenu = PopupMenu(ctx, anchor, gravity)
        popupMenu.inflate(menuRes)
        if (showIcon) {
            popupMenu.setForceShowIconCompat(showIcon)
        }
        return popupMenu
    }

    /**
     * 注意ListPopupWindow的存在价值
     * 参考文档：http://blog.csdn.net/aqi00/article/details/52352745
     */
    fun buildListPopupWindow() {
    }

}

/**
 * 通过反射，设置popupmenu显示图标
 * Sets whether the popup menu's adapter is forced to show icons in the
 * menu item views.
 * <p>
 * Changes take effect on the next call to show().
 *
 * @param forceShowIcon {@code true} to force icons to be shown, or
 *                  {@code false} for icons to be optionally shown
 */
fun PopupMenu.setForceShowIconCompat(forceShowIcon: Boolean) = MenuBuilder.setForceShowIconCompat(this, forceShowIcon)