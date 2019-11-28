package com.example.flutter.utils

import com.example.flutter.models.Status
import com.example.flutter.models.User

interface DataExtractionInterface {

    fun getStatusesByHashtag(tag: String, lastStatus: Status? = null): List<Status>

    fun getUserById(userId: String?): User?

    fun getUserByAlias(alias: String?): User?

    fun getUserFeed(user: User?, lastStatus: Status? = null): List<Status>

    fun getUserStory(user: User?, lastStatus: Status? = null): List<Status>

//    fun getUserIdByAlias(alias: String?): String?
    @Deprecated("No longer used.")
    fun getCurrentUser(): User?

    fun getIsFollowing(userId: String, potentialFollowerId: String): Boolean

    fun getUserFollowers(userId: String, lastFollower: User? = null): List<User>

    fun getPersonsFollowedByUser(userId: String, lastUserFollowed: User? = null): List<User>

    @Deprecated("Status caching prevents need for this function. Could be useful in future implementations.")
    fun getStatusById(statusId: String?): Status?
}