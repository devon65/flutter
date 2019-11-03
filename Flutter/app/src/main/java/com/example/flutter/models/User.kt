package com.example.flutter.models

import com.example.flutter.R

data class User(val userId: String,
                val name: String,
                val alias: String,
                val statusList: ArrayList<String> = ArrayList(),
                val profilePicUrl: String = "https://devonbyubucket.s3-us-west-2.amazonaws.com/flutter-bird.png",
                val followers: List<String> = ArrayList(),
                val usersFollowed: List<String> = ArrayList()
)