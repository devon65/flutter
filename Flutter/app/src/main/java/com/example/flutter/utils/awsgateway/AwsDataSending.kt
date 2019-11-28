package com.example.flutter.utils.awsgateway

import android.util.Log
import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.DataSenderInterface
import com.example.flutter.utils.awsgateway.model.MessageResponse
import com.example.flutter.utils.awsgateway.model.StatusResponse
import com.example.flutter.utils.awsgateway.model.UserResponse
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut

object AwsDataSending: DataSenderInterface {
    private val TAG = this.javaClass.name

    init {
        FuelManager.instance.basePath = "https://mjinkdnq6b.execute-api.us-west-2.amazonaws.com/develop"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    override fun followUser(currentUserId: String, userToFollowId: String): Boolean {
        val requestPath = "/user/" + currentUserId + "/follow/" + userToFollowId

        val (request, response, result) = requestPath
            .httpPost()
            .responseObject<MessageResponse>()

        return if (response.statusCode == 200) {
            val messageResponse = result.component1() as MessageResponse
            if  (messageResponse.statusCode != null && messageResponse.statusCode == 200) { true }
            else {
                Log.e(TAG, messageResponse.message ?: "")
                false
            }
        } else {
            false
        }
    }

    override fun unfollowUser(currentUserId: String, userToUnfollowId: String): Boolean {
        val requestPath = "/user/" + currentUserId + "/unfollow/" + userToUnfollowId

        val (request, response, result) = requestPath
            .httpPost()
            .responseObject<MessageResponse>()

        return if (response.statusCode == 200) {
            val messageResponse = result.component1() as MessageResponse
            if  (messageResponse.statusCode != null && messageResponse.statusCode == 200) { true }
            else {
                Log.e(TAG, messageResponse.message ?: "")
                false
            }
        } else {
            false
        }
    }

    override fun postStatus(status: Status): Status? {
        val requestPath = "/user/" + status.user.userId + "/status"
        val headers = mutableMapOf("messageBody" to status.messageBody)
        if (status.attachmentUrl != null){
            headers.put("attachmentUrl", status.attachmentUrl)
        }
        val (request, response, result) = requestPath
            .httpPost()
            .header(headers)
            .responseObject<StatusResponse>()

        return if (response.statusCode == 200) {
            val statusResponse = result.component1() as StatusResponse
            if  (statusResponse.statusCode != null && statusResponse.statusCode == 200) { statusResponse.body }
            else {
                Log.e(TAG, statusResponse.message ?: "")
                null
            }
        }
        else null
    }

    override fun updateProfilePic(currentUserId: String, profilePicEncoding: String): Boolean {
        val jsonBody = "{\"profilePicEncoded\":\"%s\"}".format(profilePicEncoding)
        val requestPath = "/user/" + currentUserId + "/update"
        val (request, response, result) = requestPath
            .httpPut()
            .body(jsonBody)
            .responseObject<MessageResponse>()

        return if (response.statusCode == 200) {
            val messageResponse = result.component1() as MessageResponse
            if  (messageResponse.statusCode != null && messageResponse.statusCode == 200) { true }
            else {
                Log.e(TAG, messageResponse.message ?: "")
                false
            }
        }
        else false
    }

    override fun createUser(name: String, alias: String, profilePicEncoding: String): User? {
//        val jsonBody1 = mapOf<String, String>()
        val jsonBody = "{\"alias\":\"%s\", \"name\":\"%s\", \"profilePicEncoded\":\"%s\"}".format(alias, name, profilePicEncoding)
        val requestPath = "/user/newuser"
        val (request, response, result) = requestPath
            .httpPost()
            .body(jsonBody)
            .responseObject<UserResponse>()

        return if (response.statusCode == 200) {
            val user = result.component1() as UserResponse
            if  (user.statusCode != null && user.statusCode == 200) { user.body }
            else {
                Log.e(TAG, user.message ?: "")
                null
            }
        }
        else null
    }
}