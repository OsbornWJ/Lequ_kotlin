package com.alway.lequ_kotlin.ui.base

import android.arch.lifecycle.LifecycleObserver

/**
 * 创建人: Jeven
 * 邮箱:   liaowenjie@sto.cn
 * 功能:
 */
open class BasePersenter<V: IView, M: IModel> : IPersenter {

    init {
        onStart()
    }

    override fun onStart() {

    }

    override fun onDestory() {

    }




}