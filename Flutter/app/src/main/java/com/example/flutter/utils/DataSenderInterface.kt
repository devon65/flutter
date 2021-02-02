package com.example.flutter.utils

import com.example.flutter.models.Status
import com.example.flutter.models.User

interface DataSenderInterface {
    fun followUser(currentUserId: String, userToFollowId: String)

    fun unfollowUser(currentUserId: String, userToUnfollowId: String)

    fun postStatus(status: Status, onSuccess: (status: Status) -> Unit, onFailure: () -> Unit)

    fun updateUser(user: User)

    fun createUser(user: User)
}