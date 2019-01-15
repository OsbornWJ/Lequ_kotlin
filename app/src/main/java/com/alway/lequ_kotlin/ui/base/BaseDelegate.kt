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
import com.alway.lequ_kotlin.ui.mvp.base.IPersenter
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragmentDelegate
import me.yokeyword.fragmentation.SupportHelper
import me.yokeyword.fragmentation.anim.FragmentAnimator




/**
 * 创建人: Jeven
 * 邮箱:   Osboenjie@163.com
 * 功能:   fragment base
 */

abstract class BaseDelegate: Fragment(), ISupportFragment {

    var DELEGATE = SupportFragmentDelegate(this)
    protected var _mActivity: FragmentActivity? = null

    abstract fun setLayout(): Any

    abstract fun onBindView(savedInstanceState: Bundle?, @NonNull rootView: View)

    @SuppressLint("MissingSuperCall")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        DELEGATE.onAttach(context as Activity)
        _mActivity = DELEGATE.activity
    }

    fun getProxyActivity(): ProxyActivity {
        return _mActivity as ProxyActivity
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

    @Suppress("UnnecessaryVariable")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = when {
            setLayout() is Int -> inflater.inflate(setLayout() as Int, container, false)
            setLayout() is View -> setLayout() as View
            else -> throw ClassCastException("type of setLayout() must be int or View!")
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView(savedInstanceState, view)
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

    override fun post(runnable: Runnable) {
        DELEGATE.post(runnable)
    }


    /****************************************以下为可选方法(Optional methods)******************************************************/
    // 自定制Support时，可移除不必要的方法

    /**
     * 隐藏软键盘
     */
    protected fun hideSoftInput() {
        DELEGATE.hideSoftInput()
    }

    /**
     * 显示软键盘,调用该方法后,会在onPause时自动隐藏软键盘
     */
    protected fun showSoftInput(view: View) {
        DELEGATE.showSoftInput(view)
    }

    /**
     * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
     *
     * @param containerId 容器id
     * @param toFragment  目标Fragment
     */
    fun loadRootFragment(containerId: Int, toFragment: ISupportFragment) {
        DELEGATE.loadRootFragment(containerId, toFragment)
    }

    fun loadRootFragment(containerId: Int, toFragment: ISupportFragment, addToBackStack: Boolean, allowAnim: Boolean) {
        DELEGATE.loadRootFragment(containerId, toFragment, addToBackStack, allowAnim)
    }

    fun start(toFragment: ISupportFragment) {
        DELEGATE.start(toFragment)
    }

    /**
     * @param launchMode Similar to Activity's LaunchMode.
     */
    fun start(toFragment: ISupportFragment, @ISupportFragment.LaunchMode launchMode: Int) {
        DELEGATE.start(toFragment, launchMode)
    }

    /**
     * Launch an fragment for which you would like a result when it poped.
     */
    fun startForResult(toFragment: ISupportFragment, requestCode: Int) {
        DELEGATE.startForResult(toFragment, requestCode)
    }

    /**
     * Start the target Fragment and pop itself
     */
    fun startWithPop(toFragment: ISupportFragment) {
        DELEGATE.startWithPop(toFragment)
    }

    /**
     * @see .popTo
     * @see .start
     */
    fun startWithPopTo(toFragment: ISupportFragment, targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        DELEGATE.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment)
    }

    fun replaceFragment(toFragment: ISupportFragment, addToBackStack: Boolean) {
        DELEGATE.replaceFragment(toFragment, addToBackStack)
    }

    fun pop() {
        DELEGATE.pop()
    }

    fun popChild() {
        DELEGATE.popChild()
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     *
     *
     * 出栈到目标fragment
     *
     * @param targetFragmentClass   目标fragment
     * @param includeTargetFragment 是否包含该fragment
     */
    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        DELEGATE.popTo(targetFragmentClass, includeTargetFragment)
    }

    /**
     * 获取栈内的fragment对象
     */
    fun <T : ISupportFragment> findChildFragment(fragmentClass: Class<T>): T? {
        return SupportHelper.findFragment(childFragmentManager, fragmentClass)
    }

}