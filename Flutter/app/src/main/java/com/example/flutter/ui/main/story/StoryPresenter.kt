package com.example.flutter.ui.main.story

import com.example.flutter.Utils.SessionInfo
import com.example.flutter.models.Status
import com.example.flutter.models.User

class StoryPresenter(storyActivity: StoryContract.IStoryActivity): StoryContract.IStoryPresenter {

    private var storyActivity: StoryContract.IStoryActivity? = storyActivity

    override fun onViewCreated(userId: String) {
        storyActivity?.viewUserStoryFragment(userId)
    }

    override fun getUser(userId: String?): User? {
        return SessionInfo.getUserById(userId)
    }

    override fun getUserStory(user: User?): List<Status> {
        return SessionInfo.getUserStory(user)
    }

    override fun onDestroy() {
        this.storyActivity = null
    }
}