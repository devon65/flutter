package com.example.flutter.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.flutter.R
import com.example.flutter.Utils.Constants
import com.example.flutter.ui.main.login.LoginActivity

class MainActivity : AppCompatActivity(), MainContract.IMainActivity {
    internal lateinit var presenter: MainContract.IMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setPresenter(MainActivityPresenter(this))
        presenter.onViewCreated()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun displayWeatherState() {
        return
    }

    override fun setPresenter(presenter: MainActivityPresenter) {
        this.presenter = presenter
    }


    override fun launchLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("", "")
        }
        startActivityForResult(intent, Constants.ON_LOGIN_COMPLETE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        if (requestCode == Constants.ON_LOGIN_COMPLETE) {
            if (resultCode == Activity.RESULT_OK) {
                //TODO: Do something if necessary
            }
        }
    }


    override fun getContext(): Context {
        return this
    }
}