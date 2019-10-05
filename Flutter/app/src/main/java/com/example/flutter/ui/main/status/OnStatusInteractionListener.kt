package com.example.flutter.ui.main.status

import com.example.flutter.models.Status

interface OnStatusInteractionListener {

    fun launchHashtagFeed(hashtagText: String)

    fun launchUserStory(userMentionText: String, userId: String? = null)

    fun launchStatusView(status: Status)
}