package com.example.lequ_core.utils

import android.content.Context
import android.widget.Toast
import com.example.lequ_core.config.LeQu

/**
 * 创建人：wenjie on 2018/6/7
 * 邮箱： Osbornjie@163.com
 * 功能：
 */
object ToastUtils {

    private var mToast: Toast? = null;

    fun showToastShrot(makeText: String) {
        Toast.makeText(LeQu.applicationContext, makeText, Toast.LENGTH_SHORT).show()
    }

    /**
     * 单例 toast
     *
     * @param string
     */
    fun makeText(context: Context, string: String) {
        if (mToast == null) {
            mToast = Toast.makeText(context, string, Toast.LENGTH_SHORT)
        }
        mToast!!.setText(string)
        mToast!!.show()
    }

}