package com.okasurya.remotedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRemoteConfig()
        displayButton()
    }

    private fun setupRemoteConfig() {
        remoteConfig = FirebaseRemoteConfig.getInstance()
    }

    private fun displayButton() {
        if (remoteConfig?.getBoolean(SHOW_BUTTON)) {
            button1.visibility = View.VISIBLE
            button1.text = remoteConfig?.getString(BUTTON1_LABEL)
        } else {
            button1.visibility = View.GONE
        }
    }
}
