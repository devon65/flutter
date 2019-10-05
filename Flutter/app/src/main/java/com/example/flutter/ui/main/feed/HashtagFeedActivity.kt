package com.example.flutter.ui.main.feed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.flutter.R
import com.example.flutter.models.Hashtag
import com.example.flutter.models.Status

class HashtagFeedActivity : AppCompatActivity(), FeedContract.IFeedActivity, NewsFeedFragment.OnNewsFeedInteractionListener {

    internal lateinit var presenter: FeedContract.IFeedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hashtag_feed)

        val hashtag = intent.getStringExtra(HASHTAG_ID) ?: ""

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



    override fun launchViewHashtagActivity(hashtag: String) {

    }

    override fun launchViewProfileActivity(userId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun launchViewStatusActivity(status: Status) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onHashtagClicked(hashtagText: String) {
        viewHashtagFeedFragment(hashtagText)
    }

    override fun onUserMentionClicked(userMentionText: String, userId: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusClicked(status: Status) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val HASHTAG_ID = "hashtagId"
    }
}
