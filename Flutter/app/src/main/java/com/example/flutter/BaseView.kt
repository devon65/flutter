package com.example.flutter

interface BaseView<T> {
    fun setPresenter(presenter : T)
}