package com.alway.lequ_kotlin.ui.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.alway.lequ_kotlin.R
import com.joanzapata.iconify.widget.IconTextView
import com.moment.eyepetizer.home.adapter.MultiTypeAdapter
import kotlinx.android.synthetic.main.banner2_item.view.*
import kotlinx.android.synthetic.main.banner_item.view.*
import kotlinx.android.synthetic.main.brief_card_item.view.*
import kotlinx.android.synthetic.main.dynamic_infocard_item.view.*
import kotlinx.android.synthetic.main.empty_item.view.*
import kotlinx.android.synthetic.main.followcard_item.view.*
import kotlinx.android.synthetic.main.horizontal_scrollcard_item.view.*
import kotlinx.android.synthetic.main.squarecard_collection_item.view.*
import kotlinx.android.synthetic.main.text_card_item.view.*
import kotlinx.android.synthetic.main.text_footer_item.view.*
import kotlinx.android.synthetic.main.text_header_item.view.*
import kotlinx.android.synthetic.main.video_smallcard_item.view.*
import kotlinx.android.synthetic.main.videocollection_ofhoriscroll_item.view.*
import kotlinx.android.synthetic.main.videocollection_withbrief_item.view.*

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
        MultiTypeAdapter.ITEM_TYPE.ITEM_VIDEOSMALLCARD.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.video_smallcard_item, parent, false)
            ItemVideoSmallViewHolder(view)
        }
        MultiTypeAdapter.ITEM_TYPE.ITEM_SQUARECARD_COLLECTION.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.squarecard_collection_item, parent, false)
            ItemSquareCardViewHolder(view)
        }
        MultiTypeAdapter.ITEM_TYPE.ITEM_VIDEOCOLLECTION_WITHBRIEF.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.videocollection_withbrief_item, parent, false)
            ItemVideoCollViewHolder(view)
        }
        MultiTypeAdapter.ITEM_TYPE.ITEM_BANNER.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
            ItemBannerViewHolder(view)
        }
        MultiTypeAdapter.ITEM_TYPE.ITEM_BANNER2.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.banner2_item, parent, false)
            ItemBanner2ViewHolder(view)
        }
        MultiTypeAdapter.ITEM_TYPE.ITEM_VIDEO.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.followcard_item, parent, false)
            ItemVideoViewHolder(view)
        }
        MultiTypeAdapter.ITEM_TYPE.ITEM_VIDEOCOLLECTION_OFHORISCROLLCARD.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.videocollection_ofhoriscroll_item, parent, false)
            return ItemVideoCollectionOfHolder(view)
        }
        MultiTypeAdapter.ITEM_TYPE.ITEM_TEXTHEADER.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.text_header_item, parent, false)
            return ItemTextHeaderViewHolder(view)
        }
        MultiTypeAdapter.ITEM_TYPE.ITEM_TEXTFOOTER.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.text_footer_item, parent, false)
            return ItemTextHeaderViewHolder(view)
        }
        MultiTypeAdapter.ITEM_TYPE.ITEM_DYNAMIC_INFOCARD.type.hashCode() -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.dynamic_infocard_item, parent, false)
            return ItemDynamicInfoViewHolder(view)
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
    var rlBriefRoot = itemView.rl_brief_root
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

class ItemVideoSmallViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var ivCover = itemView.iv_cover
    var tvTime = itemView.tv_time
    var tvSmallcardTitle = itemView.tv_smallcard_title
    var tvSmallcardContent = itemView.tv_smallcard_content
}

class ItemSquareCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var tvSquarecardSubTitle = itemView.tv_squarecard_collection_sub_title
    var tvSquarecardTitle = itemView.tv_squarecard_collection_title
    var ivSquarecardMore = itemView.iv_squarecard_more
    var recyclerviewSquareCard = itemView.recyclerview_squarecard_collection
}

class ItemVideoCollViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var ivVideocollIcon = itemView.iv_videocollection_icon
    var tvVideocollNickname = itemView.tv_videocollection_nickname
    var tvVideocollDes = itemView.tv_videocollection_des
    var videoRecycView = itemView.recyclerview_videocollection
}

class ItemBannerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var ivBanner = itemView.iv_banner
}

class ItemBanner2ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var ivBanner2 = itemView.iv_banner2
    var tvBanner2 = itemView.tv_banner2
}

class ItemVideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var ivFollowcardCover = itemView.iv_followcard_cover
    var tvFollowcardTime = itemView.tv_followcard_time
    var ivFollowcardIcon = itemView.iv_followcard_icon
    var tvTitle = itemView.tv_followcard_title
    var tvContent = itemView.tv_followcard_content
}

class ItemVideoCollectionOfHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var tvVideoTitle = itemView.tv_videocollection_of_title
    var ivVideoHeader = itemView.iv_videocollection_of_header
    var recyclerviewVideo = itemView.recyclerview_videocollection_of
}

class ItemTextHeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var tv_title = itemView.tv_text_header_title
    var iv_text_header_more = itemView.iv_text_header_more
}

class ItemTextFooterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var tv_text_footer = itemView.tv_text_footer
    var iv_footer_more = itemView.iv_footer_more
}

class ItemDynamicInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var civ_icon = itemView.civ_icon
    var tv_nickname = itemView.tv_dynamic_nickname
    var tv_print = itemView.tv_print
    var tv_content = itemView.tv_dynamic_content
    var iv_conver = itemView.iv_conver
    var tv_title = itemView.tv_dynamic_title
    var tv_des = itemView.tv_dynamic_des
    var tv_like = itemView.tv_like
    var tv_time = itemView.tv_dynamic_time
    var tv_dynamic_time_video = itemView.tv_dynamic_time_video
}

class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var tvEmpty: TextView = itemView.tv_empty
}

