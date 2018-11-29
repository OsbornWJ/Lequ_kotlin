package com.alway.lequ_kotlin.ui.mvp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.http.entity.Result
import com.alway.lequ_kotlin.ui.mvp.parseUri
import com.alway.lequ_kotlin.ui.mvp.parseWebView
import com.alway.lequ_kotlin.utils.ImageLoad
import com.moment.eyepetizer.home.adapter.GalleryAdapter
import com.moment.eyepetizer.home.adapter.MultiTypeAdapter
import com.moment.eyepetizer.home.adapter.VideoCollectionAdapter
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
        is ItemVideoSmallViewHolder -> onItemVideoSmallViewBind(mContext, viewHolder, datas, position)
        is ItemSquareCardViewHolder -> onItemSquareCardViewBind(mContext, viewHolder, datas, position)
        is ItemVideoCollViewHolder  -> onItemVideoCollBind(mContext, viewHolder, datas, position)
        is ItemBannerViewHolder     -> onItemBannerBind(mContext, viewHolder, datas, position)
        is ItemBanner2ViewHolder    -> onItemBanner2Bind(mContext, viewHolder, datas, position)
        is ItemVideoViewHolder      -> onItemVideoViewBind(mContext, viewHolder, datas, position)
        is ItemVideoCollectionOfHolder -> onItemVideoCollectionBind(mContext, viewHolder, datas, position)
        is ItemTextHeaderViewHolder -> onItemTextHeaderBind(mContext, viewHolder, datas, position)
        is ItemTextFooterViewHolder -> onItemTextFooterBind(mContext, viewHolder, datas, position)
        is ItemDynamicInfoViewHolder -> onItemDynamicInfoBind(mContext, viewHolder, datas, position)
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
            holder.llHeader5.setOnClickListener { parseUri(mContext, actionUrl.toString()) }
        }
    } else if ("footer2" == type.toString() || type.toString().contains("footer")) {
        holder.llHeader5.visibility = View.GONE
        holder.rlFooter2.visibility = View.VISIBLE
        holder.tvFooter.text = textCard["text"].toString()
        if (actionUrl == null || TextUtils.isEmpty(actionUrl.toString())) {
            holder.ivMore.visibility = View.GONE
        } else {
            holder.ivMore.visibility = View.VISIBLE
            holder.rlFooter2.setOnClickListener { parseUri(mContext, actionUrl.toString()) }
        }
    }
}

fun onItemBriefCardBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data = datas[position]
    val holder: ItemBriefCardViewHolder = viewHolder as ItemBriefCardViewHolder
    val briefCard = data.data as Map<*, *>
    holder.cardTitle.text = briefCard["title"].toString()
    holder.cardContent.text = briefCard["description"].toString()
    holder.rlBriefRoot.setOnClickListener { parseUri(mContext, briefCard["actionUrl"].toString()) }
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
    @Suppress("UNCHECKED_CAST")
    val itemList = scrollCard["itemList"] as List<Map<*, *>>
    val urlList: MutableList<String>? = ArrayList()
    itemList
            .map { it["data"] as Map<*, *> }
            .forEach { urlList!!.add(it["image"].toString())}
    val snapHelper: SnapHelper = PagerSnapHelper()
    holder.horizontalScrollcard.onFlingListener = null
    holder.horizontalScrollcard.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    snapHelper.attachToRecyclerView(holder.horizontalScrollcard)

    val adapter = GalleryAdapter(mContext, urlList!!.toList())
    holder.horizontalScrollcard.adapter = adapter
    adapter.setOnItemClickListener(object: BaseAdapter.OnItemClickListener {
        override fun onItemClick(adapter: BaseAdapter<*>, view: View, position: Int) {
            val map = itemList[position]
            val childData = map["data"] as Map<*, *>
            val url = childData["actionUrl"].toString()
            parseUri(mContext, url)
        }
    })
}

