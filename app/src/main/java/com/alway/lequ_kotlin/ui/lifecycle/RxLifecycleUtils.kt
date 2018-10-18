package com.alway.lequ_kotlin.ui.lifecycle

import android.annotation.SuppressLint
import android.support.annotation.NonNull
import com.alway.lequ_kotlin.ui.mvp.base.IView
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import com.trello.rxlifecycle2.internal.Preconditions


/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */
object RxLifecycleUtils {

    /**
     * 绑定 Activity 的指定生命周期
     *
     * @param view
     * @param event
     * @param <T>
     * @return
    </T> */
    fun <T> bindUntilEvent(@NonNull view: IView,
                           event: ActivityEvent): LifecycleTransformer<T> {
        Preconditions.checkNotNull(view, "view == null")
        return if (view is ActivityLifeCycleble) {
            bindUntilEvent(view as ActivityLifeCycleble, event)
        } else {
            throw IllegalArgumentException("view isn't ActivityLifeCyclebleable")
        }
    }

    /**
     * 绑定 Fragment 的指定生命周期
     *
     * @param view
     * @param event
     * @param <T>
     * @return
    </T> */
    fun <T> bindUntilEvent(@NonNull view: IView,
                           event: FragmentEvent): LifecycleTransformer<T> {
        Preconditions.checkNotNull(view, "view not is null")
        return if (view is FragmentLifecycleable) {
            bindUntilEvent(view as FragmentLifecycleable, event)
        } else {
            throw IllegalArgumentException("view isn't FragmentLifecycleableable")
        }
    }

    fun <T, R> bindUntilEvent(@NonNull lifecycleable: Lifecycleable<R>,
                              event: R): LifecycleTransformer<T> {
        Preconditions.checkNotNull(lifecycleable, "lifecycleable == null")
        return RxLifecycle.bindUntilEvent(lifecycleable.provideLifecycleSubject(), event)
    }


    /**
     * 绑定 Activity/Fragment 的生命周期
     *
     * @param view
     * @param <T>
     * @return
    </T> */
    fun <T> bindToLifecycle(@NonNull view: IView): LifecycleTransformer<T> {
        Preconditions.checkNotNull(view, "view == null")
        return if (view is Lifecycleable<*>) {
            bindToLifecycle(view as Lifecycleable<*>)
        } else {
            throw IllegalArgumentException("view isn't Lifecycleable")
        }
    }

    @SuppressLint("CheckResult")
    fun <T> bindToLifecycle(@NonNull lifecycleable: Lifecycleable<*>): LifecycleTransformer<T> {
        Preconditions.checkNotNull(lifecycleable, "lifecycleable == null")
        return if (lifecycleable is ActivityLifeCycleble) {
            RxLifecycleAndroid.bindActivity((lifecycleable as ActivityLifeCycleble).provideLifecycleSubject())
        } else if (lifecycleable is FragmentLifecycleable) {
            RxLifecycleAndroid.bindFragment((lifecycleable as FragmentLifecycleable).provideLifecycleSubject())
        } else {
            throw IllegalArgumentException("Lifecycleable not match")
        }
    }

}