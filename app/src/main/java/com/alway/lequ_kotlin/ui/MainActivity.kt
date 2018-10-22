package com.alway.lequ_kotlin.ui

import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.MenuItem
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.ProxyActivity
import com.alway.lequ_kotlin.ui.mvp.contract.MainContract
import com.alway.lequ_kotlin.ui.mvp.model.MainModel
import com.alway.lequ_kotlin.ui.mvp.presenter.MainPresenter
import com.example.lequ_core.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 创建人: Jeven
 * 邮箱:   Osboenjie@163.com
 * 功能:  主体程序
 */

class MainActivity: ProxyActivity<MainPresenter>(), NavigationView.OnNavigationItemSelectedListener, MainContract.View{

    // 再点一次退出程序时间设置
    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0

    override fun setLayout(): Any {
        return R.layout.activity_main
    }

    override fun initPersenter() {
        mPresenter = MainPresenter(MainModel(), this)
    }

    override fun initData(savedInstanceState: Bundle?) {
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

    override fun showLoading() {
        AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("测试")
                .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->  dialog.dismiss()})
                .create()
                .show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
