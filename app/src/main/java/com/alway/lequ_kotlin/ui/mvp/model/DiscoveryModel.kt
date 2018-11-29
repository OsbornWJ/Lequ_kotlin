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

    override fun allRec(page: Int): Observable<Result> = RestCretor.getRetrofit().create(Service::class.java).allRec(page)

    override fun feed(date: Long): Observable<Result> = RestCretor.getRetrofit().create(Service::class.java).feed(date, 2)

    override fun category(id: Int, start: Int, num: Int): Observable<Result> = RestCretor.getRetrofit().create(Service::class.java).category(id, start, num, parmaMap())

    fun parmaMap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map.put("udid", "1dad62050ee54c10965021ed1bff209cdee1f09e")
        map.put("vc", "256")
        map.put("vn", "3.14")
        map.put("deviceModel", "MIX%202")
        map.put("first_channel", "eyepetizer_yingyongbao_market")
        map.put("last_channel", "eyepetizer_yingyongbao_market")
        map.put("system_version_code", "25")
        return map
    }
}