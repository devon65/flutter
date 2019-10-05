package com.example.flutter

import com.example.flutter.models.Hashtag
import com.example.flutter.models.Status

interface BaseView<T> {
    fun setPresenter(presenter : T)
}