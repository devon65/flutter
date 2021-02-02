package com.example.flutter.models

import com.example.flutter.ui.main.status.StatusHelper
import com.google.gson.annotations.SerializedName
import java.util.*

data class Status(@SerializedName("user") val user: User,
                  @SerializedName("messageBody") val messageBody: String,
//             val attachedPhoto: Drawable? = null,
                  @SerializedName("attachmentUrl") val attachmentUrl: String? = null,
                  @SerializedName("statusId") val statusId: String = UUID.randomUUID().toString(),
                  @SerializedName("timeStamp") val timeStamp: Long = System.currentTimeMillis(),
                  var hashtagList: List<String> = listOf()){

    init {
        hashtagList = StatusHelper.findAllHashtags(messageBody)
    }

//    constructor(responseItem: StatusListResponseBodyItem) : this(
//        user = User(responseItem.user),
//        messageBody = responseItem.messageBody,
//        attachmentUrl = responseItem.attachmentUrl,
//        statusId = responseItem.statusId,
//        timeStamp = responseItem.timeStamp.toLong()
//    )

}