package com.example.flutter.ui.main.feed

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.flutter.R
import com.example.flutter.models.Hashtag
import com.example.flutter.models.Status
import com.example.flutter.ui.main.story.UserStoryActivity

class HashtagFeedActivity : AppCompatActivity(), FeedContract.IFeedActivity, NewsFeedFragment.OnNewsFeedInteractionListener {

    internal lateinit var presenter: FeedContract.IFeedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hashtag_feed)

        val hashtag = intent.getStringExtra(HASHTAG_ID) ?: ""

        val hashtagTitle = findViewById<TextView>(R.id.hashtag_feed_title)
        hashtagTitle.text = String.format(getString(R.string.hashtag_feed_title), hashtag)

        setPresenter(FeedPresenter(this))
        presenter.onViewCreated(hashtag)
    }

    override fun setPresenter(presenter: FeedPresenter) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun viewHashtagFeedFragment(hashtagText: String) {
        val fragment = NewsFeedFragment.newInstance(hashtagText)
        supportFragmentManager.beginTransaction()
            .replace(R.id.feed_fragment_holder, fragment)
            .commit()
    }

    override fun launchHashtagFeed(hashtagText: String) {
        viewHashtagFeedFragment(hashtagText)
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
        return presenter.getStatusFeedList(hashtagText)
    }

    companion object {
        const val HASHTAG_ID = "hashtagId"
    }
}
