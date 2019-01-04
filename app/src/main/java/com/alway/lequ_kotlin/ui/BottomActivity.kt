package com.alway.lequ_kotlin.ui

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorInt
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.LeQuDelegate
import com.alway.lequ_kotlin.ui.base.ProxyActivity
import com.joanzapata.iconify.widget.IconTextView
import kotlinx.android.synthetic.main.activity_bottom_bar_layout.*
import me.yokeyword.fragmentation.ISupportFragment


@Suppress("PrivatePropertyName")
abstract class BottomActivity : ProxyActivity(), View.OnClickListener {

    private val ITEM_TABS = mutableListOf<BottomTabBean>()
    private val ITEM_DELEGATES = mutableListOf<ISupportFragment>()
    private val ITEMS by lazy { setItems() }

    private var currentDelegate = 0
    private var clickedColorInt = Color.RED
    private var indexDelegate = 0

    override fun setLayout(): Any = R.layout.activity_bottom_bar_layout

    override fun initData(savedInstanceState: Bundle?) {

        indexDelegate = setIndexDelegate()
        if (setClickedColor() != 0) {
            clickedColorInt = setClickedColor()
        }

        ITEMS.forEach{
            ITEM_TABS.add(it.key)
            ITEM_DELEGATES.add(it.value)
        }

        for (i in 0 until ITEMS.size) {
            LayoutInflater.from(this).inflate(R.layout.bottom_item_icon_text, bottom_bar)
            val item = bottom_bar.getChildAt(i) as RelativeLayout
            item.tag = i
            item.setOnClickListener(this)
            val iconText = item.getChildAt(0) as IconTextView
            val titleText = item.getChildAt(1) as TextView
            iconText.text = ITEM_TABS[i].icon
            titleText.text = ITEM_TABS[i].title
            if (i == indexDelegate) {
                iconText.setTextColor(clickedColorInt)
                titleText.setTextColor(clickedColorInt)
            }
        }
        loadMultipleRootFragment(R.id.delegate_container, indexDelegate, *ITEM_DELEGATES.toTypedArray())
    }

    override fun onStart() {
        super.onStart()
        blurLayout.startBlur()
    }

    override fun onStop() {
        super.onStop()
        blurLayout.pauseBlur()
    }

    abstract fun setItems(): MutableMap<BottomTabBean, LeQuDelegate>

    abstract fun setIndexDelegate(): Int

    @ColorInt
    abstract fun setClickedColor(): Int

    private fun resetColor() {
        for (i in 0 until bottom_bar.childCount) {
            val item = bottom_bar.getChildAt(i) as RelativeLayout
            (item.getChildAt(0) as IconTextView).setTextColor(Color.GRAY)
            (item.getChildAt(1) as TextView).setTextColor(Color.GRAY)
        }
    }

    override fun onClick(v: View?) {
        val tag = v!!.tag as Int
        resetColor()
        val item = v as RelativeLayout
        (item.getChildAt(0) as IconTextView).setTextColor(clickedColorInt)
        (item.getChildAt(1) as TextView).setTextColor(clickedColorInt)
        showHideFragment(ITEM_DELEGATES[tag], ITEM_DELEGATES[currentDelegate])
        currentDelegate = tag
    }

}

class BottomTabBean(val icon: CharSequence, val title: CharSequence)
