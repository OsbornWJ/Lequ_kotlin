package com.alway.lequ_kotlin.ui

import android.support.v4.app.ActivityCompat
import com.alway.lequ_kotlin.ui.base.LeQuDelegate


class LeQuActivity : BottomActivity() {

    // 再点一次退出程序时间设置
    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0

    override fun setItems(): LinkedHashMap<BottomTabBean, LeQuDelegate> {
        return linkedMapOf(Pair(BottomTabBean("{fa-home}", "首页"), MainDelegate()),
                Pair(BottomTabBean("{fa-compass}", "发现"), BeautyDelegate()),
                Pair(BottomTabBean("{fa-user}", "我的"), BeautyDelegate()))
    }

    override fun setIndexDelegate(): Int = 0

    override fun setClickedColor(): Int = getColorPrimary()

    override fun onBackPressedSupport() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            pop()
        } else {
            ActivityCompat.finishAfterTransition(this)
        }
    }
}
