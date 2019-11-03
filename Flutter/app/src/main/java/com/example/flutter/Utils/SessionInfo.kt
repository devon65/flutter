package com.example.flutter.Utils

import com.example.flutter.models.Status
import com.example.flutter.models.User

object SessionInfo {

    private val dataExtractor: DataExtractionInterface = DummyData

    private var mCurrentUser: User? = null
    val currentUser: User
        get() {
            return mCurrentUser ?: extractCurrentUser()
        }

    private fun extractCurrentUser(): User{
         val superUser = dataExtractor.getCurrentUser()
        mCurrentUser = superUser
        return superUser
    }

    fun getStatusesByHashtag(tag: String): List<Status>{
        return dataExtractor.getStatusesByHashtag(tag)
    }

    fun getStatusById(statusId: String?): Status?{
        return dataExtractor.getStatusById(statusId)
    }

    fun getUserById(userId: String?): User?{
        return dataExtractor.getUserById(userId)
    }

    fun getUserFeed(user: User?): List<Status>{
        return dataExtractor.getUserFeed(user)
    }

    fun getUserStory(user: User?): List<Status>{
        return dataExtractor.getUserStory(user)
    }

    fun getUserFollowers(userId: String): List<User>{
        return dataExtractor.getUserFollowers(userId)
    }

    fun getPersonsFollowedByUser(userId: String): List<User>{
        return dataExtractor.getPersonsFollowedByUser(userId)
    }

    fun getUserByAlias(alias: String?): User?{
        return dataExtractor.getUserByAlias(alias)
    }
}