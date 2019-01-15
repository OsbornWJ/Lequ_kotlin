package com.alway.lequ_kotlin.ui.mvp.delegate.beauty

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.LeQuDelegate
import com.alway.lequ_kotlin.ui.mvp.adapter.BaseAdapter
import com.alway.lequ_kotlin.ui.mvp.contract.BeautyContract
import com.alway.lequ_kotlin.ui.mvp.presenter.BeautyPresenter
import com.alway.lequ_kotlin.utils.ImageLoad
import com.bumptech.glide.Glide
import com.example.lequ_core.config.LeQu
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.xujie.lequ.data.bean.GirlsBean
import kotlinx.android.synthetic.main.fragment_beauty_layout.*
import kotlinx.android.synthetic.main.item_girl.view.*


/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class BeautyDelegate : LeQuDelegate(), BeautyContract.View {

    var count = 20
    var page = 1
    var mdata = mutableListOf<GirlsBean.ResultsEntity>()
    private var mAdapter: BeautyAdapter? = null

    override fun setLayout(): Any = R.layout.fragment_beauty_layout

    private val mPresenter: BeautyPresenter by lazy { BeautyPresenter()}

    init {
        mPresenter.attachView(this)
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) = Unit

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        dataView.layoutManager = layoutManager
        dataView.itemAnimator = null
        smart_refreshLayout.setEnableAutoLoadMore(true)
        smart_refreshLayout.autoRefresh()
        smart_refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                if (mdata.size%count == 0) {
                    page ++
                    mPresenter.getGirls(count, page)
                }
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                mPresenter.getGirls(count, page)
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
        mAdapter = BeautyAdapter(mdata, getProxyActivity())
        mAdapter!!.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseAdapter<*>, view: View, position: Int) {
                start(BeautyDatasDelegate())
            }
        })
        dataView.adapter = mAdapter
    }

    override fun onBeautyListSucc(result: GirlsBean?) {
        if (result != null && result.results!!.isNotEmpty()) {
            val start = mAdapter!!.itemCount
            if (page == 1) {
                mAdapter!!.clearAll()
                mAdapter!!.notifyItemRangeRemoved(0, start)
                mAdapter!!.addAll(result.results as ArrayList<GirlsBean.ResultsEntity>?)
                mAdapter!!.notifyItemRangeInserted(0, result.results!!.size)
            } else {
                mAdapter!!.addAll(result.results as ArrayList<GirlsBean.ResultsEntity>?)
                mAdapter!!.notifyItemRangeInserted(start, result.results!!.size)
            }
            if (mdata.size == page*count) {
                smart_refreshLayout.setEnableLoadMore(true)
            } else smart_refreshLayout.setEnableLoadMore(false)
            mAdapter!!.notifyDataSetChanged()
        } else smart_refreshLayout.setEnableLoadMore(false)
    }

    override fun onLoadFinally() {
        smart_refreshLayout.finishLoadMore()
        smart_refreshLayout.finishRefresh()
    }

}

class BeautyAdapter(private var datas: MutableList<GirlsBean.ResultsEntity>, private var context: Context) : BaseAdapter<RecyclerView.ViewHolder>() {

    fun clearAll() = this.datas.clear()

    fun addAll(data: ArrayList<GirlsBean.ResultsEntity>?) {
        if (data == null) {
            return
        }
        this.datas.addAll(data)
    }

    override fun getItemCount(): Int = datas.size

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = BeautyItemView(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder : BeautyItemView = holder as BeautyItemView
        val itemBean = datas[position]
        itemBean.url?.let { ImageLoad.loadGirls(it, holder.itemGirl) }
    }


    fun BeautyItemView(parent: ViewGroup) : RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_girl, parent, false)
        return BeautyItemView(view)
    }

    inner class BeautyItemView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemGirl: ImageView = itemView.girl_img
    }
}