@SuppressLint("SetTextI18n")
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
        parseUri(mContext, parseWebView(title, url))
    }
    holder.tvFollowcardTime!!.text = TimeUtils.secToTime(dataObj["duration"].toString().toFloat().toInt())
    holder.tvTitle!!.text = dataObj["title"].toString()
}

@SuppressLint("SetTextI18n")
fun onItemVideoSmallViewBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data = datas[position]
    val videoSmallCard = data.data as Map<*, *>
    val cover = videoSmallCard["cover"] as Map<*, *>
    val holder: ItemVideoSmallViewHolder = viewHolder as ItemVideoSmallViewHolder
    val width = getScreenWidth(mContext) * 0.5
    val height = width * 0.6
    ImageLoad().load(cover["feed"].toString(), holder.ivCover, width.toInt(), height.toInt(), 5)

    holder.ivCover.setOnClickListener {
        val title = videoSmallCard["title"].toString()
        val webUrl = videoSmallCard["webUrl"] as Map<*, *>
        val url = webUrl["raw"].toString()
        var id = videoSmallCard["id"]
        parseUri(mContext, parseWebView(title, url))
    }
    holder.tvTime!!.text = TimeUtils.secToTime(videoSmallCard["duration"].toString().toFloat().toInt())
    holder.tvSmallcardTitle!!.text = videoSmallCard["title"].toString()
    if (videoSmallCard["author"] != null) {
        val author = videoSmallCard["author"] as Map<*, *>
        holder.tvSmallcardContent!!.text = "#" + videoSmallCard["category"] + " / " + author["name"].toString().replace(videoSmallCard["category"].toString(), "")
    }
}

fun onItemSquareCardViewBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data = datas[position]
    val squareCard = data.data as Map<*, *>
    val holder: ItemSquareCardViewHolder = viewHolder as ItemSquareCardViewHolder
    val header = squareCard["header"] as Map<*, *>
    holder.tvSquarecardTitle.text = header["title"].toString()
    if (header["font"] != null && "bigBold" == header["font"].toString()) {
        holder.tvSquarecardTitle!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f)
    }

    if (header["subTitle"] != null) {
        holder.tvSquarecardSubTitle.text = header["subTitle"].toString()
        if (header["subTitleFont"] != null && "lobster" == header["subTitleFont"].toString()) {
            holder.tvSquarecardSubTitle.typeface = Typeface.createFromAsset(mContext.assets, "fonts/Lobster-1.4.otf")
        }
        holder.tvSquarecardSubTitle.visibility = View.VISIBLE
    } else {
        holder.tvSquarecardSubTitle.visibility = View.GONE
    }

    val actionUrl = header["actionUrl"].toString()
    if (!actionUrl.isEmpty()) {
        holder.ivSquarecardMore.visibility = View.VISIBLE
    } else {
        holder.ivSquarecardMore.visibility = View.GONE
    }
    holder.tvSquarecardTitle!!.setOnClickListener {
        if (!actionUrl.isEmpty()) {
            parseUri(mContext, actionUrl)
        }
    }

    val itemList = squareCard["itemList"] as List<Map<*, *>>
    val urlList: MutableList<String>? = ArrayList<String>()
    for (map in itemList) {
        val type = map["type"]
        val data = map["data"] as Map<*, *>
        if (MultiTypeAdapter.ITEM_TYPE.ITEM_FOLLOWCARD.type.hashCode() == type.toString().hashCode()) {
            val content = data["content"] as Map<*, *>
            val datas = content["data"] as Map<*, *>
            val cover = datas["cover"] as Map<*, *>
            urlList!!.add(cover["feed"].toString())
        } else {
            urlList!!.add(data["image"].toString())
        }
    }

    holder.recyclerviewSquareCard.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    holder.recyclerviewSquareCard.onFlingListener = null
    val snapHelper: SnapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(holder.recyclerviewSquareCard)
    val adapter = GalleryAdapter(mContext, urlList!!.toList())
    holder.recyclerviewSquareCard!!.adapter = adapter
    adapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
        override fun onItemClick(adapter: BaseAdapter<*>, view: View, position: Int) {
            val map = itemList[position]
            val childData = map["data"] as Map<*, *>
            if (childData["actionUrl"] != null) {
                val actionUrl = childData["actionUrl"].toString()
                if (!actionUrl.isEmpty()) {
                    parseUri(mContext, actionUrl)
                }
            }
            if (childData["content"] != null) {
                val content = childData["content"] as Map<*, *>
                if (content["data"] != null) {
                    val videodata = content["data"] as Map<*, *>
                    val title = videodata["title"].toString()
                    if (videodata["webUrl"] != null) {
                        val webUrl = videodata["webUrl"] as Map<*, *>
                        val raw = webUrl["raw"].toString()
                        parseUri(mContext, parseWebView(title, raw))
                    }
                }
            }
        }
    })
}

