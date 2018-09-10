package com.alway.lequ_kotlin.net

import com.alway.lequ_kotlin.config.ConfigKeys
import com.alway.lequ_kotlin.config.LeQu
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 创建人: Jeven
 * 邮箱:   liaowenjie@sto.cn
 * 功能:
 */
class RestCretor {

    /**
     * 构建OkHttp
     */
    private object OkhttpHolder {
        private val TIME_OUT = 60L
        private val BUILDER = OkHttpClient.Builder()
        private val INTERCEPTORS = LeQu.getConfiguration<ArrayList<Interceptor>>(ConfigKeys.INTERCEPTOR)

        private fun addInterceptor() : OkHttpClient.Builder {
            if (!INTERCEPTORS.isEmpty()) {
                for (interceptor in INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor)
                }
            }
            return BUILDER
        }

        val OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.MINUTES)
                .build()!!
    }

    /**
     * 构建retrofit
     */
    private object RetrofitHolder {
        private val BASE_URL = LeQu.getConfiguration<String>(ConfigKeys.API_HOST)
        val RETROFIT_CLIENT = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkhttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        fun getRetrofit() : Retrofit {
            return RetrofitHolder.RETROFIT_CLIENT
        }
    }


}