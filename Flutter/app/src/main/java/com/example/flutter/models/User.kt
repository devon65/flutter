package com.example.flutter.models

import com.example.flutter.R
import com.example.flutter.utils.awsgateway.model.PersonListResponseItem
import com.example.flutter.utils.awsgateway.model.StatusListResponseItem
import com.example.flutter.utils.awsgateway.model.StatusListResponseItemUser
import com.example.flutter.utils.awsgateway.model.UserResponse

data class User(val userId: String,
                val name: String,
                val alias: String,
                val statusList: ArrayList<String> = ArrayList(),
                val profilePicUrl: String = "https://devonbyubucket.s3-us-west-2.amazonaws.com/flutter-bird.png",
                val followers: List<String> = ArrayList(),
                val usersFollowed: List<String> = ArrayList()){

    constructor(responseItem: StatusListResponseItemUser) : this(
        userId = responseItem.userId,
        name = responseItem.name,
        alias = responseItem.alias,
        profilePicUrl = responseItem.profilePic
    )

    constructor(responseItem: UserResponse) : this(
        userId = responseItem.userId,
        name = responseItem.name,
        alias = responseItem.alias,
        profilePicUrl = responseItem.profilePic
    )

    constructor(responseItem: PersonListResponseItem) : this(
        userId = responseItem.userId,
        name = responseItem.name,
        alias = responseItem.alias,
        profilePicUrl = responseItem.profilePic
    )
}