package com.example.flutter.Utils

import com.example.flutter.models.Status
import com.example.flutter.models.User

object SessionInfo {

    private var mCurrentUser: User? = null
    val currentUser: User
        get() {
            return mCurrentUser ?: getSuperUser()
        }

    private fun getSuperUser(): User{
         val superUser = DummyData.superUser
        mCurrentUser = superUser
        return superUser
    }

    fun getStatusesByHashtag(tag: String): List<Status>{
        return DummyData.getStatusesByHashtag(tag)
    }

    fun getUserById(userId: String): User?{
        return DummyData.idToUserMap[userId]
    }

    fun getUserFeed(user: User?): List<Status>{
        return DummyData.getUserFeed(user)
    }

    fun getUserStory(user: User?): List<Status>{
        return DummyData.getUserStory(user)
    }
}