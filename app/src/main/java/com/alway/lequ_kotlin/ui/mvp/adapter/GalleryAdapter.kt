package com.moment.eyepetizer.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.mvp.adapter.BaseAdapter
import com.alway.lequ_kotlin.utils.ImageLoad
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_recycler_item.view.*


/**
 * Created by moment on 2018/2/5.
 */
class GalleryAdapter(context: Context, private val mDatas: List<String>) : BaseAdapter<GalleryAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mContext: Context = context

    inner class ViewHolder(arg0: View) : RecyclerView.ViewHolder(arg0) {

        internal var mImg: ImageView? = null
    }

    override fun getItemCount(): Int = mDatas.size

    override fun onCreateDefViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.activity_recycler_item,
                viewGroup, false)
        val viewHolder = ViewHolder(view)

        viewHolder.mImg = view.id_index_gallery_item_image
        return viewHolder
    }

    /**
     * 设置值
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val width = getScreenWidth(mContext) - DensityUtil.dp2px(25f)
        val height = width * 0.6
        ImageLoad.load(mDatas.get(i), viewHolder.mImg, width.toDouble().toInt(), height.toDouble().toInt(), 5)
    }

    private fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

}