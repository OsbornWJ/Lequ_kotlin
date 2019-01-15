package com.alway.lequ_kotlin.ui.mvp.delegate.beauty

import android.os.Bundle
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.LeQuDelegate

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class BeautyDatasDelegate : LeQuDelegate() {

    override fun setLayout(): Any = R.layout.fragment_beauty_datas

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initToobar(rootView)
    }


}