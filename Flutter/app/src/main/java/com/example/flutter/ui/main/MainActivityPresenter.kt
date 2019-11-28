package com.example.flutter.ui.main

import com.example.flutter.utils.SessionInfo
import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.SendData

class MainActivityPresenter(mainActivity: MainContract.IMainActivity) :
    MainContract.IMainPresenter {
    private var mainActivity : MainContract.IMainActivity? = mainActivity

    override fun onViewCreated() {
        val isLoggedIn = false //PreferencesUtil.getBoolean(mainActivity?.getContext(), Constants.IS_LOGGED_IN_PREF, false)){
        if(!isLoggedIn){
            mainActivity?.launchLoginActivity()
        }
        else{
            mainActivity?.loadUserHomepage()
        }
    }

    override fun getStatusFeedList(
        onSuccess: (List<Status>) -> Unit,
        onFailure: () -> Unit,
        status: Status?
    ) {
        return SessionInfo.getUserFeed(SessionInfo.currentUser, onSuccess, onFailure, status)
    }

    override fun getUserStory(
        onSuccess: (List<Status>) -> Unit,
        onFailure: () -> Unit,
        status: Status?
    ) {
        return SessionInfo.getUserStory(SessionInfo.currentUser, onSuccess, onFailure, status)
    }

    override fun getUser(): User {
        return SessionInfo.currentUser
    }

    override fun onLogout(onSuccess: () -> Unit, onFailure: () -> Unit) {
        SessionInfo.userLogout(onSuccess = {
            SessionInfo.clearCache()
            onSuccess()
        }, onFailure = onFailure)
    }

    override fun postStatus(
        status: Status,
        onSuccess: (status: Status) -> Unit,
        onFailure: () -> Unit
    ) {
        SendData.postStatus(status, onSuccess, onFailure)
    }

    override fun updateProfile(
        profilePicEncoded: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        SendData.updateProfile(profilePicEncoded, onSuccess, onFailure)
    }

    override fun onDestroy() {
        this.mainActivity = null
    }

}