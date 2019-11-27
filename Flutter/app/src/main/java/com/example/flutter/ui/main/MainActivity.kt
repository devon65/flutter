package com.example.flutter.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.flutter.R
import com.example.flutter.utils.Constants
import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.ui.main.feed.HashtagFeedActivity
import com.example.flutter.ui.main.feed.NewsFeedFragment
import com.example.flutter.ui.main.login.LoginActivity
import com.example.flutter.ui.main.status.StatusViewActivity
import com.example.flutter.ui.main.story.StoryBoardFragment
import com.example.flutter.ui.main.story.UserStoryActivity
import com.example.flutter.ui.main.personlist.PersonListActivity
import com.example.flutter.utils.SessionInfo

class MainActivity : AppCompatActivity(), MainContract.IMainActivity,
    NewsFeedFragment.OnNewsFeedInteractionListener, StoryBoardFragment.OnStoryBoardInteractionListener {

    internal lateinit var presenter: MainContract.IMainPresenter
    private lateinit var viewPager: ViewPager
    private lateinit var menuTabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.view_pager)
        menuTabs = findViewById(R.id.tabs)
        val sectionsPagerAdapter = HomePagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        menuTabs.setupWithViewPager(viewPager)
        viewPager.currentItem = HomePagerAdapter.FEED_FRAGMENT

        setPresenter(MainActivityPresenter(this))

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        presenter.onViewCreated()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.action_logout -> onLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setPresenter(presenter: MainActivityPresenter) {
        this.presenter = presenter
    }

    override fun onLogout() {
        presenter.onLogout(onSuccess = {
            launchLoginActivity()
            clearStatuses()
            viewPager.currentItem = HomePagerAdapter.FEED_FRAGMENT
        }, onFailure = {
            Toast.makeText(this, "Could not log out. Please try again later.", Toast.LENGTH_LONG).show()
        })
    }

    override fun launchLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("", "")
        }
        startActivityForResult(intent, Constants.ON_LOGIN_COMPLETE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.ON_LOGIN_COMPLETE) {
            if (resultCode == Activity.RESULT_OK) {
                loadUserHomepage()
            }
        }
    }

    override fun loadUserHomepage(){
        val newsFeedFragment = supportFragmentManager.findFragmentByTag(
            "android:switcher:" + R.id.view_pager + ":" + HomePagerAdapter.FEED_FRAGMENT) as NewsFeedFragment
        newsFeedFragment.loadNewsFeed()
        val storyFragment = supportFragmentManager.findFragmentByTag(
            "android:switcher:" + R.id.view_pager + ":" + HomePagerAdapter.STORY_FRAGMENT) as StoryBoardFragment
        storyFragment.loadStoryBoard()
    }

    private fun clearStatuses(){
        val newsFeedFragment = supportFragmentManager.findFragmentByTag(
            "android:switcher:" + R.id.view_pager + ":" + HomePagerAdapter.FEED_FRAGMENT) as NewsFeedFragment
        newsFeedFragment.clearStatuses()
        val storyFragment = supportFragmentManager.findFragmentByTag(
            "android:switcher:" + R.id.view_pager + ":" + HomePagerAdapter.STORY_FRAGMENT) as StoryBoardFragment
        storyFragment.clearStatuses()
    }

    override fun postStatus(
        status: Status,
        onSuccess: (status: Status) -> Unit,
        onFailure: () -> Unit
    ) {
        presenter.postStatus(status, onSuccess, onFailure)
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
        if (userId != null || userMentionText != null) {
            val intent = Intent(this, UserStoryActivity::class.java).apply {
                putExtra(UserStoryActivity.USER_ID, userId)
                putExtra(UserStoryActivity.USER_ALIAS, userMentionText)
            }
            startActivity(intent)
        }
    }

    override fun launchStatusView(status: Status) {
        val intent = Intent(this, StatusViewActivity::class.java).apply {
            putExtra(StatusViewActivity.STATUS_ID, status.statusId)
        }
        startActivity(intent)
    }

    override fun launchPersonList(personListType: String, userId: String?, usersName: String?) {
        val intent = Intent(this, PersonListActivity::class.java).apply {
            putExtra(PersonListActivity.PERSON_LIST_TYPE, personListType)
            putExtra(PersonListActivity.USER_ID, userId)
            putExtra(PersonListActivity.NAME, usersName)
        }
        startActivity(intent)
    }

    override fun getStatusFeedList(hashtagText: String?, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit) {
        return presenter.getStatusFeedList(onSuccess, onFailure)
    }

    override fun getUser(
        userId: String?,
        alias: String?,
        onSuccess: (User) -> Unit,
        onFailure: () -> Unit
    ) {
        onSuccess(presenter.getUser())
    }

    override fun getUserStory(user: User?, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit, status: Status?) {
        return presenter.getUserStory(onSuccess, onFailure, status)
    }

    override fun followUser(userId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        //can't follow self
    }

    override fun unfollowUser(userId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        //can't unfollow self
    }
}