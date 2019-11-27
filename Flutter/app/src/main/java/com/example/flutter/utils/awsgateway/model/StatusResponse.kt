package com.example.flutter.utils.awsgateway.model

import com.example.flutter.models.Status

class StatusResponse (
    val statusCode: Int? = null,
    val message: String? = null,
    val body: Status? = null
)