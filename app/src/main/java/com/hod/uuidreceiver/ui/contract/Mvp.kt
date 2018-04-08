package com.hod.uuidreceiver.ui.contract

import android.support.annotation.CallSuper
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class AbsPresenter<in V : AbsPresenter.View> {

    private val viewSubscriptions by lazy { CompositeDisposable() }

    abstract fun onViewAttached(view: V)

    @CallSuper
    open fun onViewDetached(view: V) {
        this.viewSubscriptions.clear()
    }

    private fun clearOnDetached(disposable: Disposable) {
        this.viewSubscriptions.add(disposable)
    }

    protected fun <T> Observable<T>.subscribeUntilDetached(onNext: (T) -> Unit): Disposable =
            subscribe(onNext).apply { clearOnDetached(this) }

    protected fun <T> Observable<T>.subscribeUntilDetached(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable =
            subscribe(onNext, onError).apply { clearOnDetached(this) }

    protected fun <T> Single<T>.subscribeUntilDetached(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable =
            subscribe(onNext, onError).apply { clearOnDetached(this) }

    interface View
}
