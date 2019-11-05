package com.example.flutter.utils

import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.awsgateway.AwsDataSending

object SendInfo {
    private val dataSender: DataSenderInterface = AwsDataSending

    fun followUser(currentUserId: String, userToFollowId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        dataSender.followUser(currentUserId, userToFollowId)
    }

    fun unfollowUser(currentUserId: String, userToUnfollowId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        dataSender.unfollowUser(currentUserId, userToUnfollowId)
    }

    fun postStatus(status: Status) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun updateUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun createUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}