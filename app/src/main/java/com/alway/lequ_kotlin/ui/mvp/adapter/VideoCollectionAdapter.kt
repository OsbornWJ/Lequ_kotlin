package com.moment.eyepetizer.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.mvp.adapter.BaseAdapter
import com.alway.lequ_kotlin.utils.ImageLoad
import com.moment.eyepetizer.utils.TimeUtils
import com.scwang.smartrefresh.layout.util.DensityUtil


/**
 * Created by moment on 2018/2/5.
 */
class VideoCollectionAdapter(context: Context, private val mDatas: List<VideoCollection>) : BaseAdapter<VideoCollectionAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mContext: Context = context

    class VideoCollection {
        var icon: String? = null
        var title: String? = null
        var category: String? = null
        var duration: Long? = null
    }

    inner class ViewHolder(arg0: View) : RecyclerView.ViewHolder(arg0) {

        internal var mImg: ImageView? = null
        internal var tv_title: TextView? = null
        internal var tv_content: TextView? = null
        internal var tv_time: TextView? = null
    }

    override fun getItemCount(): Int = mDatas.size

    /**
     * 创建ViewHolder
     */
    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.video_collection_recycler_item,
                parent, false)
        val viewHolder = ViewHolder(view)

        viewHolder.mImg = view
                .findViewById(R.id.id_index_gallery_item_image)
        viewHolder.tv_title = view.findViewById(R.id.tv_title)

        viewHolder.tv_content = view.findViewById(R.id.tv_content)
        viewHolder.tv_time = view.findViewById(R.id.tv_time)
        return viewHolder
    }


    /**
     * 设置值
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val width = getScreenWidth(mContext) - DensityUtil.dp2px(25f)
        val height = width * 0.6
        ImageLoad().load(mDatas[i].icon.toString(), viewHolder.mImg, width.toDouble().toInt(), height.toInt(), 5)
        viewHolder.tv_title!!.text = mDatas.get(i).title
        viewHolder.tv_content!!.text = "#" + mDatas[i].category
        viewHolder.tv_time!!.text = TimeUtils.secToTime(mDatas.get(i).duration!!.toInt())

    }

    private fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

}