package com.example.flutter.models

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.example.flutter.ui.main.status.StatusHelper
import java.util.*

class Status(val user: User,
             val messageBody: String,
             val attachedPhoto: Drawable? = null,
             val statusId: String = UUID.randomUUID().toString(),
             val timeStamp: Long = System.currentTimeMillis(),
             var hashtagList: List<String> = listOf()){

    init {
        hashtagList = StatusHelper.findAllHashtags(messageBody)
    }

}