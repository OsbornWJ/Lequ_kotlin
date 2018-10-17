package com.alway.lequ_kotlin.ui.mvp.presenter

import com.alway.lequ_kotlin.ui.mvp.base.BasePersenter
import com.alway.lequ_kotlin.ui.mvp.contract.MainContract
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

@Suppress("UNCHECKED_CAST")
/**
 * 创建人: Jeven
 * 邮箱:   Osboenjie@163.com
 * 功能:
 */

class MainPresenter
constructor(model: MainContract.Model, rootView: MainContract.View): BasePersenter<MainContract.View, MainContract.Model>(model, rootView) {

    var provider = rootView as? LifecycleProvider<ActivityEvent>

    fun test() {

    }

}