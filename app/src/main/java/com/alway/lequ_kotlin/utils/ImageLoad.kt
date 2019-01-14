package com.alway.lequ_kotlin.utils

import android.content.Context
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.alway.lequ_kotlin.R
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.scwang.smartrefresh.layout.util.DensityUtil
import java.lang.ref.WeakReference


/**
 * Created by moment on 2018/2/6.
 */

class ImageLoad {

    companion object {

        fun load(url: String, image: ImageView?) {
            if (image == null) return
            var requestOptions = RequestOptions().centerCrop()
                    .placeholder(R.drawable.default_banner)
                    .error(R.drawable.default_banner)
                    .transform(CenterCrop())
                    .format(DecodeFormat.PREFER_RGB_565)
                    .priority(Priority.LOW)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

            Glide.with(image.context)
                    .load(url)
                    .apply(requestOptions)
                    .into(object : DrawableImageViewTarget(image) {
                    })
        }

        fun loadWithDefault(url: String, image: ImageView?, @DrawableRes defaultImg:Int) {
            if (image == null) return
            var lp = image.layoutParams
            image.layoutParams = lp
            var requestOptions = RequestOptions()
                    .placeholder(defaultImg)
                    .format(DecodeFormat.PREFER_RGB_565)
                    .error(defaultImg)
                    .dontAnimate()
                    .priority(Priority.NORMAL)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

            Glide.with(image.context)
                    .load(url)
                    .apply(requestOptions)
                    .into(object : DrawableImageViewTarget(image) {
                    })

        }

        fun loadCircle(url: String, image: ImageView?) {
            if (image == null) return
            var lp = image.layoutParams
            lp.width = DensityUtil.dp2px(40f)
            lp.height = DensityUtil.dp2px(40f)
            image.layoutParams = lp
            var requestOptions = RequestOptions().centerCrop()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .transform(CenterCrop())
                    .override(lp.width)
                    .dontAnimate()
                    .priority(Priority.LOW)
                    .transform(CircleCrop())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

            Glide.with(image.context)
                    .load(url)
                    .apply(requestOptions)
                    .into(object : DrawableImageViewTarget(image) {
                    })
        }

        fun load(url: String, image: ImageView?, width: Int, height: Int, round: Int) {
            if (image == null) return
            var lp = image.layoutParams
            lp.width = width
            lp.height = height
            image.layoutParams = lp
            var requestOptions = RequestOptions().centerCrop()
                    .placeholder(R.drawable.default_banner)
                    .error(R.drawable.default_banner)
                    .format(DecodeFormat.PREFER_RGB_565)
                    .override(width, height)
                    .priority(Priority.LOW)
                    .dontAnimate()
                    .transforms(CenterCrop(), RoundedCorners(DensityUtil.dp2px(round.toFloat())))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

            Glide.with(image.context)
                    .load(url)
                    .apply(requestOptions)
                    .into(object : DrawableImageViewTarget(image) {
                    })
        }

        fun loadRound(url: String, image: ImageView?, round: Int) {
            if (image == null) return
            var requestOptions = RequestOptions().centerCrop()
                    .placeholder(R.drawable.default_banner)
                    .error(R.drawable.default_banner)
                    .format(DecodeFormat.PREFER_RGB_565)
                    .priority(Priority.LOW)
                    .dontAnimate()
                    .transforms(CenterCrop(), RoundedCorners(DensityUtil.dp2px(round.toFloat())))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

            Glide.with(image.context)
                    .load(url)
                    .apply(requestOptions)
                    .into(object : DrawableImageViewTarget(image) {
                    })
        }

        fun clearCache(context: WeakReference<Context>) {
            Thread(Runnable {
                Glide.get(context.get()!!.applicationContext).clearDiskCache()
            }).start()
            Glide.get(context.get()!!.applicationContext).clearMemory()
        }

        fun loadGirls(url: String, image: ImageView?) {
            if (image == null) return
            var requestOptions = RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .skipMemoryCache(true)
                    .priority(Priority.NORMAL)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

            Glide.with(image.context)
                    .load(url)
                    .apply(requestOptions)
                    .into(object : DrawableImageViewTarget(image) {
                    })
        }
    }

}