package com.alway.lequ_kotlin.ui.mvp.presenter

import com.alway.lequ_kotlin.ui.lifecycle.RxLifecycleUtils
import com.alway.lequ_kotlin.ui.mvp.base.BasePersenter
import com.alway.lequ_kotlin.ui.mvp.contract.HomeContract
import com.facebook.stetho.common.LogUtil
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class HomePresenter constructor(model: HomeContract.Model, rootView: HomeContract.View): BasePersenter<HomeContract.View, HomeContract.Model>(model, rootView) {

    fun categories() {
        mModel!!.categories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView!!, FragmentEvent.DESTROY_VIEW))
                .subscribe({result -> mRootView!!.getCategoriesSuccess(result)}
                ,{error -> LogUtil.e(error.message)})
    }

}