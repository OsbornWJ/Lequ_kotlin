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
import kotlinx.android.synthetic.main.brief_card_item.view.*
import kotlinx.android.synthetic.main.empty_item.view.*
import kotlinx.android.synthetic.main.followcard_item.view.*
import kotlinx.android.synthetic.main.horizontal_scrollcard_item.view.*
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
        MultiTypeAdapter.ITEM_TYPE.ITEM_BRIEFCARD.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.brief_card_item, parent, false)
            ItemBriefCardViewHolder(view)
        }
        MultiTypeAdapter.ITEM_TYPE.ITEM_HORICONTAL_SCROLLCARD.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_scrollcard_item, parent, false)
            ItemHoricontalScrollViewHolder(view)
        }
        MultiTypeAdapter.ITEM_TYPE.ITEM_FOLLOWCARD.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.followcard_item, parent, false)
            ItemFollowCardViewHolder(view)
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

class ItemBriefCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var icon = itemView.image
    var cardTitle = itemView.tv_brief_card_title
    var cardContent = itemView.tv_brief_card_content
}

class ItemHoricontalScrollViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var horizontalScrollcard: RecyclerView = itemView.recyclerview_horizontal_scrollcard
}

class ItemFollowCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var ivFollowcardCover = itemView.iv_followcard_cover
    var tvFollowcardTime = itemView.tv_followcard_time
    var ivFollowcardIcon = itemView.iv_followcard_icon
    var tvTitle = itemView.tv_followcard_title
    var tvContent = itemView.tv_followcard_content
}

class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var tvEmpty: TextView = itemView.tv_empty
}

