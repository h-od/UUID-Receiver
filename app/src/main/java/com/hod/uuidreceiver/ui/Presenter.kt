package com.hod.uuidreceiver.ui

import com.hod.uuidreceiver.data.DataContract
import com.hod.uuidreceiver.ui.contract.AbsPresenter
import io.reactivex.Observable
import io.reactivex.Scheduler

class MainPresenter(
        private val dataManager: DataContract, private val mainThread: Scheduler, private val backgroundThread: Scheduler
) : AbsPresenter<MainPresenter.View>() {

    override fun onViewAttached(view: View) {
        subscribeForInitialData(view)
        subscribeToClicks(view)
        subscribeForErrorRecovery(view)
    }

    private fun subscribeForInitialData(view: View) {
        dataManager.fetchPrevious()
                .map { it.toModel() }
                .subscribeUntilDetached({ view.handle(it) }, { view.handle(it) })
    }

    private fun subscribeToClicks(view: View) {
        view.fetchClicked()
                .fetchData()
                .subscribeUntilDetached({ view.handle(it) }, { view.handle(it) })
    }

    private fun subscribeForErrorRecovery(view: View) {
        view.errorDismissed()
                .subscribeUntilDetached { subscribeToClicks(view) }
    }

    private fun View.handle(result: Model) {

        if (result.count.isPositive()) showCount(result.count)

        if (result.code.isNotBlank()) {
            showResponseCode(result.code)
        } else {
            showNoResponseCode()
        }
    }

    private fun View.handle(result: Throwable) = showError(result.message)

    private fun Observable<Unit>.fetchData(): Observable<Model> =
            flatMap { _ ->
                dataManager.fetch()
                        .subscribeOn(backgroundThread)
                        .observeOn(mainThread)
                        .map { it.toModel() }
            }

    private fun Int.isPositive(): Boolean = this > 0

    interface View : AbsPresenter.View {
        fun fetchClicked(): Observable<Unit>
        fun errorDismissed(): Observable<Unit>

        fun showCount(count: Int)
        fun showResponseCode(code: String)
        fun showNoResponseCode()
        fun showError(errorMessage: String?)
    }
}