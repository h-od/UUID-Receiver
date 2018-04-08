package com.hod.uuidreceiver.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hod.uuidreceiver.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainPresenter.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun fetchClicked(): Observable<Unit> {
        TODO("provide presenter with click events")
    }

    override fun errorDismissed(): Observable<Unit> {
        TODO("provide presenter with error dismissed events")
    }

    override fun showCount(count: Int) {
        TODO("provide view with count data to show")
    }

    override fun showResponseCode(code: String) {
        TODO("provide view with response code data to show")
    }

    override fun showNoResponseCode() {
        TODO("let view know that there is no response code to show")
    }

    override fun showError(errorMessage: String?) {
        TODO("inform user about error that occurred")
    }
}
