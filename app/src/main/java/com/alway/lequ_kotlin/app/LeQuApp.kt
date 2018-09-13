package com.alway.lequ_kotlin.app

import android.os.Environment
import android.support.multidex.MultiDexApplication
import com.alway.lequ_kotlin.BuildConfig
import com.alway.lequ_kotlin.exception.MyUEHandler
import com.alway.lequ_kotlin.http.remote.DomainHeaderInterceptor
import com.example.lequ_core.config.LeQu
import com.example.lequ_core.config.iconfont.FontEcModule
import com.example.lequ_core.net.interceptor.CacheInterceptor
import com.joanzapata.iconify.fonts.FontAwesomeModule
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

/**
 * 创建人：wenjie on 2018/6/7
 * 邮箱： Osbornjie@163.com
 * 功能：a
 */
class LeQuApp: MultiDexApplication() {

    companion object {
        val PATH_ERROR_LOG = File(Environment.getExternalStorageDirectory(), "/LeQu/crash")
    }

    private var leQuApp: LeQuApp? = null

    override fun onCreate() {
        super.onCreate()

        leQuApp = this

        LeQu.init(this)
                .withApiHost(BuildConfig.BASE_URL)
//                .withDomain()
                .withIcon(FontAwesomeModule())
                .withIcon(FontEcModule())
                .withInterceptor(DomainHeaderInterceptor())
                .withInterceptor(CacheInterceptor(this))
                .withInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .configure()

        if (BuildConfig.DEBUG) {
            crashException()
        }
    }

    private fun crashException() {
        Thread.setDefaultUncaughtExceptionHandler(MyUEHandler(leQuApp!!))
    }

}