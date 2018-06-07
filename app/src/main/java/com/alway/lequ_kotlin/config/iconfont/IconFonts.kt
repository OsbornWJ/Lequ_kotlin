package com.alway.lequ_kotlin.config.iconfont

import com.joanzapata.iconify.Icon

/**
 * 创建人：wenjie on 2018/6/7
 * 邮箱： Osbornjie@163.com
 * 功能：
 */
enum class IconFonts private constructor(private val character: Char): Icon {

    caiwufenxi('\ue602'),
    chanpinmoxing('\ue603'),
    chayihua('\ue604'),
    jihui('\ue605'),
    lieshi('\ue606'),
    gongsizhanlve('\ue607'),
    xiangmujihua('\ue608'),
    shangyemoshi('\ue609'),
    weixie('\ue60a'),
    youshi('\ue60b'),
    gongsizongzhi('\ue60c'),
    chanchuxiaoshuai('\ue60d'),
    xiangmuxuqiu('\ue60e'),
    xiangmumubiao('\ue60f');

    override fun key(): String {
        return name.replace('_', '-');
    }

    override fun character(): Char {
       return character
    }
}