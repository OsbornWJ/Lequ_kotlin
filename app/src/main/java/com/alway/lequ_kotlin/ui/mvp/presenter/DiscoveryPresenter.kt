package com.alway.lequ_kotlin.ui.mvp.presenter

import com.alway.lequ_kotlin.http.entity.Result
import com.alway.lequ_kotlin.ui.lifecycle.RxLifecycleUtils
import com.alway.lequ_kotlin.ui.mvp.base.BasePersenter
import com.alway.lequ_kotlin.ui.mvp.contract.DiscoveryContract
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class DiscoveryPresenter constructor(model: DiscoveryContract.Model, rootView: DiscoveryContract.View): BasePersenter<DiscoveryContract.View, DiscoveryContract.Model>(model, rootView) {

    fun discovery() {
        mModel!!.discovery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView!!, FragmentEvent.DESTROY_VIEW))
                .subscribe({t: Result ->  mRootView!!.onDiscoverySucc(t)},
                        {error: Throwable -> mRootView!!.onDiscoveryFail(error)})
    }

}