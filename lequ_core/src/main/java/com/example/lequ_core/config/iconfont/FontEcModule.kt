package com.example.lequ_core.config.iconfont

import com.joanzapata.iconify.IconFontDescriptor

/**
 * 创建人：wenjie on 2018/6/7
 * 邮箱： Osbornjie@163.com
 * 功能：
 */
class FontEcModule: IconFontDescriptor {

    override fun ttfFileName(): String {
        return "iconfont.ttf"
    }

    override fun characters(): Array<IconFonts> {
       return IconFonts.values()
    }

}