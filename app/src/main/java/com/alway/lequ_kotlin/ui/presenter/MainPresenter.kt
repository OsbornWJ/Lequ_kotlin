package com.alway.lequ_kotlin.ui.presenter

import com.alway.lequ_kotlin.ui.base.BasePersenter
import com.alway.lequ_kotlin.ui.contract.MainContract
import javax.inject.Inject

/**
 * 创建人: Jeven
 * 邮箱:   liaowenjie@sto.cn
 * 功能:
 */
class MainPresenter
constructor(model: MainContract.Model, rootView: MainContract.View) : BasePersenter<MainContract.View, MainContract.Model>(model, rootView) {

}