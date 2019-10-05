package com.example.flutter.ui.main.feed

import com.example.flutter.BasePresenter
import com.example.flutter.BaseStatusesView
import com.example.flutter.models.Status

interface FeedContract {
    interface IFeedPresenter : BasePresenter {
        fun onViewCreated(hashtagText: String)
        fun getStatusFeedList(hashtagText: String?): List<Status>
    }

    interface IFeedActivity : BaseStatusesView<IFeedPresenter> {
        fun viewHashtagFeedFragment(hashtagText: String)
    }
}