fun onItemVideoCollBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data = datas[position]
    val holder = viewHolder as ItemVideoCollViewHolder
    val videoCollection = data.data as Map<*, *>
    val header = videoCollection["header"] as Map<*, *>

    holder.tvVideocollNickname.text = header["title"].toString()
    val iconType = header["iconType"].toString()
    when (iconType) {
        "square" -> ImageLoad().loadRound(header["icon"].toString(), holder.ivVideocollIcon, 5)
        "round" -> ImageLoad().loadCircle(header["icon"].toString(), holder.ivVideocollIcon)
        else -> ImageLoad().load(header["icon"].toString(), holder.ivVideocollIcon)
    }
    holder.tvVideocollDes.text = header["description"].toString()

    val itemList = videoCollection["itemList"] as List<Map<*, *>>
    val urlList: MutableList<VideoCollectionAdapter.VideoCollection>? = ArrayList()
    for (map in itemList) {
        val data = map["data"] as Map<*, *>
        val cover = data["cover"] as Map<*, *>
        val video: VideoCollectionAdapter.VideoCollection = VideoCollectionAdapter.VideoCollection()
        video.icon = cover["feed"].toString()
        video.title = data["title"].toString()
        video.category = data["category"].toString()
        video.duration = data["duration"].toString().toFloat().toInt().toLong()
        urlList!!.add(video)
    }
    holder.videoRecycView.layoutManager = LinearLayoutManager(mContext)
    holder.videoRecycView.onFlingListener = null
    val snapHelper: SnapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(holder.videoRecycView)

    val adapter = VideoCollectionAdapter(mContext, urlList!!.toList())
    holder.videoRecycView.adapter = adapter
    adapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
        override fun onItemClick(adapter: BaseAdapter<*>, view: View, position: Int) {
            val childData = itemList[position]["data"] as Map<*, *>
            val title = childData["title"].toString()
            if (childData["webUrl"] != null) {
                val webUrl = childData["webUrl"] as Map<*, *>
                val url = webUrl["raw"].toString()
                parseUri(mContext, parseWebView(title, url))
            }
        }
    })
}

fun onItemBannerBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemBannerViewHolder = viewHolder as ItemBannerViewHolder
    val banner = data.data as Map<*, *>
    val image: String = banner["image"].toString()
    val width = getScreenWidth(mContext) - DensityUtil.dp2px(20f)
    val height = width * 0.6
    ImageLoad().load(image, holder.ivBanner, width.toDouble().toInt(), height.toInt(), 5)
    holder.ivBanner!!.setOnClickListener {
        val actionUrl = banner["actionUrl"].toString()
        parseUri(mContext, actionUrl)
    }
}

