package com.alway.lequ_kotlin.ui.mvp.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.text.TextUtils
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.http.entity.Result
import com.alway.lequ_kotlin.ui.mvp.parseUri
import com.alway.lequ_kotlin.ui.mvp.parseWebView
import com.alway.lequ_kotlin.utils.ImageLoad
import com.moment.eyepetizer.home.adapter.GalleryAdapter
import com.moment.eyepetizer.utils.TimeUtils
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.shuyu.gsyvideoplayer.utils.CommonUtil.getScreenWidth

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */

fun bindMultiViewHolder(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    when (viewHolder) {
        is ItemTextCardViewHolder -> onItemTextCardBind(mContext, viewHolder, datas, position)
        is ItemBriefCardViewHolder -> onItemBriefCardBind(mContext, viewHolder, datas, position)
        is ItemHoricontalScrollViewHolder -> onHorizontalScrollcardBind(mContext, viewHolder, datas, position)
        is ItemFollowCardViewHolder -> onItemFollowCardBind(mContext, viewHolder, datas, position)
        is EmptyViewHolder -> onEmptyItemView(mContext, viewHolder)
    }
}

fun onItemTextCardBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data = datas[position]
    val holder : ItemTextCardViewHolder = viewHolder as ItemTextCardViewHolder
    val textCard = data.data as Map<*, *>
    val actionUrl = textCard["actionUrl"]
    val type = textCard["type"]
    if ("header5" == type.toString() || type.toString().contains("header")) {
        holder.llHeader5.visibility = View.VISIBLE
        holder.rlFooter2.visibility = View.GONE
        holder.tvTextCardTitle.text = textCard["text"].toString()
        if (actionUrl == null || TextUtils.isEmpty(actionUrl.toString())) {
            holder.ivMoreHeader.visibility = View.GONE
        } else {
            holder.ivMoreHeader.visibility = View.VISIBLE

        }
    } else if ("footer2" == type.toString() || type.toString().contains("footer")) {
        holder.llHeader5.visibility = View.GONE
        holder.rlFooter2.visibility = View.VISIBLE
        holder.tvFooter.text = textCard["text"].toString()
        if (actionUrl == null || TextUtils.isEmpty(actionUrl.toString())) {
            holder.ivMore.visibility = View.GONE
        } else {
            holder.ivMore.visibility = View.VISIBLE

        }
    }
}

fun onItemBriefCardBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data = datas[position]
    val holder: ItemBriefCardViewHolder = viewHolder as ItemBriefCardViewHolder
    val briefCard = data.data as Map<*, *>
    holder.cardTitle.text = briefCard["title"].toString()
    holder.cardContent.text = briefCard["description"].toString()
    when (briefCard["iconType"].toString()) {
        "square" -> ImageLoad().loadRound(briefCard["icon"].toString(), holder.icon, 5)
        "round" -> ImageLoad().loadCircle(briefCard["icon"].toString(), holder.icon)
        else -> ImageLoad().load(briefCard["icon"].toString(), holder.icon)
    }
}

fun onHorizontalScrollcardBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data = datas[position]
    val holder: ItemHoricontalScrollViewHolder = viewHolder as ItemHoricontalScrollViewHolder
    val scrollCard = data.data as Map<*, *>
    val itemList = scrollCard["itemList"] as List<Map<*, *>>
    val urlList: MutableList<String>? = ArrayList()
    itemList
            .map { it["data"] as Map<*, *> }
            .forEach { urlList!!.add(it["image"].toString())}
    val snapHelper: SnapHelper = PagerSnapHelper()
    holder.horizontalScrollcard.onFlingListener = null
    holder.horizontalScrollcard.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    snapHelper.attachToRecyclerView(holder.horizontalScrollcard)

    holder.horizontalScrollcard.adapter = GalleryAdapter(mContext, urlList!!.toList())
}

fun onItemFollowCardBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data = datas[position]
    val holder: ItemFollowCardViewHolder = viewHolder as ItemFollowCardViewHolder
    val followCard = data.data as Map<*, *>
    val header = followCard["header"] as Map<*, *>
    val iconType = header["iconType"]
    when (iconType) {
        "square" -> ImageLoad().loadRound(header["icon"].toString(), holder.ivFollowcardIcon, 5)
        "round" -> ImageLoad().loadCircle(header["icon"].toString(), holder.ivFollowcardIcon)
        else -> ImageLoad().load(header["icon"].toString(), holder.ivFollowcardIcon)
    }
    val iconContent =TextUtils.split(header["description"].toString(), "/")
    holder.tvContent.text = header["title"].toString() + " / " + iconContent

    val content = followCard["content"] as Map<*, *>
    val dataObj = content["data"] as Map<*, *>
    val cover = dataObj["cover"] as Map<*, *>
    val width = getScreenWidth(mContext) - DensityUtil.dp2px(20f)
    val height = width * 0.6
    ImageLoad().load(cover["feed"].toString(), holder.ivFollowcardCover, width, height.toInt(), 5)

    holder.ivFollowcardCover!!.setOnClickListener {
        val title = dataObj["title"].toString()
        val webUrl = dataObj["webUrl"] as Map<*, *>
        val url = webUrl["raw"].toString()
        var id = dataObj["id"]
        parseUri(parseWebView(title, url))
    }
    holder.tvFollowcardTime!!.text = TimeUtils.secToTime(dataObj["duration"].toString().toFloat().toInt())
    holder.tvTitle!!.text = dataObj["title"].toString()

}

fun onEmptyItemView(mContext: Context, viewHolder: RecyclerView.ViewHolder) {
    val holder = viewHolder as EmptyViewHolder
    holder.tvEmpty.text = mContext.getText(R.string.empty_view)
}