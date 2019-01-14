package com.alway.lequ_kotlin.http.api

import com.alway.lequ_kotlin.http.Constant
import com.alway.lequ_kotlin.http.Constant.Companion.GANHUO_API
import com.example.lequ_core.net.RestCretor.Companion.DOMAIN
import com.xujie.lequ.data.bean.GirlsBean
import io.reactivex.Observable
import retrofit2.http.*


/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
interface GankService {

    @Headers("$DOMAIN: $GANHUO_API") // Add the Domain-Name header
    @GET("{type}/{count}/{page}")
    fun getGirls(
            @Path("type") type: String,
            @Path("count") count: Int,
            @Path("page") page: Int
    ): Observable<GirlsBean>

}