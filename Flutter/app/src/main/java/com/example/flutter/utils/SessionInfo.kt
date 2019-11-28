package com.example.flutter.utils

import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.utils.awsgateway.AwsAuthentication
import com.example.flutter.utils.awsgateway.AwsDataRetrieval
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object SessionInfo {

    private val dataExtractor: DataExtractionInterface = AwsDataRetrieval
    private val authenticationHelper: UserAuthenticationInterface = AwsAuthentication
    private var accessToken: String? = null
    var currentUser: User = User("0", "Network Error", "Network Error")

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

    fun clearCache(){
        currentUser = User("0", "Network Error", "Network Error")
        accessToken = null
        statusByIdCache.clear()
        userByIdCache.clear()
        userByAliasCache.clear()
    }

    fun userSignUp(name: String, alias: String, password: String, profilePicEncoding: String,
                   onSuccess: () -> Unit, onFailure: () -> Unit){

        GlobalScope.launch(IO) {
            val createUserSuccess: (user: User) -> Unit = {
                currentUser = it
                onSuccess()
            }
            val userSignInSuccess: (token: String?, alias: String?) -> Unit =
                { token: String?, userAlias: String? ->
                    accessToken = token
                    if (userAlias == null) { onFailure() }
                    else {
                        SendData.createUser(name, userAlias, profilePicEncoding, createUserSuccess, onFailure)
                    }
                }
            val signUpSuccess: () -> Unit = {
                authenticationHelper.signIn(alias, password, userSignInSuccess, onFailure)
            }
            authenticationHelper.signUp(name, alias, password, profilePicEncoding, signUpSuccess, onFailure)
        }
    }

    fun userSignIn(username: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        GlobalScope.launch(IO) {
            val getCurrentUserSuccess: (user: User) -> Unit = {
                currentUser = it
                onSuccess()
            }
            val userSignInSuccess: (token: String?, alias: String?) -> Unit =
                { token: String?, alias: String? ->
                    accessToken = token
                    if (alias == null) {
                        onFailure()
                    }
                    getUserByAlias(alias, getCurrentUserSuccess, onFailure)
                }
            authenticationHelper.signIn(username, password, userSignInSuccess, onFailure)
        }
    }

    fun ninjaSignIn(onSuccess: () -> Unit, onFailure: () -> Unit) {
        GlobalScope.launch(IO) {
            val getCurrentUserSuccess: (user: User) -> Unit = {
                currentUser = it
                onSuccess()
            }
            val userSignInSuccess: (token: String?, alias: String?) -> Unit =
                { token: String?, alias: String? ->
                    accessToken = token
                    if (alias == null) {
                        onFailure()
                    }
                    getUserByAlias(alias, getCurrentUserSuccess, onFailure)
                }
            authenticationHelper.ninjaSignIn(userSignInSuccess, onFailure)
        }
    }

    fun userLogout(onSuccess: () -> Unit, onFailure: () -> Unit){
        authenticationHelper.logout(onSuccess, onFailure)
    }

    fun getStatusesByHashtag(tag: String, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit, status: Status? = null){ //done
        GlobalScope.launch(Main) {
            val statuses = withContext(IO) {
                dataExtractor.getStatusesByHashtag(tag, status)
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

    fun getUserFeed(user: User?, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit, status: Status? = null){ //done
        GlobalScope.launch(Main) {
            val statuses = withContext(IO) {
                dataExtractor.getUserFeed(user, status)
            }
            if (statuses.isNullOrEmpty()) onFailure()
            else{
                onSuccess(statuses)
                addToStatusList(statuses)
            }
        }
    }

    fun getUserStory(user: User?, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit, status: Status? = null){ //done
        GlobalScope.launch(Main) {
            val statuses = withContext(IO) {
                dataExtractor.getUserStory(user, status)
            }
            if (statuses.isNullOrEmpty()) onFailure()
            else {
                onSuccess(statuses)
                addToStatusList(statuses)
            }
        }
    }

    fun checkIsFollowing(userId: String, onSuccess: (isFollowing: Boolean) -> Unit){
        val potentialFollowerId = currentUser.userId
        if (userId == potentialFollowerId || userId.isEmpty()) { onSuccess(false) }

        GlobalScope.launch(Main) {
            val isFollowing = withContext(IO) {
                dataExtractor.getIsFollowing(userId, potentialFollowerId)
            }

            onSuccess(isFollowing)
        }
    }

    fun getUserFollowers(userId: String, onSuccess: (List<User>) -> Unit, onFailure: () -> Unit, lastFollower: User? = null){ //done
        GlobalScope.launch(Main) {
            val users = withContext(IO) {
                dataExtractor.getUserFollowers(userId, lastFollower)
            }
            if (users.isNullOrEmpty()) onFailure()
            else{
                onSuccess(users)
                addToUserList(users)
            }
        }
    }

    fun getPersonsFollowedByUser(userId: String, onSuccess: (List<User>) -> Unit, onFailure: () -> Unit, lastFollower: User? = null){ //done
        GlobalScope.launch(Main) {
            val users = withContext(IO) {
                dataExtractor.getPersonsFollowedByUser(userId, lastFollower)
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
            val user = withContext(IO) {
                dataExtractor.getUserByAlias(alias)
            }
            if (user == null) onFailure()
            else onSuccess(user)
        }
    }
}