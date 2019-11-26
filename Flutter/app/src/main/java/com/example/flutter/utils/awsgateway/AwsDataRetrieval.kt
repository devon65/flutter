package com.example.flutter.utils.awsgateway

// https://www.baeldung.com/kotlin-fuel

import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.Constants
import com.example.flutter.utils.DataExtractionInterface
import com.example.flutter.utils.awsgateway.model.StatusListResponse
import com.example.flutter.utils.awsgateway.model.UserListResponse
import com.example.flutter.utils.awsgateway.model.UserResponse
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet

object AwsDataRetrieval: DataExtractionInterface {

    init {
        FuelManager.instance.basePath = "https://mjinkdnq6b.execute-api.us-west-2.amazonaws.com/develop"
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
    }

    override fun getStatusesByHashtag(tag: String, nextIndex: Int): List<Status> {
        val hashtag = tag.replace("#", "")
        val requestPath = "/hashtag/" + hashtag
        val (request, response, result) = requestPath
            .httpGet(listOf("nextindex" to nextIndex, "pagesize" to Constants.STATUS_PAGE_SIZE))
            .responseObject<StatusListResponse>()

        return if (response.statusCode == 200) {
            val statuses = result.component1() as StatusListResponse
            statuses.body ?: listOf()
        }
        else listOf()
    }

    override fun getUserById(userId: String?): User? {
        if (userId == null) return null
        val requestPath = "/user/" + userId
        val (request, response, result) = requestPath
            .httpGet()
            .responseObject<UserResponse>()

        return if (response.statusCode == 200) {
            val userResponse = result.component1() as UserResponse
            userResponse.body
        }
        else null
    }

    override fun getUserByAlias(alias: String?): User? {
        if (alias == null) return null
        val requestPath = "/user/alias/" + alias
        val (request, response, result) = requestPath
            .httpGet()
            .responseObject<UserResponse>()

        return if (response.statusCode == 200) {
            val userResponse = result.component1() as UserResponse
            userResponse.body
        }
        else null
    }

    override fun getUserFeed(user: User?, nextIndex: Int): List<Status> {
        if (user == null) return listOf()
        val requestPath = "/user/" + user.userId + "/feed"
        val (request, response, result) = requestPath
            .httpGet(listOf("nextindex" to nextIndex, "pagesize" to Constants.STATUS_PAGE_SIZE))
            .responseObject<StatusListResponse>()

        return if (response.statusCode == 200) {
            val statuses = result.component1() as StatusListResponse
            statuses.body ?: listOf()
        }
        else listOf()
    }

    override fun getUserStory(user: User?, nextIndex: Int): List<Status> {
        if (user?.userId == null) return listOf()
        val requestPath = "/user/" + user.userId + "/story"
        val (request, response, result) = requestPath
            .httpGet(listOf("nextindex" to nextIndex, "pagesize" to Constants.STATUS_PAGE_SIZE))
            .responseObject<StatusListResponse>()

        return if (response.statusCode == 200) {
            val statuses = result.component1() as StatusListResponse
            statuses.body ?: listOf()
        }
        else listOf()
    }

    override fun getCurrentUser(): User {
         return this.getUserById("0") ?: User("0", "Joe Cool", "jcool")
    }

    override fun getUserFollowers(userId: String, nextIndex: Int): List<User> {
        val requestPath = "/user/" + userId + "/followers"
        val (request, response, result) = requestPath
            .httpGet(listOf("nextindex" to nextIndex, "pagesize" to Constants.USER_PAGE_SIZE))
            .responseObject<UserListResponse>()

        return if (response.statusCode == 200) {
            val userResponse = result.component1() as UserListResponse
            userResponse.body ?: listOf()
        }
        else listOf()
    }

    override fun getPersonsFollowedByUser(userId: String, nextIndex: Int): List<User> {
        val requestPath = "/user/" + userId + "/usersfollowed"
        val (request, response, result) = requestPath
            .httpGet(listOf("nextindex" to nextIndex, "pagesize" to Constants.USER_PAGE_SIZE))
            .responseObject<UserListResponse>()

        return if (response.statusCode == 200) {
            val userResponse = result.component1() as UserListResponse
            userResponse.body ?: listOf()
        }
        else listOf()
    }

    override fun getStatusById(statusId: String?): Status? {
        return null
    }

}