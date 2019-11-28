package com.example.flutter.ui.main.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flutter.R
import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.ui.main.feed.HashtagFeedActivity
import com.example.flutter.ui.main.personlist.PersonListActivity
import com.example.flutter.ui.main.status.StatusViewActivity

class UserStoryActivity : AppCompatActivity(), StoryContract.IStoryActivity, StoryBoardFragment.OnStoryBoardInteractionListener {

    internal lateinit var presenter: StoryContract.IStoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_story)

        val userId = intent.getStringExtra(USER_ID)
        val alias = intent.getStringExtra(USER_ALIAS)

        setPresenter(StoryPresenter(this))
//        presenter.onViewCreated(userId)
        viewUserStoryFragment(userId, alias)
    }

    override fun setPresenter(presenter: StoryContract.IStoryPresenter) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun viewUserStoryFragment(userId: String?, alias: String?) {
        val fragment = StoryBoardFragment.newInstance(userId, alias, loadUponCreation = true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.user_story_holder, fragment)
            .commit()
    }

    override fun launchHashtagFeed(hashtagText: String) {
        val intent = Intent(this, HashtagFeedActivity::class.java).apply {
            putExtra(HashtagFeedActivity.HASHTAG_ID, hashtagText)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
    }

    override fun launchUserStory(userMentionText: String, userId: String?) {
        if (userId != null || userMentionText != null) { viewUserStoryFragment(userId, userMentionText) }
    }

    override fun launchStatusView(status: Status) {
        val intent = Intent(this, StatusViewActivity::class.java).apply {
            putExtra(StatusViewActivity.STATUS_ID, status.statusId)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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

    override fun getUser(userId: String?, alias: String?, onSuccess: (User) -> Unit, onFailure: () -> Unit) {
        presenter.getUser(userId, alias, onSuccess, onFailure)
    }

    override fun getUserStory(
        user: User?,
        onSuccess: (List<Status>) -> Unit,
        onFailure: () -> Unit,
        status: Status?
    ) {
        return presenter.getUserStory(user, onSuccess, onFailure, status)
    }

    override fun checkIsFollowing(userId: String, onSuccess: (isFollowing: Boolean) -> Unit) {
        presenter.checkIsFollowing(userId, onSuccess)
    }

    override fun followUser(userId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        presenter.followUser(userId, onSuccess, onFailure)
    }

    override fun unfollowUser(userId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        presenter.unfollowUser(userId, onSuccess, onFailure)
    }

    override fun updateProfile(
        profilePicEncoded: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        presenter.updateProfile(profilePicEncoded, onSuccess, onFailure)
    }

    override fun postStatus(
        status: Status,
        onSuccess: (status: Status) -> Unit,
        onFailure: () -> Unit
    ) {
        presenter.postStatus(status, onSuccess, onFailure)
    }

    companion object{
        const val USER_ID = "user_id"
        const val USER_ALIAS = "user_alias"
    }
}
