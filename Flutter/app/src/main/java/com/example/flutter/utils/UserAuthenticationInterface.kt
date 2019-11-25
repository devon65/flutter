package com.example.flutter.utils

interface UserAuthenticationInterface {
    fun signUp(name: String, alias: String, password: String, profilePicEncoding: String,
               onSuccess: () -> Unit, onFailure: () -> Unit)

    fun signIn(username: String, password: String,
               onSuccess: (accessToken: String?, userAlias: String?) -> Unit, onFailure: () -> Unit)

    fun ninjaSignIn(onSuccess: (accessToken: String?, userAlias: String?) -> Unit, onFailure: () -> Unit)

    fun logout()
}