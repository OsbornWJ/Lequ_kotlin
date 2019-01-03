package com.alway.lequ_kotlin.ui

import android.graphics.Color
import android.os.Bundle
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.ProxyActivity
import android.support.annotation.ColorInt
import com.alway.lequ_kotlin.ui.base.LeQuDelegate


abstract class BottomActivity : ProxyActivity() {

    private val ITEM_TABS = ArrayList<BottomTabBean>()
    private val ITEM_DELEGATES = ArrayList<LeQuDelegate>()
    private val ITEMS by lazy { setItems() }

    private var clickedColorInt = Color.RED
    private var indexDelegate = 0

    override fun setLayout(): Any = R.layout.activity_le_qu

    override fun initData(savedInstanceState: Bundle?) {
        
    }

    abstract fun setItems(): LinkedHashMap<BottomTabBean, LeQuDelegate>

    abstract fun setIndexDelegate(): Int

    @ColorInt
    abstract fun setClickedColor(): Int

}

class BottomTabBean(val icon: CharSequence, val title: CharSequence)
