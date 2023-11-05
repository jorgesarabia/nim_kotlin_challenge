package com.example.firstchallenge.models.response

/*{
    "data": {
        "id": "9100",
        "type": "token",
        "attributes": {
            "access_token": "a4fz6QE09GD6b390cY5HROqoT_aYHvZj2Dpy44rp0Pk",
            "token_type": "Bearer",
            "expires_in": 7200,
            "refresh_token": "6trF7VFub0lT6DSBnBYUSXB5V3hEKohdQExZAX3tCdA",
            "created_at": 1699114215
        }
    }
}*/


data class LoginResponse(val data: Login)

data class Login(
    val id: String,
    val attributes: LoginAttributes,
)

data class LoginAttributes(
    val access_token: String,
    val refresh_token: String,
)
