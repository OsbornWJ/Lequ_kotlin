package com.alway.lequ_kotlin.utils

import android.widget.Toast
import com.alway.lequ_kotlin.config.LeQu

/**
 * 创建人：wenjie on 2018/6/7
 * 邮箱： Osbornjie@163.com
 * 功能：
 */
object ToastUtils {

    fun showToastShrot(makeText: String) {
        Toast.makeText(LeQu.applicationContext, makeText, Toast.LENGTH_SHORT).show()
    }

}