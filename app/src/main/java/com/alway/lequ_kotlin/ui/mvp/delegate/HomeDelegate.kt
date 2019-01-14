package com.alway.lequ_kotlin.ui.mvp.delegate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.http.entity.Categories
import com.alway.lequ_kotlin.ui.base.LeQuDelegate
import com.alway.lequ_kotlin.ui.mvp.contract.HomeContract
import com.alway.lequ_kotlin.ui.mvp.presenter.HomePresenter
import com.alway.lequ_kotlin.utils.ImageLoad
import com.alway.lequ_kotlin.view.TabPagerAdapter
import com.example.lequ_core.config.LeQu
import kotlinx.android.synthetic.main.fragment_home_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.lang.ref.WeakReference

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class HomeDelegate: LeQuDelegate(), HomeContract.View, ViewPager.OnPageChangeListener {

    var mAdapter: TabPagerAdapter? = null
    var mFragments = ArrayList<Fragment>()

    var mTitle = arrayListOf("发现", "推荐", "日报")
    var mCategorys = ArrayList<CategoryListEntity>()
    private var currentIndex = 0

    override fun setLayout(): Any = R.layout.fragment_home_layout

    private val mPresenter: HomePresenter by lazy { HomePresenter() }

    init {
        mPresenter.attachView(this)
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        it_right.setOnClickListener { (getParentDelegate() as MainDelegate).onOpenDrawer() }
        mPresenter.categories()
    }

    class CategoryListEntity(var category_id: String?, var name: String?)

    override fun getCategoriesSuccess(data: List<Categories>) {
        val list = data.toMutableList()
        val tabs = ArrayList<Categories>()
        var tabId: Long = 1001
        for (s in mTitle) {
            val categories = Categories(tabId, s)
            tabs.add(categories)
            tabId ++
        }
        list.addAll(0, tabs)
        for (item in list) {
            when {
                item.id == 1001L -> mFragments.add(DiscoveryDelegate())
                item.id == 1002L -> mFragments.add(RecommendDelegate())
                item.id == 1003L -> mFragments.add(FeedDelegate())
                else -> {
                    mFragments.add(CategoryDelegate.newInstance(item.id.toString()))
                    mTitle.add(item.name!!)
                }
            }
            mCategorys.add(CategoryListEntity(item.id.toString(), item.name))
        }
        mAdapter = TabPagerAdapter(childFragmentManager, mFragments, mTitle)
        page_container.adapter = mAdapter
        tab_layout.setViewPager(page_container, mTitle.toArray(arrayOfNulls<String>(0)))
        page_container.offscreenPageLimit = 3
        page_container.currentItem = 0
        page_container.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

    override fun onPageSelected(position: Int) {
        if (mAdapter != null && mCategorys.size - 1 >= position) {
            currentIndex = position
            tab_layout.setCurrentTab(currentIndex, true)
        }
        ImageLoad.clearCache(WeakReference(LeQu.applicationContext))
    }

    override fun onDestroy() {
        mPresenter.dettachView()
        super.onDestroy()
    }
}