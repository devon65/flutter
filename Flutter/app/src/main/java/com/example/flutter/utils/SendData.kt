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

    fun followUser(userToFollowId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val currentUserId = SessionInfo.currentUser.userId
        if (currentUserId.isEmpty()) { onFailure() }

        GlobalScope.launch(IO) {
            val isSuccess = dataSender.followUser(currentUserId, userToFollowId)
            if (isSuccess) withContext(Main){ onSuccess() }
            else withContext(Main) { onFailure() }
        }
    }

    fun unfollowUser(userToUnfollowId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val currentUserId = SessionInfo.currentUser.userId
        if (currentUserId.isEmpty()) { onFailure() }

        GlobalScope.launch(IO) {
            val isSuccess = dataSender.unfollowUser(currentUserId, userToUnfollowId)
            if (isSuccess) withContext(Main){ onSuccess() }
            else withContext(Main) { onFailure() }
        }
    }

    fun postStatus(status: Status, onSuccess: (status: Status) -> Unit, onFailure: () -> Unit) {
        GlobalScope.launch(IO) {
            val postedStatus = dataSender.postStatus(status)
            if (postedStatus == null) withContext(Main){ onFailure() }
            else withContext(Main) { onSuccess(postedStatus) }
        }
    }

    fun updateProfile(profilePicEncoding: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val currentUserId = SessionInfo.currentUser.userId
        if (currentUserId.isEmpty()) { onFailure() }

        GlobalScope.launch(IO) {
            val success = dataSender.updateProfilePic(currentUserId, profilePicEncoding)
            if (success) withContext(Main){ onSuccess() }
            else withContext(Main) { onFailure() }
        }
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