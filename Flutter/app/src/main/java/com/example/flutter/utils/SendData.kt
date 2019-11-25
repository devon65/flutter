package com.example.flutter.utils

import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.awsgateway.AwsDataSending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object SendData {
    private val dataSender: DataSenderInterface = AwsDataSending

    fun followUser(currentUserId: String, userToFollowId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        dataSender.followUser(currentUserId, userToFollowId)
    }

    fun unfollowUser(currentUserId: String, userToUnfollowId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        dataSender.unfollowUser(currentUserId, userToUnfollowId)
    }

    fun postStatus(status: Status, onSuccess: (status: Status) -> Unit, onFailure: () -> Unit) {
        dataSender.postStatus(status, onSuccess, onFailure)
    }

    fun updateUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun createUser(name: String,
                   alias: String,
                   profilePicEncoding: String,
                   onSuccess: (user: User) -> Unit,
                   onFailure: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val user = dataSender.createUser(name, alias, profilePicEncoding)
            if (user == null) { onFailure() }
            else { onSuccess(user) }
        }
    }
}