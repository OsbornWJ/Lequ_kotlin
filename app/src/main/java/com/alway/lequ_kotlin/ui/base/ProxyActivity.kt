package com.alway.lequ_kotlin.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.alway.lequ_kotlin.ui.lifecycle.ActivityLifeCycleble
import com.alway.lequ_kotlin.ui.mvp.base.IPersenter
import com.example.lequ_core.utils.AppManager
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import me.yokeyword.fragmentation.*
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * 创建人：wenjie on 2018/6/7
 * 邮箱： Osbornjie@163.com
 * 功能： 代理Activity
 */

@SuppressLint("Registered")
abstract class ProxyActivity<P: IPersenter?>: AppCompatActivity(), ISupportActivity, ActivityLifeCycleble {

    private var lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    protected var mPresenter: P? = null

    protected val DELEGATE = SupportActivityDelegate(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        DELEGATE.onCreate(savedInstanceState)
        when {
            setLayout() is Int -> setContentView(setLayout() as Int)
            setLayout() is View -> setContentView(setLayout() as View)
            else -> throw ClassCastException("type of setLayout() must be int or View!")
        }
        initPersenter()
        AppManager.appManager!!.addActivity(this)
        initData(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    override fun onPause() {
        super.onPause()
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleSubject.onNext(ActivityEvent.STOP)
    }

    override fun onDestroy() {
        DELEGATE.onDestroy()
        mPresenter!!.onDestory()
        mPresenter = null
        super.onDestroy()
        System.gc()
        System.runFinalization()
    }

    abstract fun setLayout(): Any

    abstract fun initData(savedInstanceState: Bundle?)

    open fun initPersenter() {

    }

    override fun provideLifecycleSubject(): Subject<ActivityEvent> {
        return lifecycleSubject
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        DELEGATE.onPostCreate(savedInstanceState)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return DELEGATE.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev)
    }

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        DELEGATE.fragmentAnimator = fragmentAnimator
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return DELEGATE.fragmentAnimator
    }

    override fun onBackPressed() {
        DELEGATE.onBackPressed()
    }

    override fun onBackPressedSupport() {
        DELEGATE.onBackPressedSupport()
    }

    override fun extraTransaction(): ExtraTransaction {
        return DELEGATE.extraTransaction()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
       return DELEGATE.onCreateFragmentAnimator()
    }

    override fun getSupportDelegate(): SupportActivityDelegate {
        return DELEGATE
    }

    // 选择性拓展其他方法

    fun loadRootFragment(containerId: Int, @NonNull toFragment: ISupportFragment) {
        DELEGATE.loadRootFragment(containerId, toFragment)
    }

    fun start(toFragment: ISupportFragment) {
        DELEGATE.start(toFragment)
    }

    /**
     * @param launchMode Same as Activity's LaunchMode.
     */
    fun start(toFragment: ISupportFragment, @ISupportFragment.LaunchMode launchMode: Int) {
        DELEGATE.start(toFragment, launchMode)
    }

    /**
     * It is recommended to use [SupportFragment.startWithPopTo].
     *
     * @see .popTo
     * @see .start
     */
    fun startWithPopTo(toFragment: ISupportFragment) {
        DELEGATE.startWithPop(toFragment)
    }

    /**
     * Pop the fragment.
     */
    fun pop() {
        DELEGATE.pop()
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     */
    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        DELEGATE.popTo(targetFragmentClass, includeTargetFragment)
    }

    /**
     * If you want to begin another FragmentTransaction immediately after popTo(), use this method.
     * 如果你想在出栈后, 立刻进行FragmentTransaction操作，请使用该方法
     */
    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean, afterPopTransactionRunnable: Runnable) {
        DELEGATE.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable)
    }

    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean, afterPopTransactionRunnable: Runnable, popAnim: Int) {
        DELEGATE.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim)
    }

    /**
     * 得到位于栈顶Fragment
     */
    fun getTopFragment(): ISupportFragment {
        return SupportHelper.getTopFragment(supportFragmentManager)
    }

    /**
     * 获取栈内的fragment对象
     */
    fun <T : ISupportFragment> findFragment(fragmentClass: Class<T>): T {
        return SupportHelper.findFragment(supportFragmentManager, fragmentClass)
    }
}