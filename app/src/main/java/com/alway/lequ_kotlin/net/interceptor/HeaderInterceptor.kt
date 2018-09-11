package com.alway.lequ_kotlin.net.interceptor

import android.text.TextUtils
import com.alway.lequ_kotlin.config.ConfigKeys
import com.alway.lequ_kotlin.config.LeQu
import com.alway.lequ_kotlin.net.RestCretor
import com.alway.lequ_kotlin.net.RestCretor.Companion.DOMAIN
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 创建人: Jeven
 * 邮箱:   liaowenjie@sto.cn
 * 功能:
 */

class HeaderInterceptor : Interceptor {

    private val HEADER_DOMAIN = LeQu.lequConfig.getConfiguration<Map<String, HttpUrl>>(ConfigKeys.HEADER_DOMAIN)

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
        return HEADER_DOMAIN[domainName]
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

}