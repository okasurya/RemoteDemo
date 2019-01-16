package com.okasurya.remotedemo

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        fetchRemoteConfig()
    }

    private fun fetchRemoteConfig() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .build()
        remoteConfig.setConfigSettings(configSettings)
        remoteConfig.setDefaults(R.xml.default_config)

        remoteConfig.fetch(TimeUnit.HOURS.toSeconds(3))
            .addOnCompleteListener(ThreadPool(), OnCompleteListener {
               if(it.isSuccessful) {
                   remoteConfig.activateFetched()
               } else {
                   Log.d("MainApp", "Failed to fetch remote config")
               }
            })
            .addOnFailureListener {
                Log.d("MainApp", "Failed to fetch remote config")
                it.printStackTrace()
            }
    }
}
