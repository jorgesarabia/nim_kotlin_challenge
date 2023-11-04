package com.example.firstchallenge

import android.app.Application
import com.example.firstchallenge.util.PreferenceHelper

class FirstChallengeApplication: Application() {

    companion object {
        lateinit var pref: PreferenceHelper
    }

    override fun onCreate() {
        super.onCreate()
        pref = PreferenceHelper(applicationContext)
    }
}