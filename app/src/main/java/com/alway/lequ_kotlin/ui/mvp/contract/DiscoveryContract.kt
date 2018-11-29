package com.alway.lequ_kotlin.ui.mvp.contract

import com.alway.lequ_kotlin.http.entity.Result
import com.alway.lequ_kotlin.ui.mvp.base.IModel
import com.alway.lequ_kotlin.ui.mvp.base.IView
import io.reactivex.Observable

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class DiscoveryContract {

    interface View: IView {
        fun initData()
        fun onDiscoverySucc(result: Result)
        fun onDiscoveryFail(error: Throwable?)
    }

    interface Model: IModel {

        fun discovery(): Observable<Result>

        fun allRec(page: Int): Observable<Result>

        fun feed(date: Long): Observable<Result>

        fun category(id: Int, start: Int, num: Int): Observable<Result>
    }

}
