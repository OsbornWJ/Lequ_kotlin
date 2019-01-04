package com.alway.lequ_kotlin.ui

import com.alway.lequ_kotlin.ui.base.LeQuDelegate
import com.alway.lequ_kotlin.ui.mvp.delegate.DiscoveryDelegate

class LeQuActivity : BottomActivity() {

    override fun setItems(): LinkedHashMap<BottomTabBean, LeQuDelegate> {
        return linkedMapOf(Pair(BottomTabBean("{fa-home}", "首页"), DiscoveryDelegate()),
                Pair(BottomTabBean("{fa-compass}", "发现"), DiscoveryDelegate()),
                Pair(BottomTabBean("{fa-user}", "我的"), DiscoveryDelegate()))
    }

    override fun setIndexDelegate(): Int = 0

    override fun setClickedColor(): Int = getColorPrimary()
}
