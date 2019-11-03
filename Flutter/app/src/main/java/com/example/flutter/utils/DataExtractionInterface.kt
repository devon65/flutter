package com.example.flutter.utils

import com.example.flutter.models.Status
import com.example.flutter.models.User

interface DataExtractionInterface {

    fun getStatusesByHashtag(tag: String, postExecute: (List<Status>) -> Unit)

    fun getUserById(userId: String?, postExecute: (User?) -> Unit)

    fun getUserByAlias(alias: String?, postExecute: (User?) -> Unit)

    fun getUserFeed(user: User?, postExecute: (List<Status>) -> Unit)

    fun getUserStory(user: User?, postExecute: (List<Status>) -> Unit)

//    fun getUserIdByAlias(alias: String?): String?

    fun getCurrentUser(postExecute: (User?) -> Unit)

    fun getUserFollowers(userId: String, postExecute: (List<User>) -> Unit)

    fun getPersonsFollowedByUser(userId: String, postExecute: (List<User>) -> Unit)

    fun getStatusById(statusId: String?, postExecute: (Status?) -> Unit)
}