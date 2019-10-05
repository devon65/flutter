package com.example.flutter.ui.main

import com.example.flutter.Utils.Constants
import com.example.flutter.Utils.PreferencesUtil

class MainActivityPresenter(mainActivity: MainContract.IMainActivity) :
    MainContract.IMainPresenter {
    private var mainActivity : MainContract.IMainActivity? = mainActivity

    override fun onViewCreated() {
        if(!PreferencesUtil.getBoolean(mainActivity?.getContext(), Constants.IS_LOGGED_IN_PREF, false)){
//        if(true){
            mainActivity?.launchLoginActivity()
        }
    }

    override fun onDestroy() {
        this.mainActivity = null
    }

}