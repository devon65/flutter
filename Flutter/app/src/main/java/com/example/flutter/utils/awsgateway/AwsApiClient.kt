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

    override fun getStatusesByHashtag(tag: String, postExecute: (List<Status>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = awsClient.hashtagHashtagGet(tag)
            postExecute(getStatusListFromResponse(response))
        }
    }

    override fun getUserById(userId: String?, postExecute: (User?) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = awsClient.userCurrentuserGet(userId)
            postExecute(User(response))

        }
    }

    override fun getUserByAlias(alias: String?, postExecute: (User?) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = awsClient.userAliasAliasGet(alias)
            postExecute(User(response))
        }
    }

    override fun getUserFeed(user: User?, postExecute: (List<Status>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = awsClient.userCurrentuserFeedGet(user?.userId)
            postExecute(getStatusListFromResponse(response))
        }
    }

    override fun getUserStory(user: User?, postExecute: (List<Status>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = awsClient.userCurrentuserStoryGet(user?.userId)
            postExecute( getStatusListFromResponse(response))
        }
    }

    override fun getCurrentUser(postExecute: (User?) -> Unit) {
         this.getUserById("0", postExecute)
    }

    override fun getUserFollowers(userId: String, postExecute: (List<User>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = awsClient.userCurrentuserFollowersGet(userId)
            postExecute(getUserListFromResponse(response))
        }
    }

    override fun getPersonsFollowedByUser(userId: String, postExecute: (List<User>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = awsClient.userCurrentuserUsersfollowedGet(userId)
            postExecute(getUserListFromResponse(response))
        }
    }

    override fun getStatusById(statusId: String?, postExecute: (Status?) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            postExecute(null)
        }
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