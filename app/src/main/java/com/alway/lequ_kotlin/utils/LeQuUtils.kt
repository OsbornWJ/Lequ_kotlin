package com.alway.lequ_kotlin.utils

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
