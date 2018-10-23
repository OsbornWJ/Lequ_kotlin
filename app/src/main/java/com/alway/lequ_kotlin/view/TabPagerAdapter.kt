package com.alway.lequ_kotlin.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class TabPagerAdapter(var fm: FragmentManager?, var fragments: ArrayList<Fragment>, var titles: ArrayList<String>): FragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}