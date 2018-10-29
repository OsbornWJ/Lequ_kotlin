package com.alway.lequ_kotlin.ui.mvp.delegate

import android.os.Bundle
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.BaseDelegate

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:  发现页面
 */
class DiscoveryDelegate : BaseDelegate() {

    override fun setLayout(): Any {
        return R.layout.comm_recycler_data
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
//        refreshLayout.refreshHeader = ClassicsHeader(_mActivity)
    }

    override fun initPersenter() {

    }
}
