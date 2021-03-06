package com.example.flutter.ui.main.login

import com.example.flutter.utils.Constants
import com.example.flutter.utils.DummyData
import com.example.flutter.utils.PreferencesUtil
import com.example.flutter.utils.SessionInfo

class LoginPresenter(loginActivity: LoginContract.ILoginActivity) : LoginContract.ILoginPresenter {
    var loginActivity : LoginContract.ILoginActivity? = loginActivity

    override fun onViewCreated() {
        loginActivity?.launchLoginScreen()
        return
    }

    override fun onLoginPressed(username: String, password: String) {
        SessionInfo.getUserById("0", {
//            PreferencesUtil.setBoolean(loginActivity?.getContext(), Constants.IS_LOGGED_IN_PREF, true)
            SessionInfo.currentUser = it
            loginActivity?.onLoginSuccess()
        },{
            loginActivity?.onLoginFailure()
        })
    }

    override fun onSignupPressed(name: String, alias: String, password: String) {
        onLoginPressed(alias, password)
    }

    override fun onDestroy() {
        loginActivity = null
    }

}