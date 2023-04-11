package com.mkrlabs.pmisdefence.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {


    lateinit var activityHomeBinding: ActivityHomeBinding
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
        navController = findNavController(R.id.newsNavHostFragment)


    }
}