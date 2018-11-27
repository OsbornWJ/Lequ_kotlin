package com.alway.lequ_kotlin.utils

import android.util.Base64
import com.alway.lequ_kotlin.http.entity.Result
import java.io.UnsupportedEncodingException

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */

fun getMultiType(position: Int, datas: ArrayList<Result.ItemList>): Int {
    val type: String = datas[position].type!!
    return type.hashCode()
}

/**
 * 编码字符串
 *
 * @param data 待编码字符串
 * @return 结果字符串
 */
val UTF_8 = "utf-8"
fun encodeToString(data: String?): String? {
    try {
        if (data != null) {
            return encodeToString(data.toByteArray(charset(UTF_8)))
        }
    } catch (e: UnsupportedEncodingException) {
        //never in
        e.printStackTrace()
    }

    return null
}

/**
 * 编码数据
 *
 * @param data 字节数组
 * @return 结果字符串
 */
fun encodeToString(data: ByteArray): String {
    return Base64.encodeToString(data, Base64.URL_SAFE or Base64.NO_WRAP)
}

/**
 * 解码数据
 *
 * @param data 编码过的字符串
 * @return 原始数据
 */
fun decode(data: String?): ByteArray {
    return Base64.decode(data, Base64.URL_SAFE or Base64.NO_WRAP)
}