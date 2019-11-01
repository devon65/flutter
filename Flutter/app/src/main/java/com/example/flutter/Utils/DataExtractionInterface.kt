package com.example.flutter.Utils

import com.example.flutter.models.Status
import com.example.flutter.models.User

interface DataExtractionInterface {
    fun getStatusesByHashtag(tag: String): List<Status>

    fun getUserById(userId: String?): User?

    fun getUserFeed(user: User?): List<Status>

    fun getUserStory(user: User?): List<Status>

    fun getUserIdByAlias(alias: String?): String?

    fun getCurrentUser(): User

    fun getUserFollowers(userId: String): List<User>

    fun getPersonsFollowedByUser(userId: String): List<User>

    fun getStatusById(statusId: String?): Status?
}