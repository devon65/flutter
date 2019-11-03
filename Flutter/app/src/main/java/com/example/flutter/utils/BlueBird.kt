package com.example.flutter.utils

import android.app.Application
import android.content.Context

class BlueBird: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: BlueBird
            private set

        val context: Context
            get() {
                return instance.applicationContext
            }
    }
}