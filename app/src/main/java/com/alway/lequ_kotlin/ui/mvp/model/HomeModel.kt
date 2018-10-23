package com.alway.lequ_kotlin.ui.mvp.model

import com.alway.lequ_kotlin.http.api.Service
import com.alway.lequ_kotlin.http.entity.Categories
import com.alway.lequ_kotlin.ui.mvp.base.BaseModel
import com.alway.lequ_kotlin.ui.mvp.contract.HomeContract
import com.example.lequ_core.net.RestCretor
import io.reactivex.Observable

/**
 * 创建人: Jeven
 * 邮箱:   Osboenjie@163.com
 * 功能:
 */

class HomeModel : BaseModel(), HomeContract.Model {

    override fun categories(): Observable<List<Categories>> =  RestCretor.getRetrofit().create(Service::class.java)
            .categories()

}