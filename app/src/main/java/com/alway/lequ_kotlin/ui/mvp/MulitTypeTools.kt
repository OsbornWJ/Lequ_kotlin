package com.alway.lequ_kotlin.ui.mvp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.alway.lequ_kotlin.ui.MainActivity
import com.alway.lequ_kotlin.ui.mvp.delegate.WebViewDelegate
import com.alway.lequ_kotlin.utils.encodeToString
import com.example.lequ_core.config.ConfigKeys
import com.example.lequ_core.config.LeQu
import java.net.URLEncoder

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */

fun parseWebView(title: String, url: String): String {
    val builder = StringBuilder()
    builder.append("eyepetizer://webview/?title=")
    builder.append(URLEncoder.encode(title, "utf-8"))
    builder.append("&")
    builder.append("url=")
    builder.append(URLEncoder.encode(url, "utf-8"))
    return builder.toString()
}

fun parseUri(context: Context, url: String) {
    val uri: Uri = Uri.parse(url)
    val path = uri.host
    when (path) {
        "webview" -> {
            val title = uri.getQueryParameter("title")
            val url = uri.getQueryParameter("url")
            val bundle = Bundle()
            bundle.putString("urlBase64", encodeToString(url))
            bundle.putString("title", title)

            LeQu.getConfiguration<MainActivity>(ConfigKeys.ACTIVITY).start(WebViewDelegate.newInstance(bundle))
        }
    /*//分类详情
        "category" -> {
            val path = uri.pathSegments[0]
            var intent = Intent(context, CategoriesTagListActivity::class.java)
            var bundle = Bundle()
            bundle.putInt("id", path.toInt())
            bundle.putString("title", uri.getQueryParameter("title"))
            if (uri.getQueryParameter("tabIndex") != null) {
                bundle.putInt("tabIndex", uri.getQueryParameter("tabIndex").toInt())
            }
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    //全部分类
        "categories" -> {
            var intent = Intent(context, CategoriesAllActivity::class.java)
            context.startActivity(intent)
        }
    //排行榜
        "ranklist" -> {
            var intent = Intent(context, RankListActivity::class.java)
            context.startActivity(intent)
        }
    //近期专题
        "campaign" -> {
            var intent = Intent(context, SpecialTopicsActivity::class.java)
            intent.putExtra("title", uri.getQueryParameter("title"))
            context.startActivity(intent)
        }
    //360全景视频
        "tag" -> {
            var intent = Intent(context, TagIndexActivity::class.java)
            var bundle = Bundle()
            var id = uri.pathSegments[0]
            bundle.putInt("id", id.toInt())
            bundle.putString("title", uri.getQueryParameter("title"))
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    //近期话题
        "common" -> {
            var intent = Intent(context, DiscussListActivity::class.java)
            var bundle = Bundle()
            bundle.putString("url", uri.getQueryParameter("url"))
            bundle.putString("title", uri.getQueryParameter("title"))
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    //切换tab
        "feed" -> {
            var tabIndex = uri.getQueryParameter("tabIndex").toInt()
            RxBus.default!!.post(ChangeTabEvent(tabIndex))
        }

        "video" -> {
            var intent = Intent(context, TagIndexActivity::class.java)
            var bundle = Bundle()
            bundle.putInt("id", uri.getQueryParameter("id").toInt())
            bundle.putString("title", uri.getQueryParameter("title"))
            bundle.putString("description", uri.getQueryParameter("description"))
            bundle.putString("bg", uri.getQueryParameter("bg"))
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
        else -> {
            Toast.makeText(context, path, Toast.LENGTH_SHORT).show()
        }*/
    }
}