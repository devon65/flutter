package com.example.flutter.ui.main

import com.example.flutter.Utils.Constants
import com.example.flutter.Utils.PreferencesUtil
import com.example.flutter.Utils.SessionInfo
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

    override fun getStatusFeedList(): List<Status> {
        return SessionInfo.getUserFeed(SessionInfo.currentUser)
    }

    override fun getUserStory(): List<Status> {
        return SessionInfo.getUserStory(SessionInfo.currentUser)
    }

    override fun getUser(): User {
        return SessionInfo.currentUser
    }

    override fun onDestroy() {
        this.mainActivity = null
    }

}