package com.example.flutter.models

import com.example.flutter.ui.main.status.StatusHelper
import java.util.*

class Status(val user: User,
             val messageBody: String,
             val statusId: String = UUID.randomUUID().toString(),
             val timeStamp: Long = System.currentTimeMillis(),
             var hashtagList: List<String> = listOf()){

    init {
        hashtagList = StatusHelper.findAllHashtags(messageBody)
    }

}