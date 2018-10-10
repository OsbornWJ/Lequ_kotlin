package com.alway.lequ_kotlin.ui

import android.annotation.SuppressLint
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.MenuItem
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.ProxyActivity
import com.alway.lequ_kotlin.ui.contract.MainContract
import com.alway.lequ_kotlin.ui.model.MainModel
import com.alway.lequ_kotlin.ui.presenter.MainPresenter
import com.example.lequ_core.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: ProxyActivity<MainPresenter>(), NavigationView.OnNavigationItemSelectedListener, MainContract.View{

    // 再点一次退出程序时间设置
    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initPersenter() {
        mPresenter = MainPresenter(MainModel(), this)
    }

    override fun initData() {
        ActionBarDrawerToggle(this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
                .syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(R.id.nav_home)
    }

    override fun onBackPressedSupport() {
        if (drawer_layout.isDrawerOpen(Gravity.START)) {
            drawer_layout.closeDrawer(Gravity.START)
        } else {
            if (supportFragmentManager.backStackEntryCount > 1) {
                pop()
            } else {
                if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                    finish()
                } else {
                    TOUCH_TIME = System.currentTimeMillis()
                    ToastUtils.showToastShrot(getString(R.string.press_again_exit))
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
