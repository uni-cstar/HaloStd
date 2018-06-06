package halo.stdlib.android.widget

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager

/**
 * Created by Lucio on 17/12/18.
 * 图片分割线
 */
class ItemDecorationLinearDrawable : BaseLinearItemDecoration {

    val mDivider: Drawable

    /**
     * 此方法待测
     * @param divider 如果是用xml写的drawable，则需要定义size节点指明高度
     */
    constructor(divider: Drawable, orientation: Int = LinearLayoutManager.VERTICAL)
            : super(if (orientation == LinearLayoutManager.HORIZONTAL) divider.intrinsicWidth else divider.intrinsicHeight, orientation) {
        mDivider = divider
    }

    constructor(divider: Drawable, space: Int, orientation: Int = LinearLayoutManager.VERTICAL)
            : super(space, orientation) {
        mDivider = divider
    }

    override fun draw(c: Canvas, left: Int, top: Int, right: Int, bottom: Int) {
        mDivider.setBounds(left, top, right, bottom)
        mDivider.draw(c)
    }

}