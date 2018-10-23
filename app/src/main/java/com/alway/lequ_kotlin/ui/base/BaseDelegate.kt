package com.alway.lequ_kotlin.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import com.trello.rxlifecycle2.components.support.RxFragment
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragmentDelegate
import me.yokeyword.fragmentation.anim.FragmentAnimator


/**
 * 创建人: Jeven
 * 邮箱:   Osboenjie@163.com
 * 功能:   fragment base
 */

abstract class BaseDelegate: Fragment(), ISupportFragment {

    val DELEGATE = SupportFragmentDelegate(this)
    protected var _mActivity: FragmentActivity? = null

    abstract fun setLayout(): Any

    abstract fun onBindView(savedInstanceState: Bundle?, @NonNull rootView: View)

    abstract fun initPersenter()

    @SuppressLint("MissingSuperCall")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        DELEGATE.onAttach(context as Activity)
        _mActivity = DELEGATE.activity
    }

    fun getProxyActivity(): ProxyActivity<*> {
        return _mActivity as ProxyActivity<*>
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DELEGATE.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        DELEGATE.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        DELEGATE.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = when {
            setLayout() is Int -> inflater.inflate(setLayout() as Int, container, false)
            setLayout() is View -> setLayout() as View
            else -> throw ClassCastException("type of setLayout() must be int or View!")
        }
        onBindView(savedInstanceState, rootView)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        DELEGATE.onResume()
    }

    override fun onPause() {
        super.onPause()
        DELEGATE.onPause()
    }

    override fun onDestroyView() {
        DELEGATE.onDestroyView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        DELEGATE.onDestroy()
        super.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        DELEGATE.onHiddenChanged(hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        DELEGATE.setUserVisibleHint(isVisibleToUser)
    }


    override fun getSupportDelegate(): SupportFragmentDelegate {
        return DELEGATE
    }

    override fun extraTransaction(): ExtraTransaction {
        return DELEGATE.extraTransaction()
    }

    override fun enqueueAction(runnable: Runnable) {
        DELEGATE.enqueueAction(runnable)
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        DELEGATE.onEnterAnimationEnd(savedInstanceState)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        DELEGATE.onLazyInitView(savedInstanceState)
    }

    override fun onSupportVisible() {
        DELEGATE.onSupportVisible()
    }

    override fun onSupportInvisible() {
        DELEGATE.onSupportInvisible()
    }

    override fun isSupportVisible(): Boolean {
        return DELEGATE.isSupportVisible
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DELEGATE.onCreateFragmentAnimator()
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return DELEGATE.fragmentAnimator
    }

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator) {
        DELEGATE.fragmentAnimator = fragmentAnimator
    }

    override fun setFragmentResult(resultCode: Int, bundle: Bundle) {
        DELEGATE.setFragmentResult(resultCode, bundle)
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        DELEGATE.onFragmentResult(requestCode, resultCode, data)
    }

    override fun onNewBundle(args: Bundle) {
        DELEGATE.onNewBundle(args)
    }

    override fun putNewBundle(newBundle: Bundle) {
        DELEGATE.putNewBundle(newBundle)
    }

    override fun onBackPressedSupport(): Boolean {
        return DELEGATE.onBackPressedSupport()
    }

}