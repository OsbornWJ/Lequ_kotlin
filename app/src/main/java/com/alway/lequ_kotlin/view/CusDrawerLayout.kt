package com.alway.lequ_kotlin.view

import android.content.Context
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class CusDrawerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : DrawerLayout(context, attrs) {

    var widthMeasure = 0
    var heightMeasure = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (widthMeasureSpec == 0 || heightMeasure == 0) {
            widthMeasure = MeasureSpec.makeMeasureSpec(
                    MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY)
            heightMeasure = MeasureSpec.makeMeasureSpec(
                    MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasure, heightMeasure)
    }

}
