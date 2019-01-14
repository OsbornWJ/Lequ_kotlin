package com.alway.lequ_kotlin.ui.mvp.presenter

import android.annotation.SuppressLint
import com.alway.lequ_kotlin.http.Constant
import com.alway.lequ_kotlin.ui.lifecycle.RxLifecycleUtils
import com.alway.lequ_kotlin.ui.mvp.base.BasePersenter
import com.alway.lequ_kotlin.ui.mvp.contract.BeautyContract
import com.alway.lequ_kotlin.ui.mvp.model.BeautyModel
import com.alway.lequ_kotlin.ui.mvp.model.HomeModel
import com.example.lequ_core.utils.ToastUtils
import com.facebook.stetho.common.LogUtil
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class BeautyPresenter: BasePersenter<BeautyContract.View>() {

    private val mModel: BeautyModel by lazy { BeautyModel() }

    @SuppressLint("CheckResult")
    fun getGirls(count: Int, page: Int) {
        checkNotNull(mRootView) {"mRootView is null"}
        requireNotNull(mModel) { "mModel mModel is null" }.getGirls(Constant.GANHUO_FULI, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { mRootView.onLoadFinally() }
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, FragmentEvent.DESTROY_VIEW))
                .subscribe({ result ->
                    if (!result.isError) {
                        mRootView.onBeautyListSucc(result)
                    } else ToastUtils.showToastShrot("加载错误！") },
                    {error -> LogUtil.e(error.message)})
    }
}