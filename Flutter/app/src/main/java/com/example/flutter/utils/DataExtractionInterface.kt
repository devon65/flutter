package com.example.flutter.utils

import com.example.flutter.models.Status
import com.example.flutter.models.User

interface DataExtractionInterface {

    fun getStatusesByHashtag(tag: String, nextIndex: Int = 0): List<Status>

    fun getUserById(userId: String?): User?

    fun getUserByAlias(alias: String?): User?

    fun getUserFeed(user: User?, nextIndex: Int = 0): List<Status>

    fun getUserStory(user: User?, nextIndex: Int = 0): List<Status>

//    fun getUserIdByAlias(alias: String?): String?

    fun getCurrentUser(): User?

    fun getUserFollowers(userId: String, nextIndex: Int = 0): List<User>

    fun getPersonsFollowedByUser(userId: String, nextIndex: Int = 0): List<User>

    fun getStatusById(statusId: String?): Status?
}