package com.example.firstchallenge.io

import com.example.firstchallenge.models.request.LoginRequest
import com.example.firstchallenge.models.response.LoginResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(value = "oauth/token")
    fun login(@Body loginRequest: LoginRequest):Call<LoginResponse>

    companion object Factory {
        private const val BASE_URL_V1 = "https://survey-api.nimblehq.co/api/v1/"

        fun create(): ApiService {

            val retrofit = Retrofit.Builder().baseUrl(BASE_URL_V1)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}