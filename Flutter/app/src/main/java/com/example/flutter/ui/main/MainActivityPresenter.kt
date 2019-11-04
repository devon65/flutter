package com.example.flutter.ui.main

import com.example.flutter.utils.SessionInfo
import com.example.flutter.models.Status
import com.example.flutter.models.User

class MainActivityPresenter(mainActivity: MainContract.IMainActivity) :
    MainContract.IMainPresenter {
    private var mainActivity : MainContract.IMainActivity? = mainActivity

    override fun onViewCreated() {
//        if(!PreferencesUtil.getBoolean(mainActivity?.getContext(), Constants.IS_LOGGED_IN_PREF, false)){
        if(true){
            mainActivity?.launchLoginActivity()
        }
    }

    override fun getStatusFeedList(onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit) {
        return SessionInfo.getUserFeed(SessionInfo.currentUser, onSuccess, onFailure)
    }

    override fun getUserStory(onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit) {
        return SessionInfo.getUserStory(SessionInfo.currentUser, onSuccess, onFailure)
    }

    override fun getUser(): User {
        return SessionInfo.currentUser
    }

    override fun onDestroy() {
        this.mainActivity = null
    }

}