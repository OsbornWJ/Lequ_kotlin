package com.alway.lequ_kotlin.ui.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.alway.lequ_kotlin.R
import com.joanzapata.iconify.widget.IconTextView
import com.moment.eyepetizer.home.adapter.MultiTypeAdapter
import kotlinx.android.synthetic.main.empty_item.view.*
import kotlinx.android.synthetic.main.text_card_item.view.*

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */

fun createMultiViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
        MultiTypeAdapter.ITEM_TYPE.ITEM_TEXTCARD.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.text_card_item, parent, false)
            ItemTextCardViewHolder(view)
        }
        else -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.empty_item, parent, false)
            EmptyViewHolder(view)
        }
    }
}

class ItemTextCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var llHeader5: LinearLayout = itemView.ll_header5
    var tvTextCardTitle: TextView = itemView.tv_text_card_title
    var ivMoreHeader: IconTextView = itemView.iv_more_header
    var rlFooter2: RelativeLayout = itemView.rl_footer2
    var tvFooter: TextView = itemView.tv_footer
    var ivMore: IconTextView = itemView.iv_more
}

class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var tvEmpty: TextView = itemView.tv_empty
}

