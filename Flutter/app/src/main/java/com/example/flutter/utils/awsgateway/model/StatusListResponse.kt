package com.example.flutter.utils.awsgateway.model
import com.example.flutter.models.Status

data class StatusListResponse (
    val statusCode: Int? = null,
    val message: String? = null,
    val body: List<Status>? = null
)