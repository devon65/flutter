package com.example.flutter.Utils.awsgateway

import com.example.flutter.Utils.DataExtractionInterface
import com.example.flutter.models.Status
import com.example.flutter.models.User

class AwsDataRetrieval: DataExtractionInterface{
    override fun getStatusesByHashtag(tag: String): List<Status> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserById(userId: String?): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserFeed(user: User?): List<Status> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserStory(user: User?): List<Status> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserByAlias(alias: String?): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentUser(): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserFollowers(userId: String): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPersonsFollowedByUser(userId: String): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStatusById(statusId: String?): Status? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}