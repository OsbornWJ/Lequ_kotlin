package com.alway.lequ_kotlin.ui.mvp.model

import com.alway.lequ_kotlin.http.api.Service
import com.alway.lequ_kotlin.http.entity.Result
import com.alway.lequ_kotlin.ui.mvp.base.BaseModel
import com.alway.lequ_kotlin.ui.mvp.contract.DiscoveryContract
import com.example.lequ_core.net.RestCretor
import io.reactivex.Observable

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class DiscoveryModel: BaseModel(), DiscoveryContract.Model  {

    override fun discovery(): Observable<Result> = RestCretor.getRetrofit().create(Service::class.java).discovery()


}