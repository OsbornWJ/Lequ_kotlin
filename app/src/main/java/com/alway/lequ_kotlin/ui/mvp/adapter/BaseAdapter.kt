package com.alway.lequ_kotlin.ui.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
abstract class BaseAdapter<T: RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {

    var mOnItemClickListener: OnItemClickListener? = null
    var mOnItemLongClickListener: OnItemLongClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.mOnItemClickListener = onItemClickListener
    }

    abstract fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): T

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        val viewHolder = onCreateDefViewHolder(parent, viewType)
        bindViewClickListener(viewHolder)
        return viewHolder
    }

    private fun bindViewClickListener(viewHolder: RecyclerView.ViewHolder?) {
        if (viewHolder == null) return
        val view: View? = viewHolder.itemView ?: return
        if (mOnItemClickListener != null) {
            view!!.setOnClickListener { mOnItemClickListener!!.onItemClick(this@BaseAdapter, view, viewHolder.layoutPosition) }
        }
        if (mOnItemLongClickListener != null) {
            view!!.setOnClickListener { mOnItemLongClickListener!!.onItemLongClick(this@BaseAdapter, view, viewHolder.layoutPosition) }
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(adapter: BaseAdapter<*>, view: View, position: Int): Boolean
    }

    interface OnItemClickListener {
        fun onItemClick(adapter: BaseAdapter<*>, view: View, position: Int)
    }


}