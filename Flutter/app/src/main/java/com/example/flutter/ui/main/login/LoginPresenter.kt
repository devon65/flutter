package com.example.flutter.ui.main.login

import com.example.flutter.utils.Constants
import com.example.flutter.utils.DummyData
import com.example.flutter.utils.PreferencesUtil
import com.example.flutter.utils.SessionInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPresenter(loginActivity: LoginContract.ILoginActivity) : LoginContract.ILoginPresenter {
    var loginActivity : LoginContract.ILoginActivity? = loginActivity

    override fun onViewCreated() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                loginActivity?.launchLoadingScreen()
            }

            SessionInfo.ninjaSignIn(
                onSuccess = {
                    loginActivity?.onLoginSuccess()
                }, onFailure = {
                    loginActivity?.launchLoginScreen()
                })
        }
    }

    override fun onLoginPressed(username: String, password: String) {
//        SessionInfo.getUserById("0", {
////            PreferencesUtil.setBoolean(loginActivity?.getContext(), Constants.IS_LOGGED_IN_PREF, true)
//            SessionInfo.currentUser = it
//            loginActivity?.onLoginSuccess()
//        },{
//            loginActivity?.onLoginFailure()
//        })
        if (username.isNullOrEmpty() || password.isNullOrEmpty()){
            loginActivity?.onLoginFailure()
            return
        }
        SessionInfo.userSignIn(username, password,
            onSuccess = {
                loginActivity?.onLoginSuccess()
            }, onFailure = {
                loginActivity?.onLoginFailure()
            })
    }

    override fun onSignupPressed(
        name: String,
        alias: String,
        password: String,
        profilePicEncoding: String
    ) {
        if (name.isNullOrEmpty() || alias.isNullOrEmpty() || password.isNullOrEmpty() || profilePicEncoding.isNullOrEmpty()){
            loginActivity?.onSignupFailure()
            return
        }
        SessionInfo.userSignUp(name, alias, password, profilePicEncoding,
            onSuccess = {
                loginActivity?.onSignupSuccess()
            }, onFailure = {
                loginActivity?.onSignupFailure()
            })
    }

    override fun onDestroy() {
        loginActivity = null
    }

}