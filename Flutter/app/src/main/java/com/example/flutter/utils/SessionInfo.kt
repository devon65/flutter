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

    var currentUser: User = User("0", "Network Error", "Network Error")

    fun userSignUp(name: String, alias: String, password: String, profilePicEncoding: String,
                   onSuccess: () -> Unit, onFailure: () -> Unit){

        GlobalScope.launch(IO) {
            val createUserSuccess: (user: User) -> Unit = {
                currentUser = it
                userSignIn(alias, password, onSuccess, onFailure)
            }
            val signUpSuccess: () -> Unit = {
                SendData.createUser(name, alias, profilePicEncoding, createUserSuccess, onFailure)
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

    fun getStatusesByHashtag(tag: String, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit){ //done
        GlobalScope.launch(Main) {
            val statuses = withContext(IO) {
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
            val user = withContext(IO) {
                dataExtractor.getUserByAlias(alias)
            }
            if (user == null) onFailure()
            else onSuccess(user)
        }
    }
}