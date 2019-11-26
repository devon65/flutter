package com.example.flutter.models

import com.example.flutter.R
import com.example.flutter.utils.awsgateway.model.*
import com.google.gson.annotations.SerializedName

data class User(@SerializedName("userId") val userId: String,
                @SerializedName("name") val name: String,
                @SerializedName("alias") val alias: String,
                @SerializedName("profilePicUrl") val profilePicUrl: String = "https://devonbyubucket.s3-us-west-2.amazonaws.com/flutter-bird.png",
                val statusList: ArrayList<String> = ArrayList(),
                val followers: List<String> = ArrayList(),
                val usersFollowed: List<String> = ArrayList()){

//    constructor(responseItem: StatusListResponseBodyItemUser) : this(
//        userId = responseItem.userId,
//        name = responseItem.name,
//        alias = responseItem.alias,
//        profilePicUrl = responseItem.profilePic
//    )
//
//    constructor(responseItem: UserResponse) : this(
//        userId = responseItem.userId,
//        name = responseItem.name,
//        alias = responseItem.alias,
//        profilePicUrl = responseItem.profilePic
//    )
//
//    constructor(responseItem: PersonListResponseItem) : this(
//        userId = responseItem.userId,
//        name = responseItem.name,
//        alias = responseItem.alias,
//        profilePicUrl = responseItem.profilePic
//    )
}