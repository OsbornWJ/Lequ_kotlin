package com.alway.lequ_kotlin.app

import android.support.multidex.MultiDexApplication
import com.alway.lequ_kotlin.BuildConfig
import com.alway.lequ_kotlin.config.LeQu
import com.alway.lequ_kotlin.config.iconfont.FontEcModule
import com.alway.lequ_kotlin.net.RestCretor
import com.alway.lequ_kotlin.net.interceptor.CacheInterceptor
import com.alway.lequ_kotlin.net.interceptor.HeaderInterceptor
import com.joanzapata.iconify.fonts.FontAwesomeModule
import okhttp3.logging.HttpLoggingInterceptor

/**
 * 创建人：wenjie on 2018/6/7
 * 邮箱： Osbornjie@163.com
 * 功能：
 */
class LeQuApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        LeQu.init(this)
                .withApiHost(BuildConfig.BASE_URL)
//                .withDomain()
                .withIcon(FontAwesomeModule())
                .withIcon(FontEcModule())
                .withInterceptor(HeaderInterceptor())
                .withInterceptor(CacheInterceptor(this))
                .withInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .configure()
    }

}