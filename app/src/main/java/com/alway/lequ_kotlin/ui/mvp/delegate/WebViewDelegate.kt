package com.alway.lequ_kotlin.ui.mvp.delegate

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.BaseDelegate
import com.alway.lequ_kotlin.utils.decode
import java.util.HashMap

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
class WebViewDelegate : BaseDelegate() {

    private var url: String? = null
    private var webView: WebView? = null
    private var back: ImageView? = null
    private var titleText: TextView? = null
    private var chromeClient: WebChromeClient? = null

    private val headers: Map<String, String>
        get() {
            val map = HashMap<String, String>()
            map.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            map.put("Connection", "keep-alive")
            map.put("Accept", "*/*")
            map.put("Cookie", "add cookies here")
            map.put("Content-Encoding", "gzip")
            map.put("clientversion", "")
            map.put("devicetype", 3.toString() + "")
            map.put("deviceinfo", "android")
            map.put("qudao", "")
            map.put("clientid", "")
            map.put("devicetoken", "")
            return map

        }

    override fun setLayout(): Any {
        return R.layout.web_profile_webview
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

    override fun initPersenter() {
        /*webView!!.settings.defaultTextEncodingName = "UTF-8"
        val bundle = arguments ?: return
        url = bundle.getString("urlBase64")
        if (TextUtils.isEmpty(url)) {
            url = bundle.getString("url")
        } else {
            url = String(decode(url))
        }
        val title = bundle.getString("title")
        titleText!!.text = title
        CookieSyncManager.createInstance(_mActivity)
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.removeSessionCookie()//移除
        CookieSyncManager.getInstance().sync()
        CookieSyncManager.createInstance(this)
        CookieManager.getInstance().removeAllCookie()
        initializeWebView(webView!!)
        webView!!.loadUrl(url, headers)
        back!!.setOnClickListener {
            if (webView!!.canGoBack()) {
                webView!!.goBack()//返回上一页面
            } else {
                finish()
            }
        }*/
    }

}