package com.example.flutter.ui.main.feed

import com.example.flutter.utils.SessionInfo
import com.example.flutter.models.Status

class FeedPresenter(feedActivity: FeedContract.IFeedActivity): FeedContract.IFeedPresenter {

    private var feedActivity: FeedContract.IFeedActivity? = feedActivity

    override fun onViewCreated(hashtagText: String) {
        feedActivity?.viewHashtagFeedFragment(hashtagText)
    }

    override fun onDestroy() {
        this.feedActivity = null
    }

    override fun getStatusFeedList(hashtagText: String?, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit) {
        SessionInfo.getStatusesByHashtag(hashtagText ?: "", onSuccess, onFailure)
    }
}