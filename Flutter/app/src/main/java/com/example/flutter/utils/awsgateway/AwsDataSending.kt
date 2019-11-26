package com.example.flutter.utils.awsgateway

import android.util.Log
import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.DataSenderInterface
import com.example.flutter.utils.awsgateway.model.UserResponse
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpPost

object AwsDataSending: DataSenderInterface {
    private val TAG = this.javaClass.name

    init {
        FuelManager.instance.basePath = "https://mjinkdnq6b.execute-api.us-west-2.amazonaws.com/develop"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    override fun followUser(currentUserId: String, userToFollowId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unfollowUser(currentUserId: String, userToUnfollowId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun postStatus(
        status: Status,
        onSuccess: (status: Status) -> Unit,
        onFailure: () -> Unit
    ): Status {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateProfilePic(profilePicEncoding: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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