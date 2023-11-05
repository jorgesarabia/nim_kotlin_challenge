package com.example.firstchallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firstchallenge.FirstChallengeApplication.Companion.pref
import com.example.firstchallenge.io.ApiService
import com.example.firstchallenge.models.request.LoginRequest
import com.example.firstchallenge.models.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var password: EditText
    lateinit var loginButton: Button

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(pref.getToken().isNotEmpty()){
            Log.d("Token_guardado", pref.getToken())
            goToHome()
        }

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener(View.OnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            if (emailText.isEmpty() || passwordText.isEmpty()){
                Toast.makeText(this, "need email and password", Toast.LENGTH_SHORT).show()
            } else {
                performLogin(emailText, passwordText)
            }
        })


    }

    private fun performLogin(email: String, password: String) {

        val request = LoginRequest(
            grant_type = "password",
            email = email,
            password = password,
            client_id = "ofzl-2h5ympKa0WqqTzqlVJUiRsxmXQmt5tkgrlWnOE",
            client_secret = "lMQb900L-mTeU-FVTCwyhjsfBwRCxwwbCitPob96cuU"
        )

        val call = apiService.login(loginRequest = request)

        call.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful) {
                    val loginResponse = response.body()

                    if(loginResponse == null){
                        Toast.makeText(applicationContext,"Error", Toast.LENGTH_SHORT).show()
                        return
                    }

                    val token = loginResponse.data.attributes.access_token
                    val refreshToken = loginResponse.data.attributes.refresh_token

                    pref.saveTokens(token, refreshToken)
                    goToHome()
                } else {
                    Toast.makeText(applicationContext,"email/password combination does not match", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext,"Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun goToHome(){
        val i = Intent(this, HomeActivity::class.java)
        startActivity(i)
        finish()
    }
}