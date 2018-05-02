package halo.android.widget

import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.widget.LinearLayoutManager

/**
 * Created by Lucio on 17/12/18.
 * 颜色 线性分割线
 */

class ColorLinearItemDecoration(space: Int,
                                color: Int,
                                orientation: Int = LinearLayoutManager.HORIZONTAL)
    : BaseLinearItemDecoration(space, orientation) {

    private val mPaint: Paint

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = color
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = space.toFloat()
    }

    override fun draw(c: Canvas, left: Int, top: Int, right: Int, bottom: Int) {
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

}