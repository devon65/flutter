package com.example.flutter.utils.awsgateway

import androidx.annotation.RestrictTo
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.DataExtractionInterface
import com.example.flutter.utils.awsgateway.model.PersonListResponse
import com.example.flutter.utils.awsgateway.model.StatusListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object AwsApiClient: DataExtractionInterface {
    val awsClient: FlutterAppEndpointsClient =
        ApiClientFactory().build(FlutterAppEndpointsClient::class.java)

    override fun getStatusesByHashtag(tag: String): List<Status> {
        val response = awsClient.hashtagHashtagGet(tag)
        return getStatusListFromResponse(response)
    }

    override fun getUserById(userId: String?): User? {
        val response = awsClient.userCurrentuserGet(userId)
        return User(response)
    }

    override fun getUserByAlias(alias: String?): User? {
        val response = awsClient.userAliasAliasGet(alias)
        return User(response)
    }

    override fun getUserFeed(user: User?): List<Status> {
        val response = awsClient.userCurrentuserFeedGet(user?.userId)
        return getStatusListFromResponse(response)
    }

    override fun getUserStory(user: User?): List<Status> {
        val response = awsClient.userCurrentuserStoryGet(user?.userId)
        return getStatusListFromResponse(response)
    }

    override fun getCurrentUser(): User {
         return this.getUserById("0") ?: User("0", "Joe Cool", "jcool")
    }

    override fun getUserFollowers(userId: String): List<User> {
        val response = awsClient.userCurrentuserFollowersGet(userId)
        return getUserListFromResponse(response)
    }

    override fun getPersonsFollowedByUser(userId: String): List<User> {
        val response = awsClient.userCurrentuserUsersfollowedGet(userId)
        return getUserListFromResponse(response)
    }

    override fun getStatusById(statusId: String?): Status? {
        return null
    }


    private fun getStatusListFromResponse(response: StatusListResponse): List<Status>{
        val resultList = ArrayList<Status>()
        for (stat in response){
            resultList.add(Status(stat))
        }
        return resultList
    }

    private fun getUserListFromResponse(response: PersonListResponse): List<User>{
        val resultList = ArrayList<User>()
        for (user in response){
            resultList.add(User(user))
        }
        return resultList
    }

}