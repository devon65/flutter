package com.example.flutter.models

data class User(val userId: String,
                val name: String,
                val alias: String,
                val statusList: ArrayList<String> = ArrayList(),
                val attachmentsList: ArrayList<Attachments> = ArrayList(),
                val followers: List<String> = ArrayList(),
                val usersFollowed: List<String> = ArrayList()
)