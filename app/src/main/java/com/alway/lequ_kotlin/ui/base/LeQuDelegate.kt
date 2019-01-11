package com.alway.lequ_kotlin.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import com.alway.lequ_kotlin.ui.lifecycle.FragmentLifecycleable
import com.alway.lequ_kotlin.ui.mvp.delegate.HomeDelegate
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject


/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
abstract class LeQuDelegate: PermissionCheckerDelegate(), FragmentLifecycleable {

    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()

    protected var _mBackToFirstListener: OnBackToFirstListener? = null

    fun getParentDelegate(): LeQuDelegate {
        return parentFragment as LeQuDelegate
    }

    override fun provideLifecycleSubject(): Subject<FragmentEvent> {
        return lifecycleSubject
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleSubject.onNext(FragmentEvent.ATTACH)
        if (context is OnBackToFirstListener) {
            _mBackToFirstListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnBackToFirstListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        super.onPause()
        lifecycleSubject.onNext(FragmentEvent.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleSubject.onNext(FragmentEvent.STOP)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleSubject.onNext(FragmentEvent.DESTROY)

    }

    override fun onDetach() {
        super.onDetach()
        lifecycleSubject.onNext(FragmentEvent.DETACH)
        _mBackToFirstListener = null
    }

    /**
     * 处理回退事件
     *
     * @return
     */
    override fun onBackPressedSupport(): Boolean {
        if (childFragmentManager.backStackEntryCount > 1) {
            popChild()
        } else {
            if (this is HomeDelegate) {   // 如果是 第一个Fragment 则退出app
                _mActivity!!.finish()
            } else {                                    // 如果不是,则回到第一个Fragment
                checkNotNull(_mBackToFirstListener) {"_mBackToFirstListener is null"}.onBackToFirstFragment()
            }
        }
        return true
    }

    interface OnBackToFirstListener {
        fun onBackToFirstFragment()
    }
}
