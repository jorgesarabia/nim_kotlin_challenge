package com.example.firstchallenge.models.response

/*{
    "errors": [
        {
            "detail": "Your email or password is incorrect. Please try again.",
            "code": "invalid_email_or_password"
        }
    ]
}*/

data class ErrorsResponse(val errors: List<SingleErrorResponse>?)

data class SingleErrorResponse(
    val detail: String?,
    val code: String?
)
