package com.alway.lequ_kotlin.utils

import okhttp3.HttpUrl


/**
 * 创建人: Jeven
 * 邮箱:   liaowenjie@sto.cn
 * 功能:
 */

class UrlUtils {

    companion object {
        fun checkUrl(url: String): HttpUrl {
            val parseUrl = HttpUrl.parse(url)
            return parseUrl ?: throw Exception(url)
        }
    }

}
