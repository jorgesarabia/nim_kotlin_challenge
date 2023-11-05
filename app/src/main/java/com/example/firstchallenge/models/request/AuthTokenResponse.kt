package com.example.firstchallenge.models.request


object ClientTokens {
    const val clientId = "ofzl-2h5ympKa0WqqTzqlVJUiRsxmXQmt5tkgrlWnOE"
    const val clientSecret = "lMQb900L-mTeU-FVTCwyhjsfBwRCxwwbCitPob96cuU"
}

data class LoginRequest(
    val email: String,
    val password: String
){
    val grant_type = "password"
    val client_id = ClientTokens.clientId
    val client_secret = ClientTokens.clientSecret
}

data class RefreshTokenRequest(val refresh_token: String) {
    val grant_type = "refresh_token"
    val client_id = ClientTokens.clientId
    val client_secret = ClientTokens.clientSecret
}
