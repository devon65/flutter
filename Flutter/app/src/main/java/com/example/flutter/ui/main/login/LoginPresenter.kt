package com.example.flutter.ui.main.login

import android.widget.Toast
import com.example.flutter.Utils.Constants
import com.example.flutter.Utils.PreferencesUtil

class LoginPresenter(loginActivity: LoginContract.ILoginActivity) : LoginContract.ILoginPresenter {
    var loginActivity : LoginContract.ILoginActivity? = loginActivity

    override fun onViewCreated() {
        loginActivity?.launchLoginScreen()
        return
    }

    override fun onLoginPressed(username: String, password: String) {
        val success = true;
        if(success){
            PreferencesUtil.setBoolean(loginActivity?.getContext(), Constants.IS_LOGGED_IN_PREF, true)
            loginActivity?.onLoginSuccess()
        }
        else{
            loginActivity?.onLoginFailure()
        }
    }

    override fun onDestroy() {
        loginActivity = null
    }

}