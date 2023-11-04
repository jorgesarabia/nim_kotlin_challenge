package com.example.firstchallenge.util

import android.content.Context

class PreferenceHelper(private val context:Context) {

    private val cDbNAME = "appShared"
    private val cTOKEN = "token"

    private val storage = context.getSharedPreferences(cDbNAME, 0)

    fun saveToken(token: String) {
        storage.edit().putString(cTOKEN, token).apply()
    }

    fun getToken():String {
        return  storage.getString(cTOKEN, "")!!
    }

}