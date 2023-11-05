package com.example.firstchallenge.util

import android.content.Context

class PreferenceHelper(private val context:Context) {

    private val cDbName = "appShared"
    private val cToken = "token"
    private val cRefreshToken = "refreshToken"

    private val storage = context.getSharedPreferences(cDbName, 0)

    fun saveTokens(token: String, refreshToken: String ) {
        val edit = storage.edit()
        edit.putString(cToken, token)
        edit.putString(cRefreshToken, refreshToken)
        edit.apply()
//        storage.edit().putString(cToken, "iFJAQRXzRYiCb1ycfPQpzyPGR1sVoqDoPLngYDfR1XA").apply()
//        storage.edit().putString(cRefreshToken, "QMrCnT3_Ra2TiucXK0wez4L4eXkfvjbPfufgW4d2iE4 TAG").apply()

        /*
        Older:
        {
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
        }

        OldPair:
        - cToken, "iFJAQRXzRYiCb1ycfPQpzyPGR1sVoqDoPLngYDfR1XA"
        - cRefreshToken, "QMrCnT3_Ra2TiucXK0wez4L4eXkfvjbPfufgW4d2iE4"

        05/11/23 => 16:17
        {
            "data": {
                "id": "9613",
                "type": "token",
                "attributes": {
                    "access_token": "iNXbLZLFygmlNwN9JWlRipH4poPUUnCxWpcvXsUbCrc",
                    "token_type": "Bearer",
                    "expires_in": 7200,
                    "refresh_token": "qBox-XCroipGxzUzuiQhiedC5aIkwbPJmfW3U5VIagg",
                    "created_at": 1699211721
                }
            }
         }

         05/11/23 => 16:21
         {
            "data": {
                "id": "9615",
                "type": "token",
                "attributes": {
                    "access_token": "BuCucJL4YgANon6qmQVgN6Q5t7jo3rWBM-vOaw1celY",
                    "token_type": "Bearer",
                    "expires_in": 7200,
                    "refresh_token": "XP0h373HqWMXWyH_rV_a-bxXv8rMfTHEhYIqDhXCoXQ",
                    "created_at": 1699212063
                }
            }
        }
        */
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