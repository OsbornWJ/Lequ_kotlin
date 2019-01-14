package com.alway.lequ_kotlin.app

import android.os.Environment
import android.support.multidex.MultiDexApplication
import com.alway.lequ_kotlin.BuildConfig
import com.alway.lequ_kotlin.exception.MyUEHandler
import com.alway.lequ_kotlin.http.Constant
import com.alway.lequ_kotlin.http.remote.DomainHeaderInterceptor
import com.example.lequ_core.config.LeQu
import com.example.lequ_core.config.iconfont.FontEcModule
import com.example.lequ_core.net.interceptor.CacheInterceptor
import com.example.lequ_core.utils.AppManager
import com.joanzapata.iconify.fonts.FontAwesomeModule
import me.yokeyword.fragmentation.Fragmentation
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File


/**
 * 创建人：wenjie on 2018/6/7
 * 邮箱： Osbornjie@163.com
 * 功能：
 */
class LeQuApp: MultiDexApplication() {

    companion object {
        val PATH_ERROR_LOG = File(Environment.getExternalStorageDirectory(), "/LeQu/crash")
    }

    private var leQuApp: LeQuApp? = null

    override fun onCreate() {
        super.onCreate()

        leQuApp = this

        AppManager.appManager!!.init(leQuApp!!)

        LeQu.init(this)
                .withApiHost(BuildConfig.BASE_URL)  //访问地址
                .withDomain(Constant.GANHUO_API, BuildConfig.GANHUO_API)                     //其它访问地址
                .withIcon(FontAwesomeModule())
                .withIcon(FontEcModule())
                .withInterceptor(DomainHeaderInterceptor())
                .withInterceptor(CacheInterceptor(this))
                .withInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .configure()

        if (BuildConfig.DEBUG) {
            crashException()
        }

        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
                /**
                 * 可以获取到[me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning]
                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                 */
                .handleException { }
                .install()

    }

    private fun crashException() {
        Thread.setDefaultUncaughtExceptionHandler(MyUEHandler(leQuApp!!))
    }

}