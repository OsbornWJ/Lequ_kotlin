@file:Suppress("PackageName")

package com.example.lequ_core.net.interceptor

import android.text.TextUtils
import com.example.lequ_core.net.RestCretor
import com.example.lequ_core.net.RestCretor.Companion.DOMAIN
import com.example.lequ_core.config.ConfigKeys
import com.example.lequ_core.config.LeQu
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 创建人: Jeven
 * 邮箱:   liaowenjie@sto.cn
 * 功能:  头部拦截器  转换baseUrl，如果头部有传参信息需要重写obtainHeader()
 */

open class HeaderInterceptor : Interceptor {

    private object Headerholder {
        val HEADER_DOMAIN = LeQu.lequConfig.getConfiguration<Map<String, HttpUrl>>(ConfigKeys.HEADER_DOMAIN)
    }

    private fun getBaseUrl(): HttpUrl {
        return LeQu.lequConfig.getConfiguration(ConfigKeys.API_HOST)
    }

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain!!.request()
        val domainName = obtainDomainNameFromHeaders(request)
        val newBuilder = request.newBuilder()

        val httpUrl: HttpUrl?
        if (!TextUtils.isEmpty(domainName)) {
            httpUrl = fetchDomain(domainName)
            newBuilder.removeHeader(DOMAIN)
        } else {
            httpUrl = getBaseUrl()
        }
        obtainHeader(domainName, newBuilder)
        if (null != httpUrl) {
            val newUrl = RestCretor.getUrlParser().parseUrl(httpUrl, request.url())
            return chain.proceed(newBuilder
                    .url(newUrl)
                    .build())
        }
        return chain.proceed(newBuilder.build())
    }

    private fun fetchDomain(domainName: String?) : HttpUrl? {
        checkNotNull(domainName){"domainName cannot be null"}
        return Headerholder.HEADER_DOMAIN[domainName]
    }

    /**
     * 从 [Request.header] 中取出 DomainName
     *
     * @param request [Request]
     * @return DomainName
     */
    private fun obtainDomainNameFromHeaders(request: Request): String? {
        val headers = request.headers(DOMAIN)
        if (headers == null || headers.size == 0)
            return null
        if (headers.size > 1)
            throw IllegalArgumentException("Only one Domain-Name in the headers")
        return request.header(DOMAIN)
    }

    /**
     * 为请求添加头部信息 （处理极端情况，各服务器的header可能有差）
     */
    open fun obtainHeader(domain: String?, builder: Request.Builder) {

    }

}