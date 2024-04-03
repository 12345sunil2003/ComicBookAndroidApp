package com.read.comicbook

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    fun saveUserData(userId: Long) {
        sharedPreferences.edit().putLong("logged_in_userId", userId).apply()
    }

    fun getUserData(): Long? {
        val userDataJson = sharedPreferences.getLong("logged_in_userId", -1)
        return if (userDataJson != -1L) {
            userDataJson
        } else {
            null
        }
    }

    fun clearUserData() {
        sharedPreferences.edit().remove("logged_in_userId").apply()
    }
}