package com.moment.eyepetizer.home.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.alway.lequ_kotlin.http.entity.Result
import com.alway.lequ_kotlin.utils.getMultiType
import com.alway.lequ_kotlin.ui.mvp.adapter.*
/**
 * Created by moment on 2017/12/11.
 */

class MultiTypeAdapter(private var datas: ArrayList<Result.ItemList>, private var context: Context) : BaseAdapter<RecyclerView.ViewHolder>() {

    enum class ITEM_TYPE(val type: String) {
        ITEM_TEXTCARD("textCard"),
        ITEM_BRIEFCARD("briefCard"),
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
        ITEM_TEXTFOOTER("textFooter"),
        ITEM_DYNAMIC_INFOCARD("DynamicInfoCard")
    }


    fun clearAll() = this.datas.clear()

    fun addAll(data: ArrayList<Result.ItemList>?) {
        if (data == null) {
            return
        }
        this.datas.addAll(data)
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = createMultiViewHolder(parent, viewType)

    override fun getItemCount(): Int = datas.size

    override fun getItemViewType(position: Int): Int = getMultiType(position, datas)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = bindMultiViewHolder(context, holder, datas, position)

}
