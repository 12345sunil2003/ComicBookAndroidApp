package com.read.comicbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.read.comicbook.db.schema.UserEntity
import com.read.comicbook.home.HomeActivity
import com.read.comicbook.user.LoginActivity
import com.read.comicbook.user.UserViewModel

class SplashActivity : AppCompatActivity() {
    private var pref : SharedPreferencesManager?=null
    var user: UserEntity? = null
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Utils.hideStatusBar(window)
        pref = SharedPreferencesManager(this)
        attachObserver()
        setUser()
    }

    private fun attachObserver() {
        viewModel.user.observe(this){
            user = it
            (applicationContext as MyApp).setUser(user)
            delayToStart()
        }
    }

    private fun setUser() {
        val userId = pref?.getUserData()
        if (userId !=null) {
            viewModel.getUserById(userId)
        }else {
            delayToStart()
        }
    }

    private fun delayToStart() {
        val screen = if (user == null) LoginActivity::class.java else HomeActivity::class.java
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, screen))
            finish()
        }, SPLASH_DELAY)
    }

    companion object {
        const val SPLASH_DELAY = 3000L //3sec
    }
}