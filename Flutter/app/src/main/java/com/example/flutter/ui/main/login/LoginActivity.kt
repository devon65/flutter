package com.example.flutter.ui.main.login

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.flutter.R
import com.example.flutter.ui.main.feed.NewsFeedFragment
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), LoginContract.ILoginActivity,
    LoginFragment.OnLoginFragmentInteractionListener, SignUpFragment.OnSignUpFragmentInteractionListener {
    lateinit var loginPresenter: LoginContract.ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setPresenter(LoginPresenter(this))
        loginPresenter.onViewCreated()
    }


    override fun setPresenter(presenter: LoginContract.ILoginPresenter) {
        this.loginPresenter = presenter
    }

    override fun onLoginClicked(username: String, password: String) {
        loginPresenter.onLoginPressed(username, password)
    }

    override fun onSignUpPressed(nameOfUser: String, userAlias: String, password: String) {
        loginPresenter.onSignupPressed(nameOfUser, userAlias, password)
    }

    override fun selectProfilePicture(callback: (bitmap: Bitmap) -> Unit) {

    }

    override fun launchLoginScreen() {
        val fragment = LoginFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.login_fragment_holder, fragment)
            .commit()
    }

    override fun launchSignUpScreen() {
        val fragment = SignUpFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.login_fragment_holder, fragment)
            .commit()
    }

    override fun onLoginSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onLoginFailure() {
        Toast.makeText(this, "Login didn't work", Toast.LENGTH_LONG).show()
    }

    override fun onSignupSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onSignupFailure() {
        Toast.makeText(this, "Sign up didn't work", Toast.LENGTH_LONG).show()
    }

    override fun getContext(): Context {
        return this
    }

    override fun onBackPressed() {
        //do nothing
    }

}
