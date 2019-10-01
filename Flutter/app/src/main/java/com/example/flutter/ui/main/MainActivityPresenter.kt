package com.example.flutter.ui.main

import com.example.flutter.Utils.Constants
import com.example.flutter.Utils.SharedPreferencesUtil

class MainActivityPresenter(mainActivity: MainContract.IMainActivity) :
    MainContract.IMainPresenter {
    private var mainActivity : MainContract.IMainActivity? = mainActivity

    override fun onViewCreated() {
//        if(!SharedPreferencesUtil.getBoolean(mainActivity?.getContext(), Constants.IS_LOGGED_IN_PREF, false)){
        if(true){
            mainActivity?.launchLoginActivity()
        }
    }

    override fun onLoadWeatherTapped() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        this.mainActivity = null
    }

}