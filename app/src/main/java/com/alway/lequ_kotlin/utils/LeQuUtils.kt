package com.alway.lequ_kotlin.utils

import android.util.Base64
import com.alway.lequ_kotlin.http.entity.Result

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
 * 解码数据
 *
 * @param data 编码过的字符串
 * @return 原始数据
 */
fun decode(data: String?): ByteArray {
    return Base64.decode(data, Base64.URL_SAFE or Base64.NO_WRAP)
}