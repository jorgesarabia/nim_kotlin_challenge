package com.example.firstchallenge.models.request

data class LoginRequest(
    val grant_type: String,
    val email: String,
    val password: String,
    val client_id: String,
    val client_secret: String
)
