package com.example.flutter.ui.main

import android.content.Context
import com.example.flutter.BasePresenter
import com.example.flutter.BaseStatusesView
import com.example.flutter.models.Status
import com.example.flutter.models.User

interface MainContract {
    interface IMainPresenter : BasePresenter {
        fun onViewCreated()
        fun getStatusFeedList(): List<Status>
        fun getUserStory(): List<Status>
        fun getUser(): User
    }

    interface IMainActivity : BaseStatusesView<MainActivityPresenter> {
        fun getContext(): Context
        fun launchLoginActivity()
        fun onLogout()
    }
}