fun onItemBanner2Bind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemBanner2ViewHolder = viewHolder as ItemBanner2ViewHolder
    val banner = data.data as Map<*, *>
    val image: String = banner["image"].toString()
    val width = getScreenWidth(mContext) - DensityUtil.dp2px(20f)
    val height = width * 0.6
    ImageLoad().load(image, holder.ivBanner2, width.toDouble().toInt(), height.toDouble().toInt(), 5)

    val label = banner["label"]
    if (label != null) {
        val label = banner["label"] as Map<*, *>
        val text = label["text"]
        if (!text.toString().isEmpty()) {
            holder.tvBanner2.text = label["text"].toString()
            holder.tvBanner2.visibility = View.VISIBLE
        } else {
            holder.tvBanner2.visibility = View.GONE
        }
    } else {
        holder.tvBanner2.visibility = View.GONE
    }

    val actionUrl = banner["actionUrl"].toString()
    holder.ivBanner2.setOnClickListener {
        if (!actionUrl.isEmpty()) {
            parseUri(mContext, actionUrl)
        }
    }
}

fun onItemVideoViewBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data = datas[position]
    val holder: ItemVideoViewHolder = viewHolder as ItemVideoViewHolder
    val followCard = data.data as Map<*, *>
    if (followCard["author"] != null) {
        val author = followCard["author"] as Map<*, *>
        ImageLoad().loadCircle(author["icon"].toString(), holder.ivFollowcardIcon)
        holder.tvContent.text = author["name"].toString() + " / #" + followCard["category"]
    } else if (followCard["tags"] != null) {
        val tags = followCard["tags"] as List<Map<*, *>>
        ImageLoad().loadRound(tags[0]["headerImage"].toString(), holder.ivFollowcardIcon, 5)
        holder.tvContent.text = "#" + tags[0]["name"].toString() + "#"
    }

    val cover = followCard["cover"] as Map<*, *>
    val width = getScreenWidth(mContext) - DensityUtil.dp2px(20f)
    val height = width * 0.6
    ImageLoad().load(cover["feed"].toString(), holder.ivFollowcardCover, width, height.toInt(), 5)

    holder.ivFollowcardCover!!.setOnClickListener {
        val title = followCard["title"].toString()
        val webUrl = followCard["webUrl"] as Map<*, *>
        val url = webUrl["raw"].toString()
        parseUri(mContext, parseWebView(title, url))
    }
    holder.tvFollowcardTime.text = TimeUtils.secToTime(followCard["duration"].toString().toFloat().toInt())
    holder.tvTitle.text = followCard["title"].toString()
}

fun onItemVideoCollectionBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemVideoCollectionOfHolder = viewHolder as ItemVideoCollectionOfHolder
    val videoCollectionOfHorizontalScrollCard = data.data as Map<*, *>

    val header = videoCollectionOfHorizontalScrollCard["header"] as Map<*, *>
    holder.tvVideoTitle.text = header["title"].toString()
    val actionUrl = header["actionUrl"]

    holder.tvVideoTitle.setOnClickListener {
        if (actionUrl != null) {
            parseUri(mContext, actionUrl.toString())
        }
    }
    if (actionUrl == null || TextUtils.isEmpty(actionUrl.toString())) {
        holder.ivVideoHeader.visibility = View.GONE
    } else {
        holder.ivVideoHeader.visibility = View.VISIBLE
    }

    val itemList = videoCollectionOfHorizontalScrollCard["itemList"] as List<Map<*, *>>
    val urlList: MutableList<VideoCollectionAdapter.VideoCollection>? = ArrayList()
    for (map in itemList) {
        val data = map["data"] as Map<*, *>
        val cover = data["cover"] as Map<*, *>
        val video: VideoCollectionAdapter.VideoCollection = VideoCollectionAdapter.VideoCollection()
        video.icon = cover["feed"].toString()
        video.title = data["title"].toString()
        video.category = data["category"].toString()
        video.duration = data["duration"].toString().toFloat().toInt().toLong()
        urlList!!.add(video)
    }

    val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
    holder.recyclerviewVideo.layoutManager = linearLayout
    holder.recyclerviewVideo.onFlingListener = null
    val snapHelper: SnapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(holder.recyclerviewVideo)

    val adapter = VideoCollectionAdapter(mContext, urlList!!.toList())
    holder.recyclerviewVideo.adapter = adapter
    adapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
        override fun onItemClick(adapter: BaseAdapter<*>, view: View, position: Int) {
            val data = itemList[position]["data"] as Map<*, *>
            val title = data["title"].toString()
            val webUrl = data["webUrl"] as Map<*, *>
            val url = webUrl["raw"].toString()
            parseUri(mContext, parseWebView(title, url))
        }

    })
}

