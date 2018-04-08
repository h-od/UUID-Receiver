package com.hod.uuidreceiver.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hod.uuidreceiver.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }
}
