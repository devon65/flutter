package com.example.flutter.ui.main

import android.content.Context
import com.example.flutter.BasePresenter
import com.example.flutter.BaseStatusesView
import com.example.flutter.models.Status
import com.example.flutter.models.User

interface MainContract {
    interface IMainPresenter : BasePresenter {
        fun onViewCreated()
        fun getStatusFeedList(onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit, status: Status? = null)
        fun getUserStory(onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit, status: Status? = null)
        fun postStatus(status: Status, onSuccess: (status: Status) -> Unit, onFailure: () -> Unit)
        fun updateProfile(profilePicEncoded: String, onSuccess: () -> Unit, onFailure: () -> Unit)
        fun getUser(): User
        fun onLogout(onSuccess: () -> Unit, onFailure: () -> Unit)
    }

    interface IMainActivity : BaseStatusesView<MainActivityPresenter> {
        fun launchLoginActivity()
        fun loadUserHomepage()
        fun onLogout()
    }
}