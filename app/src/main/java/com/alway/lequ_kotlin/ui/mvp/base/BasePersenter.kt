package com.alway.lequ_kotlin.ui.mvp.base

/**
 * 创建人: Jeven
 * 邮箱:   Osboenjie@163.com
 * 功能:
 */

open class BasePersenter<V: IView, M: IModel>(var mModel: M?, var mRootView: V?) : IPersenter {

    override fun dettachView() {
        mRootView = null
    }

    override fun onStart() {

    }

    override fun onDestory() {
        mModel!!.onDestroy()
        mModel = null
    }

    init {
        checkNotNull(mModel){"model conot be null"}
        checkNotNull(mRootView){"rootView conot be null"}
        onStart()
    }
}