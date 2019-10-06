package com.example.flutter.ui.main.login

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.flutter.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), LoginContract.ILoginActivity {
    lateinit var loginPresenter: LoginContract.ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        login_button.setOnClickListener({ loginPresenter.onLoginPressed(login_username.text.toString(), login_password.text.toString()) })

        setPresenter(LoginPresenter(this))
        loginPresenter.onViewCreated()
    }


    override fun setPresenter(presenter: LoginContract.ILoginPresenter) {
        this.loginPresenter = presenter
    }

    override fun onLoginSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onLoginFailure() {
        Toast.makeText(this, "Login didn't work", Toast.LENGTH_LONG).show()
    }

    override fun getContext(): Context {
        return this
    }



}
