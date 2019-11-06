package com.example.flutter.utils.awsgateway.model

import com.example.flutter.models.User

class UserResponse (
    val statusCode: Int? = null,
    val message: String? = null,
    val body: User? = null
)