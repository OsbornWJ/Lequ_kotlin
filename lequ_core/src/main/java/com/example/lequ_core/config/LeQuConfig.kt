package com.example.lequ_core.config

import android.app.Activity
import com.example.lequ_core.utils.UrlUtils
import com.joanzapata.iconify.IconFontDescriptor
import com.joanzapata.iconify.Iconify
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import okhttp3.HttpUrl
import okhttp3.Interceptor
import java.util.ArrayList
import java.util.HashMap

/**
 * 创建人：wenjie on 2018/6/6
 * 邮箱： Osbornjie@163.com
 * 功能：
 */

class LeQuConfig private constructor() {


    internal val  leQuConfigs: HashMap<Any, Any>
        get() = LEQU_CONFIGS

    init {
        LEQU_CONFIGS[ConfigKeys.CONFIG_READY] = false
    }

    private object Holder {
        val INSTANCE = LeQuConfig()
    }

    fun configure() {
        initIcons()
        Logger.addLogAdapter(AndroidLogAdapter())
        LEQU_CONFIGS.put(ConfigKeys.CONFIG_READY, true)
    }

    fun withApiHost(host: String): LeQuConfig {
        LEQU_CONFIGS.put(ConfigKeys.API_HOST, UrlUtils.checkUrl(host))
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
        LEQU_CONFIGS[ConfigKeys.INTERCEPTOR] = INTERCEPTORS
        return this
    }

    fun withInterceptors(interceptors: ArrayList<Interceptor>): LeQuConfig {
        INTERCEPTORS.addAll(interceptors)
        LEQU_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS)
        return this
    }

    fun withActivity(activity: Activity): LeQuConfig {
        LEQU_CONFIGS[ConfigKeys.ACTIVITY] = activity
        return this
    }

    fun withDomain(domainName : String, domainUrl : String) : LeQuConfig {
        checkNotNull(domainName) { "State must be set beforehand" }
        checkNotNull(domainUrl) {"domainUrl connot be null"}
        HEADERS_DOMAIN[domainName] = UrlUtils.checkUrl(domainUrl)
        LEQU_CONFIGS[ConfigKeys.HEADER_DOMAIN] = HEADERS_DOMAIN
        return this
    }

    private fun checkConfiguration() {
        val isReady = LEQU_CONFIGS[ConfigKeys.CONFIG_READY] as Boolean
        if (!isReady) {
            throw RuntimeException("Configuration is not ready,call configure")
        }
    }

    internal fun <T> getConfiguration(key: Any): T {
        checkConfiguration()
        LEQU_CONFIGS[key] ?: throw NullPointerException(key.toString() + " IS NULL")
        return LEQU_CONFIGS[key] as T
    }

    companion object {

        private val LEQU_CONFIGS = HashMap<Any, Any>()
        private val ICONS = ArrayList<IconFontDescriptor>()
        private val INTERCEPTORS = ArrayList<Interceptor>()
        private val HEADERS_DOMAIN = HashMap<String, HttpUrl>()

        internal val instance: LeQuConfig
            get() = Holder.INSTANCE
    }


}