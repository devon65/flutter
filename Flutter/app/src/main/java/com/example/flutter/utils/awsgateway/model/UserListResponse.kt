package com.example.flutter.utils.awsgateway.model

import com.example.flutter.models.User

class UserListResponse (
    val statusCode: Int? = null,
    val message: String? = null,
    val body: List<User>? = null
)