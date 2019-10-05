package com.example.flutter.ui.main.story

import com.example.flutter.BasePresenter
import com.example.flutter.BaseStatusesView
import com.example.flutter.models.Status
import com.example.flutter.models.User

interface StoryContract {
    interface IStoryPresenter : BasePresenter {
        fun onViewCreated(userId: String)
        fun getUser(userId: String?): User?
        fun getUserStory(user: User?): List<Status>
    }

    interface IStoryActivity : BaseStatusesView<StoryPresenter> {
        fun viewUserStoryFragment(userId: String)
    }
}