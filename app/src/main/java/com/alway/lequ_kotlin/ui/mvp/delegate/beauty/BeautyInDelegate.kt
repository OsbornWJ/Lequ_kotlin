package com.alway.lequ_kotlin.ui.mvp.delegate.beauty

import android.os.Bundle
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.LeQuDelegate
import com.alway.lequ_kotlin.ui.mvp.delegate.beauty.BeautyDelegate

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:   beauty in入口
 */
class BeautyInDelegate : LeQuDelegate() {

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        loadRootFragment(R.id.fl_container, BeautyDelegate())
    }

    override fun setLayout(): Any = R.layout.simple_comm_fl_container

    /**
     * 处理回退事件
     *
     * @return
     */
    override fun onBackPressedSupport(): Boolean {
        if (childFragmentManager.backStackEntryCount > 1) {
            popChild()
        } else {
            /*if (this is ZhihuFirstFragment) {   // 如果是 第一个Fragment 则退出app
                _mActivity!!.finish()
            } else {                                    // 如果不是,则回到第一个Fragment
                _mBackToFirstListener!!.onBackToFirstFragment()
            }*/
        }
        return true
    }

}