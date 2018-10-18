package com.alway.lequ_kotlin.ui.lifecycle

import io.reactivex.subjects.Subject

/**
 * 创建人: Jeven
 * 邮箱:   Osbornjie@163.com
 * 功能:
 */

interface Lifecycleable<E> {

    fun provideLifecycleSubject(): Subject<E>

}