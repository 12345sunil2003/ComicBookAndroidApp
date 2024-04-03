package com.read.comicbook

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.read.comicbook.db.ComicBookDatabase
import com.read.comicbook.db.schema.UserEntity

class MyApp: Application() {

    private var comicDb: ComicBookDatabase?=null
    private var user: UserEntity?=null
    private var pref: SharedPreferencesManager?=null
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initPref()
        initDb()
    }

    private fun initPref() {
        pref = SharedPreferencesManager(this)
    }

    private fun initDb() {
        comicDb = ComicBookDatabase.getInstance(this)
    }

    fun getDb(): ComicBookDatabase? {
        return comicDb
    }

    fun setUser(user: UserEntity?){
        this.user = user
    }

    fun getUser(): UserEntity? {
        return user
    }

    fun saveUserPref(userId: Long){
        pref?.saveUserData(userId)
    }

    fun clearUserPref(){
        pref?.clearUserData()
    }

}