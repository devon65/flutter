package com.example.flutter.ui.main

import android.content.Context
import com.example.flutter.BasePresenter
import com.example.flutter.BaseView

interface MainContract {
    interface IMainPresenter : BasePresenter {
        fun onViewCreated()
        fun onLoadWeatherTapped()
    }

    interface IMainActivity : BaseView<MainActivityPresenter> {
        fun getContext(): Context
        fun displayWeatherState()
        fun launchLoginActivity()
    }
}