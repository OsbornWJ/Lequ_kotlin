package com.alway.lequ_kotlin.ui.mvp.base

/**
 * 创建人: Jeven
 * 邮箱:   Osboenjie@163.com
 * 功能:
 */

abstract class BasePersenter<V: IView>: IPersenter {

    protected lateinit var mRootView: V

    override fun attachView(view: IView?) {
        this.mRootView = view as V
    }

    override fun dettachView() {

    }
}