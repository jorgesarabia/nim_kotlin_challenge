package com.example.firstchallenge.io

import android.provider.Settings.Global
import android.util.Log
import android.widget.Toast
import com.example.firstchallenge.FirstChallengeApplication.Companion.pref
import com.example.firstchallenge.models.request.LoginRequest
import com.example.firstchallenge.models.request.RefreshTokenRequest
import com.example.firstchallenge.models.response.ErrorsResponse
import com.example.firstchallenge.models.response.LoginResponse
import com.example.firstchallenge.models.response.UserResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST(value = "oauth/token")
    fun login(@Body loginRequest: LoginRequest):Call<LoginResponse>

    @POST(value = "oauth/token")
    fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest):Call<LoginResponse>

    @GET(value = "me")
    fun getCurrentUser():Call<UserResponse>

    companion object Factory {
        private const val BASE_URL_V1 = "https://survey-api.nimblehq.co/api/v1/"

        fun create(): ApiService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_V1)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = pref.getToken()

        if (accessToken.isNotEmpty()) {
            val newRequest = originalRequest.newBuilder()
            newRequest.addHeader("Authorization", "Bearer $accessToken")

            val response = chain.proceed(newRequest.build())

            val responseBody = response.body()
            val error = response.message()
            val some = response.networkResponse()
            val code = response.code()

            if (response.code() == 401) {
                val newAccessToken: String?

                runBlocking {
                    val getNewAccessToken = async { refreshAccessToken() }
                    newAccessToken = getNewAccessToken.await()
                }

                if (newAccessToken != null) {
                    val renewedRequest = originalRequest.newBuilder()
                    renewedRequest.addHeader("Authorization", "Bearer $newAccessToken")
                    response.close()
                    return chain.proceed(renewedRequest.build())
                }
            }

            return response
        }

        return chain.proceed(originalRequest)
    }

    private suspend fun refreshAccessToken(): String?  {
        try {
            val refreshTokenRequest = RefreshTokenRequest(refresh_token = pref.getRefreshToken())
            val apiService = ApiService.create()


            pref.removeTokens()

            runBlocking {
                val response = apiService.refreshToken(refreshTokenRequest = refreshTokenRequest).awaitResponse()
                if(response.isSuccessful) {
                    val loginResponse = response.body()

                    if (loginResponse != null) {
                        val token = loginResponse.data.attributes.access_token
                        val refreshToken = loginResponse.data.attributes.refresh_token

                        pref.saveTokens(token, refreshToken)
                    }
                } else {
                    val errorBodyString = response.errorBody()?.string()
                    if(!errorBodyString.isNullOrEmpty()){
                        Log.e("RequestError", response.errorBody()?.string() ?: "No error body")
                        try {
                            val gson = GsonBuilder().create()
                            val apiError = gson.fromJson(errorBodyString, ErrorsResponse::class.java)
                            val firstError = apiError.errors?.first()
                            val errorCode = firstError?.code
                            Log.e("ErrorCode", errorCode?:"No code")
                        } catch (e: JsonParseException) {
                            Log.e("JsonParseException", e.toString())
                        }
                    }
                }
            }

//            call.enqueue(object: Callback<LoginResponse>{
//                override fun onResponse(call: Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
//
//                }
//
//                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                    // Handle error
//                }
//            })

        } catch (e: Exception) {
            Log.e("Exception", e.toString())
        }

        val updatedToken = pref.getToken()

        if (updatedToken.isNotEmpty()){
            return updatedToken
        }


        return null
    }
}
