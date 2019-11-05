package com.example.flutter.ui.main.feed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.flutter.R
import com.example.flutter.models.Status
import com.example.flutter.ui.main.status.StatusViewActivity
import com.example.flutter.ui.main.story.UserStoryActivity

class HashtagFeedActivity : AppCompatActivity(), FeedContract.IFeedActivity, NewsFeedFragment.OnNewsFeedInteractionListener {

    internal lateinit var presenter: FeedContract.IFeedPresenter
    lateinit var hashtagTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hashtag_feed)

        val hashtag = intent.getStringExtra(HASHTAG_ID) ?: ""

        hashtagTitle = findViewById(R.id.hashtag_feed_title)

        setPresenter(FeedPresenter(this))
        presenter.onViewCreated(hashtag)
    }

    override fun setPresenter(presenter: FeedContract.IFeedPresenter) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun viewHashtagFeedFragment(hashtagText: String) {
        hashtagTitle.text = String.format(getString(R.string.hashtag_feed_title), hashtagText)
        val fragment = NewsFeedFragment.newInstance(hashtagText, loadUponCreation = true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.feed_fragment_holder, fragment)
            .commit()
    }

    override fun launchHashtagFeed(hashtagText: String) {
        viewHashtagFeedFragment(hashtagText)
    }

    override fun launchUserStory(userMentionText: String, userId: String?) {
        if (userId != null || userMentionText != null) {
            val intent = Intent(this, UserStoryActivity::class.java).apply {
                putExtra(UserStoryActivity.USER_ID, userId)
                putExtra(UserStoryActivity.USER_ALIAS, userMentionText)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }
    }

    override fun launchStatusView(status: Status) {
        val intent = Intent(this, StatusViewActivity::class.java).apply {
            putExtra(StatusViewActivity.STATUS_ID, status.statusId)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
    }

    override fun getStatusFeedList(hashtagText: String?, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit) {
        presenter.getStatusFeedList(hashtagText, onSuccess, onFailure)
    }

    companion object {
        const val HASHTAG_ID = "hashtagId"
    }
}
