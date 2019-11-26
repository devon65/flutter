package com.example.flutter.ui.main.login

import android.content.Context
import com.example.flutter.BasePresenter
import com.example.flutter.BaseView

interface LoginContract {
    interface ILoginPresenter : BasePresenter {
        fun onViewCreated()
        fun onLoginPressed(username: String, password: String)
        fun onSignupPressed(name: String, alias: String, password: String, profilePicEncoding: String)
    }

    interface ILoginActivity : BaseView<ILoginPresenter> {
        fun onLoginSuccess()
        fun onLoginFailure()
        fun onSignupSuccess()
        fun onSignupFailure(message: String? = null)
        fun launchLoginScreen()
        fun launchSignUpScreen()
        fun launchLoadingScreen()
        fun getContext(): Context
    }
}