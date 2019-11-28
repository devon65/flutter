package com.example.flutter.ui.main.story

import com.example.flutter.utils.SessionInfo
import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.SendData

class StoryPresenter(storyActivity: StoryContract.IStoryActivity): StoryContract.IStoryPresenter {

    private var storyActivity: StoryContract.IStoryActivity? = storyActivity

    override fun onViewCreated(userId: String) {
//        storyActivity?.viewUserStoryFragment(userId)
    }

    override fun getUser(userId: String?, alias: String?, onSuccess: (User) -> Unit, onFailure: () -> Unit) {
        if (userId != null) SessionInfo.getUserById(userId, onSuccess, onFailure)
            else SessionInfo.getUserByAlias(alias, onSuccess, onFailure)
    }

    override fun getUserStory(
        user: User?,
        onSuccess: (List<Status>) -> Unit,
        onFailure: () -> Unit,
        status: Status?
    ) {
        SessionInfo.getUserStory(user, onSuccess, onFailure, status)
    }

    override fun onDestroy() {
        this.storyActivity = null
    }

    override fun checkIsFollowing(userId: String, onSuccess: (isFollowing: Boolean) -> Unit) {
        SessionInfo.checkIsFollowing(userId, onSuccess)
    }

    override fun followUser(userId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        SendData.followUser(userId, onSuccess, onFailure)
    }

    override fun unfollowUser(userId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        SendData.unfollowUser(userId, onSuccess, onFailure)
    }

    override fun postStatus(
        status: Status,
        onSuccess: (status: Status) -> Unit,
        onFailure: () -> Unit
    ) {
        SendData.postStatus(status, onSuccess, onFailure)
    }

    override fun updateProfile(
        profilePicEncoded: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        SendData.updateProfile(profilePicEncoded, onSuccess, onFailure)
    }
}