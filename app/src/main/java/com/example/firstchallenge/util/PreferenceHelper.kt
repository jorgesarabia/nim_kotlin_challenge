package com.example.firstchallenge.util

import android.content.Context

class PreferenceHelper(private val context:Context) {

    private val cDbName = "appShared"
    private val cToken = "token"
    private val cRefreshToken = "token"

    private val storage = context.getSharedPreferences(cDbName, 0)

    fun saveTokens(token: String, refreshToken: String ) {
        storage.edit().putString(cToken, token).apply()
        storage.edit().putString(cRefreshToken, refreshToken).apply()
    }

    fun removeTokens() {
        val editor = storage.edit()
        editor.remove(cToken)
        editor.remove(cRefreshToken)
        editor.apply()
    }

    fun getToken():String {
        return  storage.getString(cToken, "")!!
    }

    fun getRefreshToken():String {
        return  storage.getString(cRefreshToken, "")!!
    }

}