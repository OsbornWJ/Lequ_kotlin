package com.alway.lequ_kotlin.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import com.alway.lequ_kotlin.ui.lifecycle.FragmentLifecycleable
import com.alway.lequ_kotlin.ui.mvp.base.IPersenter
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

    protected val mPresenter by lazy { initPersenter() }

    fun getParentDelegate(): LeQuDelegate {
        return parentFragment as LeQuDelegate
    }

    override fun provideLifecycleSubject(): Subject<FragmentEvent> {
        return lifecycleSubject
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleSubject.onNext(FragmentEvent.ATTACH)
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
        mPresenter!!.onDestory()
    }

    override fun onDetach() {
        super.onDetach()
        lifecycleSubject.onNext(FragmentEvent.DETACH)
    }

    interface OnFragmentOpenDrawerListener {
        fun onOpenDrawer()
    }
}
