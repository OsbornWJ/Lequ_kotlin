package com.alway.lequ_kotlin.ui.mvp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.http.entity.Result

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */

fun bindMultiViewHolder(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    when (viewHolder) {
        is ItemTextCardViewHolder -> onItemTextCardBind(mContext, viewHolder, datas, position)
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

fun onEmptyItemView(mContext: Context, viewHolder: RecyclerView.ViewHolder) {
    var holder = viewHolder as EmptyViewHolder
    holder.tvEmpty.text = mContext.getText(R.string.empty_view)
}