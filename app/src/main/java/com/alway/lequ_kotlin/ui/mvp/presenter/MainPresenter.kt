package com.alway.lequ_kotlin.ui.mvp.presenter

import com.alway.lequ_kotlin.ui.mvp.base.BasePersenter
import com.alway.lequ_kotlin.ui.mvp.contract.MainContract

@Suppress("UNCHECKED_CAST")
/**
 * 创建人: Jeven
 * 邮箱:   Osboenjie@163.com
 * 功能:
 */

class MainPresenter
constructor(model: MainContract.Model, rootView: MainContract.View): BasePersenter<MainContract.View, MainContract.Model>(model, rootView) {

/*    fun test() {

        Observable.interval(1, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView!!, ActivityEvent.PAUSE))
                .subscribe({ ToastUtils.showToastShrot("hhhh")})
    }*/

}