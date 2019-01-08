package com.alway.lequ_kotlin.ui

import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.LeQuDelegate
import com.alway.lequ_kotlin.ui.mvp.contract.MainContract
import com.alway.lequ_kotlin.ui.mvp.delegate.HomeDelegate
import com.alway.lequ_kotlin.ui.mvp.delegate.ViewPagerDelegate
import com.alway.lequ_kotlin.ui.mvp.model.MainModel
import com.alway.lequ_kotlin.ui.mvp.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


/**
 * 创建人: Jeven
 * 邮箱:   Osboenjie@163.com
 * 功能:  主体程序
 */

class MainDelegate: LeQuDelegate(), NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    override fun setLayout(): Any {
        return R.layout.activity_main
    }

    override fun initPersenter() = MainPresenter(MainModel(), this)


    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        ActionBarDrawerToggle(_mActivity, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
                .syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(R.id.nav_home)
        loadRootFragment(R.id.fl_container, HomeDelegate())
    }

    fun onOpenDrawer() {
        if (!drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
