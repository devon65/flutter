package com.example.flutter.utils.awsgateway

import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.DataSenderInterface

object AwsDataSending: DataSenderInterface {

    override fun followUser(currentUserId: String, userToFollowId: String) {

    }

    override fun unfollowUser(currentUserId: String, userToUnfollowId: String) {

    }

    override fun postStatus(
        status: Status,
        onSuccess: (status: Status) -> Unit,
        onFailure: () -> Unit
    ) {

    }

    override fun updateUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}