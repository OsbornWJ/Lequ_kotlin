package com.alway.lequ_kotlin.http.remote

import android.text.TextUtils
import com.example.lequ_core.net.interceptor.HeaderInterceptor
import okhttp3.Request

/**
 * 创建人: Jeven
 * 邮箱:   liaowenjie@sto.cn
 * 功能:
 */

class DomainHeaderInterceptor: HeaderInterceptor() {

    override fun obtainHeader(domain: String?, builder: Request.Builder) {
        if (TextUtils.isEmpty(domain)) {
            getDrfaultDomain(builder)
        }
    }

    fun getDrfaultDomain(builder: Request.Builder) {

    }
}