fun onItemTextHeaderBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemTextHeaderViewHolder = viewHolder as ItemTextHeaderViewHolder
    val txtCard = data.data as Map<*, *>
    holder.tv_title.text = txtCard["text"].toString()
    val actionUrl = txtCard["actionUrl"]
    if (actionUrl != null) {
        if (actionUrl.toString().isEmpty()) {
            holder.iv_text_header_more.visibility = View.GONE
        } else {
            holder.iv_text_header_more.visibility = View.VISIBLE
        }
    } else {
        holder.iv_text_header_more.visibility = View.GONE
    }

    holder.tv_title.setOnClickListener {
        if (actionUrl != null) {
            parseUri(mContext, actionUrl.toString())
        }
    }
}

fun onItemTextFooterBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemTextFooterViewHolder = viewHolder as ItemTextFooterViewHolder
    val txtCard = data.data as Map<*, *>
    holder.tv_text_footer.text = txtCard["text"].toString()
    val actionUrl = txtCard["actionUrl"]
    if (actionUrl != null) {
        if (actionUrl.toString().isEmpty()) {
            holder.iv_footer_more.visibility = View.GONE
        } else {
            holder.iv_footer_more.visibility = View.VISIBLE
        }
    } else {
        holder.iv_footer_more.visibility = View.GONE
    }

    holder.tv_text_footer.setOnClickListener {
        if (actionUrl != null) {
            parseUri(mContext, actionUrl.toString())
        }
    }
}

fun onItemDynamicInfoBind(mContext: Context, viewHolder: RecyclerView.ViewHolder, datas: ArrayList<Result.ItemList>, position: Int) {
    val data: Result.ItemList = datas[position]
    val holder: ItemDynamicInfoViewHolder = viewHolder as ItemDynamicInfoViewHolder
    val dynamicInfoCard = data.data as Map<*, *>
    holder.tv_print.text = dynamicInfoCard["text"].toString()
    val user = dynamicInfoCard["user"] as Map<*, *>

    ImageLoad().loadCircle(user["avatar"].toString(), holder.civ_icon)
    holder.tv_nickname.text = user["nickname"].toString()
    val reply = dynamicInfoCard["reply"] as Map<*, *>
    holder.tv_content.text = reply["message"].toString()
    holder.tv_like.text = "赞" + reply["likeCount"].toString().toFloat().toInt()


    val simpleVideo = dynamicInfoCard["simpleVideo"] as Map<*, *>
    holder.tv_title.text = simpleVideo["title"].toString()
    holder.tv_des.text = "#" + simpleVideo["category"].toString()
    val cover = simpleVideo["cover"] as Map<*, *>
    holder.tv_dynamic_time_video!!.text = TimeUtils.secToTime(simpleVideo["duration"].toString().toFloat().toInt())

    val width = getScreenWidth(mContext) * 0.4
    val height = width * 0.6
    ImageLoad().load(cover["feed"].toString(), holder.iv_conver, width.toInt(), height.toInt(), 5)

    holder.iv_conver.setOnClickListener {
        var title = simpleVideo["title"].toString()
        var id = simpleVideo["id"]
    }
    val obj = simpleVideo["releaseTime"].toString().toDouble()
    holder.tv_time.text = TimeUtils.getDiffTime(obj.toLong())
}

fun onEmptyItemView(mContext: Context, viewHolder: RecyclerView.ViewHolder) {
    val holder = viewHolder as EmptyViewHolder
    holder.tvEmpty.text = mContext.getText(R.string.empty_view)
}