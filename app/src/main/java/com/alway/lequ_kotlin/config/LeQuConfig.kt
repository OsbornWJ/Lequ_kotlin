package com.alway.lequ_kotlin.config

import android.app.Activity
import android.os.Handler
import com.joanzapata.iconify.IconFontDescriptor
import com.joanzapata.iconify.Iconify
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import java.util.ArrayList
import java.util.HashMap

/**
 * 创建人：wenjie on 2018/6/6
 * 邮箱： Osbornjie@163.com
 * 功能：
 */

class LeQuConfig private constructor() {


    internal val latteConfigs: HashMap<Any, Any>
        get() = LATTE_CONFIGS

    init {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, false)
        LATTE_CONFIGS.put(ConfigKeys.HANDLER, HANDLER)
    }

    private object Holder {
        val INSTANCE = LeQuConfig()
    }

    fun configure() {
        initIcons()
        Logger.addLogAdapter(AndroidLogAdapter())
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, true)
    }

    fun withApiHost(host: String): LeQuConfig {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST, host)
        return this
    }

    fun withLoaderDelayed(delayed: Long): LeQuConfig {
        LATTE_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed)
        return this
    }

    private fun initIcons() {
        if (ICONS.size > 0) {
            val initializer = Iconify.with(ICONS[0])
            for (i in 1 until ICONS.size) {
                initializer.with(ICONS[i])
            }
        }
    }

    fun withIcon(descriptor: IconFontDescriptor): LeQuConfig {
        ICONS.add(descriptor)
        return this
    }

    fun withInterceptor(interceptor: Interceptor): LeQuConfig {
        INTERCEPTORS.add(interceptor)
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS)
        return this
    }

    fun withInterceptors(interceptors: ArrayList<Interceptor>): LeQuConfig {
        INTERCEPTORS.addAll(interceptors)
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS)
        return this
    }

    fun withActivity(activity: Activity): LeQuConfig {
        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY, activity)
        return this
    }

    private fun checkConfiguration() {
        val isReady = LATTE_CONFIGS[ConfigKeys.CONFIG_READY] as Boolean
        if (!isReady) {
            throw RuntimeException("Configuration is not ready,call configure")
        }
    }

    internal fun <T> getConfiguration(key: Any): T {
        checkConfiguration()
        LATTE_CONFIGS[key] ?: throw NullPointerException(key.toString() + " IS NULL")
        return LATTE_CONFIGS[key] as T
    }

    companion object {

        private val LATTE_CONFIGS = HashMap<Any, Any>()
        private val HANDLER = Handler()
        private val ICONS = ArrayList<IconFontDescriptor>()
        private val INTERCEPTORS = ArrayList<Interceptor>()

        internal val instance: LeQuConfig
            get() = Holder.INSTANCE
    }


}