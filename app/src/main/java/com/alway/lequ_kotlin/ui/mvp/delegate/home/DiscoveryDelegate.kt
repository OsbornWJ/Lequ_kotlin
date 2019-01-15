package com.alway.lequ_kotlin.ui.mvp.delegate.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.http.entity.Result
import com.alway.lequ_kotlin.ui.base.BaseDelegate
import com.alway.lequ_kotlin.ui.base.LeQuDelegate
import com.alway.lequ_kotlin.ui.mvp.contract.DiscoveryContract
import com.alway.lequ_kotlin.ui.mvp.presenter.DiscoveryPresenter
import com.bumptech.glide.Glide
import com.example.lequ_core.config.LeQu
import com.moment.eyepetizer.home.adapter.MultiTypeAdapter
import com.moment.eyepetizer.home.adapter.StartActionListener
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.comm_recycler_data.*

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:  发现页面
 */
class DiscoveryDelegate : LeQuDelegate(), DiscoveryContract.View {

    var mAdapter: MultiTypeAdapter? = null

    private val mPresenter: DiscoveryPresenter by lazy { DiscoveryPresenter()}

    override fun setLayout(): Any {
        return R.layout.comm_recycler_data
    }

    init {
        mPresenter.attachView(this)
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) = Unit

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        smart_refreshLayout.setRefreshHeader(ClassicsHeader(_mActivity))
        smart_refreshLayout.setRefreshFooter(ClassicsFooter(_mActivity))
        smart_refreshLayout.setOnRefreshListener { initData() }
        smart_refreshLayout.setEnableAutoLoadMore(true)
        smart_refreshLayout.autoRefresh()
        dataView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    2 -> Glide.with(LeQu.applicationContext).pauseRequests()
                    0, 1 -> Glide.with(LeQu.applicationContext).resumeRequests()
                }
            }
        })
        val list = ArrayList<Result.ItemList>()
        dataView.layoutManager = LinearLayoutManager(_mActivity)
        mAdapter = MultiTypeAdapter(list, _mActivity!!, object : StartActionListener {
            override fun jumpAction(delegate: BaseDelegate) {
                (parentFragment as BaseDelegate).start(delegate)
            }
        })
        dataView.adapter = mAdapter
    }


    override fun initData() {
        requireNotNull(mPresenter) {"${DiscoveryDelegate::class.java.simpleName} presenter is null"}.discovery()
    }

    override fun onDiscoverySucc(result: Result) {
        val start = mAdapter!!.itemCount
        if (result.itemList!!.isEmpty()) return
        mAdapter!!.clearAll()
        mAdapter!!.notifyItemRangeRemoved(0, start)
        mAdapter!!.addAll(result.itemList as ArrayList<Result.ItemList>?)
        mAdapter!!.notifyItemRangeInserted(0, result.itemList!!.size)
        smart_refreshLayout.finishRefresh()
        smart_refreshLayout.finishLoadMore(0, true, TextUtils.isEmpty(result.nextPageUrl))
    }

    override fun onDiscoveryFail(error: Throwable?) {
        smart_refreshLayout.finishRefresh()
        smart_refreshLayout.finishLoadMore(false)
    }

    override fun onDestroy() {
        mPresenter.dettachView()
        super.onDestroy()
    }
}
