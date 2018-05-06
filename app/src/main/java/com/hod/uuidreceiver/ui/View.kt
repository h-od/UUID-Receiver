package com.hod.uuidreceiver.ui

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.hod.uuidreceiver.App
import com.hod.uuidreceiver.R
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val component by lazy { (application as App).component }
    private val presenter by lazy { component.presenter }

    private val errorDismissedPublishSubject: PublishSubject<Unit> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialiseView()
        presenter.onViewAttached(this)
    }

    override fun onDestroy() {
        presenter.onViewDetached(this)
        super.onDestroy()
    }

    override fun fetchClicked(): Observable<Unit> = fetch.clicks().debounce(1, TimeUnit.SECONDS)

    override fun errorDismissed(): Observable<Unit> = errorDismissedPublishSubject

    override fun showCount(count: Int) {
        timesFetched.text = getString(R.string.fetched_times, count.toString())
    }

    override fun showResponseCode(code: String) {
        responseCode.text = code
    }

    override fun showNoResponseCode() {
        responseCode.text = getString(R.string.no_response_code)
    }

    override fun showError(errorMessage: String?) {
        AlertDialog.Builder(this)
                .customise(errorMessage)
                .create()
                .show()
    }

    private fun initialiseView() {
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    private fun AlertDialog.Builder.customise(errorMessage: String?): AlertDialog.Builder {
        if (errorMessage.isNullOrBlank()) {
            setMessage(R.string.error)
        } else {
            setTitle(R.string.error).setMessage(errorMessage)
        }
        return setOnDismissListener { errorDismissedPublishSubject.onNext(Unit) }
    }
}
