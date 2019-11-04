package com.example.flutter.ui.main.story

import com.example.flutter.utils.SessionInfo
import com.example.flutter.models.Status
import com.example.flutter.models.User

class StoryPresenter(storyActivity: StoryContract.IStoryActivity): StoryContract.IStoryPresenter {

    private var storyActivity: StoryContract.IStoryActivity? = storyActivity

    override fun onViewCreated(userId: String) {
//        storyActivity?.viewUserStoryFragment(userId)
    }

    override fun getUser(userId: String?, alias: String?, onSuccess: (User) -> Unit, onFailure: () -> Unit) {
        if (userId != null) SessionInfo.getUserById(userId, onSuccess, onFailure)
            else SessionInfo.getUserByAlias(alias, onSuccess, onFailure)
    }

    override fun getUserStory(user: User?, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit) {
        SessionInfo.getUserStory(user, onSuccess, onFailure)
    }

    override fun onDestroy() {
        this.storyActivity = null
    }
}