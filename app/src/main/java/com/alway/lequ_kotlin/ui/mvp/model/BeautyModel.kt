package com.alway.lequ_kotlin.ui.mvp.model

import com.alway.lequ_kotlin.http.api.GankService
import com.alway.lequ_kotlin.ui.mvp.base.BaseModel
import com.alway.lequ_kotlin.ui.mvp.contract.BeautyContract
import com.example.lequ_core.net.RestCretor
import com.xujie.lequ.data.bean.GirlsBean
import io.reactivex.Observable

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class BeautyModel: BaseModel(), BeautyContract.Model {

    override fun getGirls(type: String, count: Int, page: Int): Observable<GirlsBean> = RestCretor.getRetrofit()
            .create(GankService::class.java).getGirls(type, count, page)
}