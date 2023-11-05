package com.example.firstchallenge.models.response

/*
{
    "data": {
        "id": "254",
        "type": "user",
        "attributes": {
            "email": "jorge@sarabiajor.ge",
            "name": "Jorge Sarabia",
            "avatar_url": "https://secure.gravatar.com/avatar/4ff13d146bd61608bfd62e21a0e3fa88"
        }
    }
}
*/

data class UserResponse(
    val id: String,
    val attributes: UserAttributes,
)

data class UserAttributes(
    val email: String,
    val name: String,
    val avatar_url: String,
)