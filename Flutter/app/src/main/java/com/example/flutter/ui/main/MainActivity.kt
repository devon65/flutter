package com.example.flutter.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.flutter.R
import com.example.flutter.Utils.Constants
import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.ui.main.feed.HashtagFeedActivity
import com.example.flutter.ui.main.feed.NewsFeedFragment
import com.example.flutter.ui.main.login.LoginActivity
import com.example.flutter.ui.main.story.StoryBoardFragment
import com.example.flutter.ui.main.story.UserStoryActivity

class MainActivity : AppCompatActivity(), MainContract.IMainActivity,
    NewsFeedFragment.OnNewsFeedInteractionListener, StoryBoardFragment.OnStoryBoardInteractionListener {

    internal lateinit var presenter: MainContract.IMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = HomePagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        setPresenter(MainActivityPresenter(this))

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
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
        if (requestCode == Constants.ON_LOGIN_COMPLETE) {
            if (resultCode == Activity.RESULT_OK) {
                //TODO: Do something if necessary
            }
        }
    }

    override fun getContext(): Context {
        return this
    }

    override fun launchHashtagFeed(hashtagText: String) {
        val intent = Intent(this, HashtagFeedActivity::class.java).apply {
            putExtra(HashtagFeedActivity.HASHTAG_ID, hashtagText)
        }
        startActivity(intent)
    }

    override fun launchUserStory(userMentionText: String, userId: String?) {
        if (userId != null) {
            val intent = Intent(this, UserStoryActivity::class.java).apply {
                putExtra(UserStoryActivity.USER_ID, userId)
            }
            startActivity(intent)
        }
    }

    override fun launchStatusView(status: Status) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStatusFeedList(hashtagText: String?): List<Status> {
        return presenter.getStatusFeedList()
    }

    override fun getUser(userId: String?): User {
        return presenter.getUser()
    }

    override fun getUserStory(user: User?): List<Status> {
        return presenter.getUserStory()
    }
}