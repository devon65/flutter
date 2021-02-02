package com.example.flutter.utils

import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.awsgateway.AwsDataRetrieval
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object SessionInfo {

    private val dataExtractor: DataExtractionInterface = AwsDataRetrieval

//    private var mCurrentUser: User? = null
//    val currentUser: User
//        get() {
//            return mCurrentUser ?: extractCurrentUser()
//        }
//
//    private fun extractCurrentUser(): User{
//        val theCurrentUser = dataExtractor.getCurrentUser()
//        mCurrentUser = theCurrentUser
//        return theCurrentUser
//    }

    private val statusByIdCache = HashMap<String, Status>()
    private val userByIdCache = HashMap<String, User>()
    private val userByAliasCache = HashMap<String, User>()

    private fun addToStatusList(statuses: List<Status>) {
        for (status in statuses){
            statusByIdCache[status.statusId] = status
        }
    }

    private fun addToUserList(users: List<User>) {
        for (user in users){
            userByIdCache[user.userId] = user
            userByAliasCache[user.alias] = user
        }
    }

    var currentUser: User = User("0", "Lame O", "noWorky")

    fun getStatusesByHashtag(tag: String, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit){ //done
        GlobalScope.launch(Main) {
            val statuses = withContext(Dispatchers.IO) {
                dataExtractor.getStatusesByHashtag(tag)
            }
            if (statuses.isNullOrEmpty()) onFailure()
            else {
                onSuccess(statuses)
                addToStatusList(statuses)
            }
        }
    }

    fun getStatusById(statusId: String?): Status?{ //done
        return statusByIdCache[statusId]
    }

    fun getUserById(userId: String?, onSuccess: (User) -> Unit, onFailure: () -> Unit){ //done
        GlobalScope.launch(Main) {
            val user = withContext(Dispatchers.IO) {
                dataExtractor.getUserById(userId)
            }
            if (user == null) onFailure()
            else onSuccess(user)
        }
    }

    fun getUserFeed(user: User?, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit){ //done
        GlobalScope.launch(Main) {
            val statuses = withContext(Dispatchers.IO) {
                dataExtractor.getUserFeed(user)
            }
            if (statuses.isNullOrEmpty()) onFailure()
            else{
                onSuccess(statuses)
                addToStatusList(statuses)
            }
        }
    }

    fun getUserStory(user: User?, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit){ //done
        GlobalScope.launch(Main) {
            val statuses = withContext(Dispatchers.IO) {
                dataExtractor.getUserStory(user)
            }
            if (statuses.isNullOrEmpty()) onFailure()
            else {
                onSuccess(statuses)
                addToStatusList(statuses)
            }
        }
    }

    fun getUserFollowers(userId: String, onSuccess: (List<User>) -> Unit, onFailure: () -> Unit){ //done
        GlobalScope.launch(Main) {
            val users = withContext(Dispatchers.IO) {
                dataExtractor.getUserFollowers(userId)
            }
            if (users.isNullOrEmpty()) onFailure()
            else{
                onSuccess(users)
                addToUserList(users)
            }
        }
    }

    fun getPersonsFollowedByUser(userId: String, onSuccess: (List<User>) -> Unit, onFailure: () -> Unit){ //done
        GlobalScope.launch(Main) {
            val users = withContext(Dispatchers.IO) {
                dataExtractor.getPersonsFollowedByUser(userId)
            }
            if (users.isNullOrEmpty()) onFailure()
            else {
                onSuccess(users)
                addToUserList(users)
            }
        }
    }

    fun getUserByAlias(alias: String?, onSuccess: (User) -> Unit, onFailure: () -> Unit){
        GlobalScope.launch(Main) {
            val user = withContext(Dispatchers.IO) {
                dataExtractor.getUserByAlias(alias)
            }
            if (user == null) onFailure()
            else onSuccess(user)
        }
    }
}