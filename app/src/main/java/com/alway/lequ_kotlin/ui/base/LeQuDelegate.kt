package com.alway.lequ_kotlin.ui.base

import com.alway.lequ_kotlin.ui.mvp.base.IPersenter

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
abstract class LeQuDelegate<P: IPersenter>: BaseDelegate() {

    protected var mPresenter: P? = null

    fun getParentDelegate(): LeQuDelegate<*> {
        return parentFragment as LeQuDelegate<*>
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter!!.onDestory()
        mPresenter = null
    }

}
