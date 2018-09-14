package com.alway.lequ_kotlin.ui.base

/**
 * 创建人: Jeven
 * 邮箱:   liaowenjie@sto.cn
 * 功能:
 */
open class BasePersenter<V: IView, M: IModel> : IPersenter {

    protected var mModel: M? = null
    protected var mRootView: V? = null

    constructor(model: M, rootView: V) {
        checkNotNull(model){"model conot be null"}
        checkNotNull(rootView){"rootView conot be null"}
        this.mModel = model
        this.mRootView = rootView
        onStart()
    }

    override fun onStart() {

    }

    override fun onDestory() {
        mModel!!.onDestroy()
        mModel = null
        mRootView = null
    }




}