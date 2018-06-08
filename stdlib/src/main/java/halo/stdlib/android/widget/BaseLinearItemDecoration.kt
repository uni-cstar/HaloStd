package halo.stdlib.android.widget

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Lucio on 17/12/18.
 * recycler  线性 分割线
 * @param drawBeginning 是否在开始的地方绘制一条分割线；因为Item的分割线是在Item的结束之后的绘制分割线，所以如果需要在第一条Item的前面绘制一条分割线，将此变量设置为true
 */

abstract class BaseLinearItemDecoration @JvmOverloads constructor(val space: Int,
                                                                  val orientation: Int = LinearLayoutManager.HORIZONTAL,
                                                                  val drawBeginning: Boolean = false)
    : RecyclerView.ItemDecoration() {

    final override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        if (drawBeginning && parent?.getChildLayoutPosition(view) == 0) {
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect?.set(space, 0, space, 0)
            } else {
                outRect?.set(0, space, 0, space)
            }
        } else {
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect?.set(0, 0, space, 0)
            } else {
                outRect?.set(0, 0, 0, space)
            }
        }
    }

    final override fun onDraw(c: Canvas?, parent: RecyclerView?) {
        if (parent?.layoutManager == null) return
        c?.run {
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                drawHorizontal(c, parent)
            } else {
                drawVertical(c, parent)
            }
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        //recycler的左右padding区域不在分割线内
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount

        //绘制第一条线
        if (drawBeginning && childCount > 0) {
            val first = parent.getChildAt(0)
            val params = first.layoutParams as RecyclerView.LayoutParams
            val bottom = first.top - params.topMargin
            val top = bottom - space
            draw(c, left, top, right, bottom)
        }

        //常规绘制
        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + space
            draw(c, left, top, right, bottom)
        }
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount

        //绘制第一条线
        if (drawBeginning && childCount > 0) {
            val first = parent.getChildAt(0)
            val params = first.layoutParams as RecyclerView.LayoutParams
            val right = first.left - params.leftMargin
            val left = right - space
            draw(c, left, top, right, bottom)
        }

        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + space
            draw(c, left, top, right, bottom)
        }
    }

    //根据指定区域绘制
    protected abstract fun draw(c: Canvas, left: Int, top: Int, right: Int, bottom: Int)

}