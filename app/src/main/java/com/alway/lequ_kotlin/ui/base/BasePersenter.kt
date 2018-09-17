package com.alway.lequ_kotlin.ui.base

/**
 * 创建人: Jeven
 * 邮箱:   liaowenjie@sto.cn
 * 功能:
 */
open class BasePersenter<V: IView, M: IModel>(model: M, rootView: V) : IPersenter {

    protected var mModel: M? = model
    protected var mRootView: V? = rootView

    override fun onStart() {

    }

    override fun onDestory() {
        mModel!!.onDestroy()
        mModel = null
        mRootView = null
    }

    init {
        checkNotNull(model){"model conot be null"}
        checkNotNull(rootView){"rootView conot be null"}
        onStart()
    }


}