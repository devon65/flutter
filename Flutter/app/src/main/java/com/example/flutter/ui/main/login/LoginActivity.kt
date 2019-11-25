package com.example.flutter.ui.main.login

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.flutter.R


class LoginActivity : AppCompatActivity(), LoginContract.ILoginActivity,
    LoginFragment.OnLoginFragmentInteractionListener, SignUpFragment.OnSignUpFragmentInteractionListener {
    private lateinit var loginPresenter: LoginContract.ILoginPresenter
    private lateinit var loadingSpinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loadingSpinner = findViewById(R.id.login_loading)

        setPresenter(LoginPresenter(this))
        loginPresenter.onViewCreated()
    }


    override fun setPresenter(presenter: LoginContract.ILoginPresenter) {
        this.loginPresenter = presenter
    }

    override fun onLoginClicked(username: String, password: String) {
        loadingSpinner.visibility = View.VISIBLE
        loginPresenter.onLoginPressed(username, password)
    }

    override fun onSignUpPressed(
        nameOfUser: String,
        userAlias: String,
        password: String,
        profilePicEncoding: String
    ) {
        loadingSpinner.visibility = View.VISIBLE
        loginPresenter.onSignupPressed(nameOfUser, userAlias, password, profilePicEncoding)
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

    override fun launchLoadingScreen() {
        val fragment = LoginLoadingFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.login_fragment_holder, fragment)
            .commit()
    }

    override fun onLoginSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onLoginFailure() {
        loadingSpinner.visibility = View.GONE
        Toast.makeText(this, "Login didn't work", Toast.LENGTH_LONG).show()
    }

    override fun onSignupSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onSignupFailure() {
        loadingSpinner.visibility = View.GONE
        Toast.makeText(this, "Sign up didn't work", Toast.LENGTH_LONG).show()
    }

    override fun getContext(): Context {
        return this
    }

    override fun onBackPressed() {
        //do nothing
    }

}
