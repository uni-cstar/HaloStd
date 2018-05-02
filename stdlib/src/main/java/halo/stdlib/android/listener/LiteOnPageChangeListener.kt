package halo.stdlib.android.listener

import android.support.v4.view.ViewPager

/**
 * Created by Lucio on 17/11/23.
 */

abstract class LiteOnPageChangeListener : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }
}