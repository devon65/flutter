package com.example.flutter.ui.main.login

import android.widget.Toast
import com.example.flutter.Utils.Constants
import com.example.flutter.Utils.SharedPreferencesUtil

class LoginPresenter(loginActivity: LoginContract.ILoginActivity) : LoginContract.ILoginPresenter {
    var loginActivity : LoginContract.ILoginActivity? = loginActivity

    override fun onViewCreated() {
        return
    }

    override fun onLoginPressed(username: String, password: String) {
        val success = true;
        if(success){
            SharedPreferencesUtil.setBoolean(loginActivity?.getContext(), Constants.IS_LOGGED_IN_PREF, true)
            Toast.makeText(loginActivity?.getContext(), username + " " + password, Toast.LENGTH_LONG).show()
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