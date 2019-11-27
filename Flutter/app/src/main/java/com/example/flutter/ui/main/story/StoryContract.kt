package com.example.flutter.ui.main.story

import com.example.flutter.BasePresenter
import com.example.flutter.BaseStatusesView
import com.example.flutter.models.Status
import com.example.flutter.models.User

interface StoryContract {
    interface IStoryPresenter : BasePresenter {
        fun onViewCreated(userId: String)
        fun getUser(userId: String?, alias: String?, onSuccess: (User) -> Unit, onFailure: () -> Unit)
        fun getUserStory(user: User?, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit, status: Status? = null)
        fun followUser(userId: String, onSuccess: () -> Unit, onFailure: () -> Unit)
        fun unfollowUser(userId: String, onSuccess: () -> Unit, onFailure: () -> Unit)
    }

    interface IStoryActivity : BaseStatusesView<IStoryPresenter> {
        fun viewUserStoryFragment(userId: String?, alias: String?)
    }
}