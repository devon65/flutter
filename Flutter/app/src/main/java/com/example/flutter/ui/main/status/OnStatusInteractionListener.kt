package com.example.flutter.ui.main.status

import com.example.flutter.models.Status

interface OnStatusInteractionListener {

    fun onHashtagClicked(hashtagText: String)

    fun onUserMentionClicked(userMentionText: String, userId: String? = null)

    fun onStatusClicked(status: Status)
}