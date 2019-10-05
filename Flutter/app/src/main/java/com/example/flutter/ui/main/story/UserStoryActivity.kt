package com.example.flutter.ui.main.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flutter.R
import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.ui.main.feed.HashtagFeedActivity

class UserStoryActivity : AppCompatActivity(), StoryContract.IStoryActivity, StoryBoardFragment.OnStoryBoardInteractionListener {

    internal lateinit var presenter: StoryContract.IStoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_story)

        val userId = intent.getStringExtra(USER_ID) ?: ""

        setPresenter(StoryPresenter(this))
        presenter.onViewCreated(userId)
    }

    override fun setPresenter(presenter: StoryPresenter) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun viewUserStoryFragment(userId: String) {
        val fragment = StoryBoardFragment.newInstance(userId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.user_story_holder, fragment)
            .commit()
    }

    override fun launchHashtagFeed(hashtagText: String) {
        val intent = Intent(this, HashtagFeedActivity::class.java).apply {
            putExtra(HashtagFeedActivity.HASHTAG_ID, hashtagText)
        }
        startActivity(intent)
    }

    override fun launchUserStory(userMentionText: String, userId: String?) {
        if (userId != null) { viewUserStoryFragment(userId) }
    }

    override fun launchStatusView(status: Status) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(userId: String?): User? {
        return presenter.getUser(userId)
    }

    override fun getUserStory(user: User?): List<Status> {
        return presenter.getUserStory(user)
    }

    companion object{
        const val USER_ID = "user_id"
    }
}
