package com.alway.lequ_kotlin.ui.mvp.presenter

import android.annotation.SuppressLint
import com.alway.lequ_kotlin.ui.lifecycle.RxLifecycleUtils
import com.alway.lequ_kotlin.ui.mvp.base.BasePersenter
import com.alway.lequ_kotlin.ui.mvp.base.IModel
import com.alway.lequ_kotlin.ui.mvp.base.IView
import com.alway.lequ_kotlin.ui.mvp.contract.HomeContract
import com.alway.lequ_kotlin.ui.mvp.model.HomeModel
import com.facebook.stetho.common.LogUtil
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class HomePresenter: BasePersenter<HomeContract.View>() {

    private val mModel: HomeModel by lazy { HomeModel() }

    @SuppressLint("CheckResult")
    fun categories() {
        checkNotNull(mRootView) {"mRootView is null"}
        requireNotNull(mModel) { "mModel mModel is null" }.categories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView!!, FragmentEvent.DESTROY_VIEW))
                .subscribe({result -> mRootView!!.getCategoriesSuccess(result)}
                ,{error -> LogUtil.e(error.message)})
    }

}