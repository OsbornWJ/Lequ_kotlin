package com.alway.lequ_kotlin.ui.mvp.presenter

import android.annotation.SuppressLint
import com.alway.lequ_kotlin.http.entity.Result
import com.alway.lequ_kotlin.ui.lifecycle.RxLifecycleUtils
import com.alway.lequ_kotlin.ui.mvp.base.BasePersenter
import com.alway.lequ_kotlin.ui.mvp.contract.DiscoveryContract
import com.alway.lequ_kotlin.ui.mvp.model.DiscoveryModel
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
@SuppressLint("CheckResult")
class DiscoveryPresenter: BasePersenter<DiscoveryContract.View>() {

    private val mModel: DiscoveryModel by lazy { DiscoveryModel() }

    fun discovery() {
        mModel.discovery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView!!, FragmentEvent.DESTROY_VIEW))
                .subscribe({t: Result ->  mRootView!!.onDiscoverySucc(t)},
                        {error: Throwable -> mRootView!!.onDiscoveryFail(error)})
    }

    fun allRec(page: Int) {
        mModel.allRec(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView!!, FragmentEvent.DESTROY_VIEW))
                .subscribe({t: Result ->  mRootView!!.onDiscoverySucc(t)},
                        {error: Throwable -> mRootView!!.onDiscoveryFail(error)})
    }

    fun feed(date: Long) {
        mModel.feed(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView!!, FragmentEvent.DESTROY_VIEW))
                .subscribe({t: Result ->  mRootView!!.onDiscoverySucc(t)},
                        {error: Throwable -> mRootView!!.onDiscoveryFail(error)})
    }

    fun category(id: Int, start: Int, num: Int) {
        mModel.category(id, start, num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView!!, FragmentEvent.DESTROY_VIEW))
                .subscribe({t: Result ->  mRootView!!.onDiscoverySucc(t)},
                        {error: Throwable -> mRootView!!.onDiscoveryFail(error)})

    }

}