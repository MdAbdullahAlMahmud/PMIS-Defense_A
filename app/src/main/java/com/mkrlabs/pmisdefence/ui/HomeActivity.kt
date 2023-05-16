package com.mkrlabs.pmisdefence.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.ActivityHomeBinding
import com.mkrlabs.pmisdefence.fragment.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var activityHomeBinding: ActivityHomeBinding
    lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
        navController = findNavController(R.id.newsNavHostFragment)

       /* appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_fragment,
                R.id.profile_fragment
            ),
            activityHomeBinding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)*/

        activityHomeBinding.navView.setupWithNavController(navController)



    }

    public fun openNavigationDrawer(){
        activityHomeBinding.drawerLayout.open()
    }


    /*override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/
}