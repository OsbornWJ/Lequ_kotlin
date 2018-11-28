package com.alway.lequ_kotlin.ui.mvp.delegate

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.BaseDelegate
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class ViewPagerDelegate : BaseDelegate() {

    var type:String = ""

    companion object {
        val HOME_DELEGATE: String = "home_delegate"

        fun newInstance(type: String): ViewPagerDelegate {
            val bundle = Bundle()
            bundle.putString("type", type)
            val delegate = ViewPagerDelegate()
            delegate.arguments = bundle
            return delegate
        }
    }

    override fun setLayout(): Any {
        return R.layout.view_pager_delegate
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        type = arguments!!.getString("type")
        fetchBindView(type)
    }

    override fun initPersenter() {

    }

    fun fetchBindView(type: String) {
        if (TextUtils.isEmpty(type)) return
        when (type.hashCode()) {
            HOME_DELEGATE.hashCode() -> {
                loadRootFragment(R.id.fl_container, HomeDelegate())
            }
        }
    }
}
