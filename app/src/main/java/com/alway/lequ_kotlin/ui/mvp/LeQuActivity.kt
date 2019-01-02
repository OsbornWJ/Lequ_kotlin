package com.alway.lequ_kotlin.ui.mvp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.ProxyActivity
import kotlinx.android.synthetic.main.activity_le_qu.*

class LeQuActivity : ProxyActivity() {

    override fun setLayout(): Any = R.layout.activity_le_qu

    override fun initData(savedInstanceState: Bundle?) {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
