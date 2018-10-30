package com.alway.lequ_kotlin.ui.mvp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.alway.lequ_kotlin.http.entity.Result

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:   分离type类型
 */
class MulitTypeAdapter constructor(var mContext: Context, var mData:ArrayList<Result.ItemList>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    enum class ITEM_TYPE(var type: String) {
        ITEM_TEXTCARD("textCard"),
        ITEM_BRIEFCARD("briefCard"),
        ITEM_DYNAMIC_INFOCARD("DynamicInfoCard"),
        ITEM_HORICONTAL_SCROLLCARD("horizontalScrollCard"),
        ITEM_FOLLOWCARD("followCard"),
        ITEM_VIDEOSMALLCARD("videoSmallCard"),
        ITEM_SQUARECARD_COLLECTION("squareCardCollection"),
        ITEM_VIDEOCOLLECTION_WITHBRIEF("videoCollectionWithBrief"),
        ITEM_BANNER("banner"),
        ITEM_BANNER2("banner2"),
        ITEM_VIDEO("video"),
        ITEM_VIDEOCOLLECTION_OFHORISCROLLCARD("videoCollectionOfHorizontalScrollCard"),
        ITEM_TEXTHEADER("textHeader"),
        ITEM_TEXTFOOTER("textFooter")
    }

    fun cleanAll() {
        mData.clear()
    }

    fun addAll(data: ArrayList<Result.ItemList>?) {
        if (data == null) {
            return
        }
        mData.addAll(data)
    }

   /* //创建新View，被LayoutManager所调用
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder? =
            createMyViewHolder(viewGroup, viewType)


    //将数据与界面进行绑定的操作
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) =
            bindViewHolder(mContext, mData, viewHolder, position)

    override fun getItemViewType(position: Int): Int = getMultiType(position, mData)

    //获取数据的数量
    override fun getItemCount(): Int = mData.size*/
}