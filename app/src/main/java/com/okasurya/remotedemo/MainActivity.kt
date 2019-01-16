package com.okasurya.remotedemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRemoteConfig()
    }

    private fun setupRemoteConfig() {
        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .build()
        remoteConfig.setConfigSettings(configSettings)
        remoteConfig.setDefaults(R.xml.default_config)

        remoteConfig.fetch(TimeUnit.HOURS.toSeconds(3))
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Fetch Succeeded", Toast.LENGTH_SHORT).show()
                    remoteConfig.activateFetched()
                } else {
                    Toast.makeText(this, "Fetch Failed", Toast.LENGTH_SHORT).show()
                }
                displayButton()
            }
    }

    private fun displayButton() {
        if(remoteConfig?.getBoolean(SHOW_BUTTON)) {
            button1.visibility = View.VISIBLE
            button1.text = remoteConfig?.getString(BUTTON1_LABEL)
        } else {
            button1.visibility = View.GONE
        }
    }
}
