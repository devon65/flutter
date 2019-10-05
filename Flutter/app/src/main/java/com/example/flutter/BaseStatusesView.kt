package com.example.flutter

import com.example.flutter.models.Status

interface BaseStatusesView<T>: BaseView<T>{
    fun launchViewHashtagActivity(hashtag: String)
    fun launchViewProfileActivity(userId: String)
    fun launchViewStatusActivity(status: Status)
}