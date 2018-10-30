package com.alway.lequ_kotlin.ui.mvp.delegate

import android.os.Bundle
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.http.entity.Result
import com.alway.lequ_kotlin.ui.base.LeQuDelegate
import com.alway.lequ_kotlin.ui.mvp.contract.DiscoveryContract
import com.alway.lequ_kotlin.ui.mvp.model.DiscoveryModel
import com.alway.lequ_kotlin.ui.mvp.presenter.DiscoveryPresenter
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.comm_recycler_data.*

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:  发现页面
 */
class DiscoveryDelegate : LeQuDelegate<DiscoveryPresenter>(), DiscoveryContract.View {

    override fun setLayout(): Any {
        return R.layout.comm_recycler_data
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        refreshLayout.setEnableAutoLoadMore(true)
        refreshLayout.setRefreshHeader(ClassicsHeader(_mActivity))
        refreshLayout.setRefreshFooter(ClassicsFooter(_mActivity))
        refreshLayout.setOnRefreshListener {  }

    }

    override fun initPersenter() {
        mPresenter = DiscoveryPresenter(DiscoveryModel(), this)
    }

    override fun initData() {
        requireNotNull(mPresenter, {"${DiscoveryDelegate::class.java.simpleName} presenter is null"}).discovery()
    }

    override fun onDiscoverySucc(result: Result) {

    }

    override fun onDiscoveryFail(error: Throwable?) {

    }
}
