package com.alway.lequ_kotlin.ui.mvp.contract

import com.alway.lequ_kotlin.http.entity.Result
import com.alway.lequ_kotlin.ui.mvp.base.IModel
import com.alway.lequ_kotlin.ui.mvp.base.IView
import com.xujie.lequ.data.bean.GirlsBean
import io.reactivex.Observable

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class BeautyContract {

    interface View: IView {
        fun onBeautyListSucc(result: GirlsBean?)
    }

    interface Model: IModel {
        fun getGirls(type: String, count: Int, page: Int): Observable<GirlsBean>
    }

}
