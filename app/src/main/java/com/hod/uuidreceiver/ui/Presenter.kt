package com.hod.uuidreceiver.ui

import com.hod.uuidreceiver.data.DataContract
import com.hod.uuidreceiver.ui.contract.AbsPresenter
import io.reactivex.Observable
import io.reactivex.Scheduler

class MainPresenter(
        private val dataManager: DataContract, private val mainThread: Scheduler
) : AbsPresenter<MainPresenter.View>() {

    override fun onViewAttached(view: View) {
        subscribeForInitialData(view)
        subscribeToClicks(view)
        subscribeForErrorRecovery(view)
    }

    private fun subscribeForInitialData(view: View) {
        TODO("show last fetched data when app starts")
    }

    private fun subscribeToClicks(view: View) {
        TODO("when button is clicked, trigger new call to fetch data")
    }

    private fun subscribeForErrorRecovery(view: View) {
        TODO("when error occurs in the stream, need to resubscribe")
    }

    interface View : AbsPresenter.View {
        fun fetchClicked(): Observable<Unit>
        fun errorDismissed(): Observable<Unit>

        fun showCount(count: Int)
        fun showResponseCode(code: String)
        fun showNoResponseCode()
        fun showError(errorMessage: String?)
    }
}