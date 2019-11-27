package com.example.flutter.utils

import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.awsgateway.AwsDataSending
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object SendData {
    private val dataSender: DataSenderInterface = AwsDataSending

    fun followUser(currentUserId: String, userToFollowId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        dataSender.followUser(currentUserId, userToFollowId)
    }

    fun unfollowUser(currentUserId: String, userToUnfollowId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        dataSender.unfollowUser(currentUserId, userToUnfollowId)
    }

    fun postStatus(status: Status, onSuccess: (status: Status) -> Unit, onFailure: () -> Unit) {
        GlobalScope.launch(IO) {
            val postedStatus = dataSender.postStatus(status)
            if (postedStatus == null) withContext(Main){ onFailure() }
            else withContext(Main) { onSuccess(postedStatus) }
        }
    }

    fun updateUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun createUser(name: String,
                   alias: String,
                   profilePicEncoding: String,
                   onSuccess: (user: User) -> Unit,
                   onFailure: () -> Unit) {
        GlobalScope.launch(IO) {
            val user = dataSender.createUser(name, alias, profilePicEncoding)
            if (user == null) withContext(Main){ onFailure() }
            else withContext(Main){ onSuccess(user) }
        }
    }
}