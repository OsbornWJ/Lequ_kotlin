package com.alway.lequ_kotlin.ui.mvp.delegate

import android.os.Bundle
import android.view.View
import com.alway.lequ_kotlin.ui.base.LeQuDelegate
import com.alway.lequ_kotlin.ui.mvp.presenter.HomePresenter

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class HomeDelegate: LeQuDelegate<HomePresenter>() {

    override fun setLayout(): Any {
        return 1
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

}