package com.example.firstchallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.firstchallenge.FirstChallengeApplication.Companion.pref

class HomeActivity : AppCompatActivity() {

    lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        logoutButton = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener(View.OnClickListener {
            pref.removeTokens()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}