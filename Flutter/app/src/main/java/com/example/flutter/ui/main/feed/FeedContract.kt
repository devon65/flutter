package com.example.flutter.ui.main.feed

import android.content.Context
import com.example.flutter.BasePresenter
import com.example.flutter.BaseStatusesView
import com.example.flutter.BaseView
import com.example.flutter.models.Hashtag
import com.example.flutter.ui.main.MainActivityPresenter

interface FeedContract {
    interface IFeedPresenter : BasePresenter {
        fun onViewCreated(hashtagText: String)
    }

    interface IFeedActivity : BaseStatusesView<FeedPresenter> {
        fun viewHashtagFeedFragment(hashtagText: String)
    }
}