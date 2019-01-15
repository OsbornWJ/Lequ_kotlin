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
import com.moment.eyepetizer.utils.UriUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.comm_recycler_data.*

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class CategoryDelegate : LeQuDelegate(), DiscoveryContract.View {

    var start_num: Int = 0
    var num: Int = 10
    var categoryId: String = ""
    var isRefresh: Boolean = true
    var mAdapter: MultiTypeAdapter? = null

    private val mPresenter: DiscoveryPresenter by lazy { DiscoveryPresenter()}

    init {
        mPresenter.attachView(this)
    }

    companion object {
        fun newInstance(categoryId: String): CategoryDelegate {
            val bundle = Bundle()
            bundle.putString("category", categoryId)
            val delegate = CategoryDelegate()
            delegate.arguments = bundle
            return delegate
        }
    }

    override fun setLayout(): Any  = R.layout.comm_recycler_data

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        categoryId = arguments!!.getString("category")
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        smart_refreshLayout.setRefreshHeader(ClassicsHeader(_mActivity))
        smart_refreshLayout.setRefreshFooter(ClassicsFooter(_mActivity))
        smart_refreshLayout.setEnableAutoLoadMore(true)
        smart_refreshLayout.autoRefresh()
        smart_refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                isRefresh = false
                initData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                start_num = 0
                num = 10
                isRefresh = true
                initData()
            }

        })

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
        requireNotNull(mPresenter) {"${CategoryDelegate::class.java.simpleName} presenter is null"}.category(categoryId.toInt(), start_num, num)
    }

    override fun onDiscoverySucc(result: Result) {
        if (!TextUtils.isEmpty(result.nextPageUrl)) {
            start_num = UriUtils().parseCategoryUri(result.nextPageUrl.toString()).start
            num = UriUtils().parseCategoryUri(result.nextPageUrl.toString()).num
        }
        smart_refreshLayout.finishRefresh()
        smart_refreshLayout.finishLoadMore(0, true, TextUtils.isEmpty(result.nextPageUrl))

        if (result.itemList!!.isEmpty()) return
        val start = mAdapter!!.itemCount
        if (isRefresh) {
            mAdapter!!.clearAll()
            mAdapter!!.notifyItemRangeRemoved(0, start)
            mAdapter!!.addAll(result.itemList as ArrayList<Result.ItemList>?)
            mAdapter!!.notifyItemRangeInserted(0, result.itemList!!.size)
        } else {
            mAdapter!!.addAll(result.itemList as ArrayList<Result.ItemList>?)
            mAdapter!!.notifyItemRangeInserted(start, result.itemList!!.size)
        }
    }

    override fun onDiscoveryFail(error: Throwable?) {
        smart_refreshLayout.finishRefresh()
        smart_refreshLayout.finishLoadMore(false)
    }

}