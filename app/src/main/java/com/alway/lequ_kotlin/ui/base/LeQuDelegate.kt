package com.alway.lequ_kotlin.ui.base

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import com.alway.lequ_kotlin.R
import com.alway.lequ_kotlin.ui.lifecycle.FragmentLifecycleable
import com.joanzapata.iconify.widget.IconTextView
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

    private var _mBackToFirstListener: OnBackToFirstListener? = null

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

    fun initToobar(view: View) {
        if (view.findViewById<IconTextView>(R.id.it_right) != null) {
            view.findViewById<IconTextView>(R.id.it_right).setOnClickListener { pop() }
        }
    }

    interface OnBackToFirstListener {
        fun onBackToFirstFragment()
    }
}
