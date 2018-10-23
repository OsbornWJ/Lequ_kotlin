package com.alway.lequ_kotlin.ui.mvp.contract

import com.alway.lequ_kotlin.http.entity.Categories
import com.alway.lequ_kotlin.ui.mvp.base.IModel
import com.alway.lequ_kotlin.ui.mvp.base.IView
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class HomeContract{

    interface View: IView {
        fun getCategoriesSuccess(data: List<Categories>)
    }

    interface Model: IModel {

        fun categories(): Observable<List<Categories>>

    }

}