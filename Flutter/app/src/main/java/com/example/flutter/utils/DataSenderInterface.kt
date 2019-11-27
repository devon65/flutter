package com.example.flutter.utils

import com.example.flutter.models.Status
import com.example.flutter.models.User

interface DataSenderInterface {
    fun followUser(currentUserId: String, userToFollowId: String): Boolean

    fun unfollowUser(currentUserId: String, userToUnfollowId: String): Boolean

    fun postStatus(status: Status): Status?

    fun updateProfilePic(profilePicEncoding: String): Boolean

    fun createUser(name: String, alias: String, profilePicEncoding: String): User?
}