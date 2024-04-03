package com.read.comicbook.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.read.comicbook.R
import com.read.comicbook.Utils
import com.read.comicbook.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        navController = findNavController(R.id.homeNavHostFragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment, R.id.categoryFragment, R.id.newPostFragment,R.id.favouriteFragment, R.id.profileFragment
        ))

        binding.bottomNavigationView.setupWithNavController(navController)

    }
}