package com.alway.lequ_kotlin.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
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

    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    protected var mPresenter: P? = null

    private val mDelegate = SupportActivityDelegate(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        mDelegate.onCreate(savedInstanceState)
        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }
        initPersenter()
        AppManager.appManager!!.addActivity(this)
        initData()
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
        mDelegate.onDestroy()
        mPresenter!!.onDestory()
        mPresenter = null
        super.onDestroy()
        System.gc()
        System.runFinalization()
    }

    open fun getLayoutId(): Int {
         return 0
    }

    abstract fun initData()

    open fun initPersenter() {

    }

    override fun provideLifecycleSubject(): Subject<ActivityEvent> {
        return lifecycleSubject
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDelegate.onPostCreate(savedInstanceState)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev)
    }

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        mDelegate.fragmentAnimator = fragmentAnimator
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return mDelegate.fragmentAnimator
    }

    override fun onBackPressed() {
        mDelegate.onBackPressed()
    }

    override fun onBackPressedSupport() {
        mDelegate.onBackPressedSupport()
    }

    override fun extraTransaction(): ExtraTransaction {
        return mDelegate.extraTransaction()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
       return mDelegate.onCreateFragmentAnimator()
    }

    override fun getSupportDelegate(): SupportActivityDelegate {
        return mDelegate
    }

    // 选择性拓展其他方法

    fun loadRootFragment(containerId: Int, @NonNull toFragment: ISupportFragment) {
        mDelegate.loadRootFragment(containerId, toFragment)
    }

    fun start(toFragment: ISupportFragment) {
        mDelegate.start(toFragment)
    }

    /**
     * @param launchMode Same as Activity's LaunchMode.
     */
    fun start(toFragment: ISupportFragment, @ISupportFragment.LaunchMode launchMode: Int) {
        mDelegate.start(toFragment, launchMode)
    }

    /**
     * It is recommended to use [SupportFragment.startWithPopTo].
     *
     * @see .popTo
     * @see .start
     */
    fun startWithPopTo(toFragment: ISupportFragment) {
        mDelegate.startWithPop(toFragment)
    }

    /**
     * Pop the fragment.
     */
    fun pop() {
        mDelegate.pop()
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     */
    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment)
    }

    /**
     * If you want to begin another FragmentTransaction immediately after popTo(), use this method.
     * 如果你想在出栈后, 立刻进行FragmentTransaction操作，请使用该方法
     */
    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean, afterPopTransactionRunnable: Runnable) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable)
    }

    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean, afterPopTransactionRunnable: Runnable, popAnim: Int) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim)
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