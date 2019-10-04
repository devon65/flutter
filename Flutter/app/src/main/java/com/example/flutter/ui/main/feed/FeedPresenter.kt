package com.example.flutter.ui.main.feed

class FeedPresenter(feedActivity: FeedContract.IFeedActivity): FeedContract.IFeedPresenter {

    private var feedActivity: FeedContract.IFeedActivity? = feedActivity

    override fun onViewCreated(hashtagText: String) {
        feedActivity?.viewHashtagFeedFragment(hashtagText)
    }

    override fun onDestroy() {
        this.feedActivity = null
    }
}