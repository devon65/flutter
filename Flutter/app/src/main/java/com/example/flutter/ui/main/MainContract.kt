package com.example.flutter.ui.main

import android.content.Context
import com.example.flutter.BasePresenter
import com.example.flutter.BaseStatusesView
import com.example.flutter.BaseView
import com.example.flutter.models.Hashtag
import com.example.flutter.models.Status

interface MainContract {
    interface IMainPresenter : BasePresenter {
        fun onViewCreated()
    }

    interface IMainActivity : BaseStatusesView<MainActivityPresenter> {
        fun getContext(): Context
        fun launchLoginActivity()
    }
}