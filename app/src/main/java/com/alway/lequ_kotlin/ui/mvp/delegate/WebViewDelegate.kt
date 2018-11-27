@file:Suppress("DEPRECATION")

package com.alway.lequ_kotlin.ui.mvp.delegate

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.*
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.base.BaseDelegate
import com.alway.lequ_kotlin.utils.decode
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.web_profile_webview.*
import java.util.*

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:   WebView
 */
class WebViewDelegate : BaseDelegate() {

    private var url: String? = null
    private var chromeClient: WebChromeClient? = null

    companion object {
        fun newInstance(bundle: Bundle): WebViewDelegate {
            val delegate = WebViewDelegate()
            delegate.arguments = bundle
            return delegate
        }
    }

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

    override fun initPersenter() {

    }

    @SuppressLint("SetTextI18n")
    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        it_right.text = "{fa-chevron-left}"
        it_left.visibility = View.GONE
        webView!!.settings.defaultTextEncodingName = "UTF-8"
        val bundle = arguments ?: return
        url = bundle.getString("urlBase64")
        url = if (TextUtils.isEmpty(url)) {
            bundle.getString("url")
        } else {
            String(decode(url))
        }
        val titleText = bundle.getString("title")
        tv_bar_title.text = titleText

        CookieSyncManager.createInstance(_mActivity)
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.removeSessionCookie()//移除
        CookieSyncManager.getInstance().sync()
        CookieSyncManager.createInstance(_mActivity)
        CookieManager.getInstance().removeAllCookie()
        initializeWebView(webView!!)
        webView.loadUrl(url, headers)

        it_right.setOnClickListener {
            if (webView!!.canGoBack()) {
                webView.goBack()//返回上一页面
            } else {
                onBackPressedSupport()
            }
        }

    }

    private fun initializeWebView(mWebView: WebView) {
        val ws = mWebView.settings
        ws.useWideViewPort = true
        ws.loadWithOverviewMode = true// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.javaScriptEnabled = true
        ws.setSupportMultipleWindows(true)
        ws.domStorageEnabled = true
        // ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);// 排版适应屏幕
        mWebView.settings.loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19
        ws.cacheMode = WebSettings.LOAD_NO_CACHE
        _mActivity!!.deleteDatabase("WebView.db")
        _mActivity!!.deleteDatabase("WebViewCache.db")
        mWebView.clearHistory()
        mWebView.clearFormData()
        chromeClient = WebChromeClient()
        mWebView.webViewClient = MyWebViewClient()
        mWebView.webChromeClient = chromeClient
    }

    // 监听
    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url, headers)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            if (!webView.settings.loadsImagesAutomatically) {
                webView.settings.loadsImagesAutomatically = true
            }
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) =
                handler.proceed()
    }

    override fun onDestroyView() {
        webView!!.removeAllViews()
        webView!!.loadUrl("about:blank")
        webView!!.stopLoading()
        webView!!.webChromeClient = null
        webView!!.webViewClient = null
        webView!!.destroy()
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()
        webView!!.onPause()
        webView!!.pauseTimers()
    }

    override fun onResume() {
        super.onResume()
        webView!!.onResume()
        webView!!.resumeTimers()
    }

}