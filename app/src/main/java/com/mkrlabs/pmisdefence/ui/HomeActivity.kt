package com.mkrlabs.pmisdefence.ui

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.mkrlabs.pmisdefence.BuildConfig
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.ActivityHomeBinding
import com.mkrlabs.pmisdefence.util.CommonFunction
import com.mkrlabs.pmisdefence.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var activityHomeBinding: ActivityHomeBinding
    lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration



    private lateinit var  sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
        navController = findNavController(R.id.newsNavHostFragment)
        sharedPref = SharedPref(this)

       /* appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_fragment,
                R.id.profile_fragment
            ),
            activityHomeBinding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)*/

        activityHomeBinding.navView.setupWithNavController(navController)
        setNavigationItem()



    }

    fun setNavigationItem(){

        activityHomeBinding.navigationInclude.navUserName.text= sharedPref.getLoggedInUserName()

        activityHomeBinding.navigationInclude.navRateTv.setOnClickListener{
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                    )
                )
            }
        }



        activityHomeBinding.navigationInclude.navLogoutTV.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { dialog, which ->
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(R.id.action_homeFragment_to_loginFragment)

                } // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }


        activityHomeBinding.navigationInclude.navDeveloperTV.setOnClickListener {
            closeNavigationDrawer()
            navController.navigate(R.id.action_homeFragment_to_informationFragment)
        }

        activityHomeBinding.navigationInclude.navVersion.setOnClickListener {

            val versionName = BuildConfig.VERSION_NAME
            val  appName = resources.getString(R.string.app_name)
            val appNameWithVersion = " $appName $versionName"
            CommonFunction.infoToast(this@HomeActivity,appNameWithVersion)
        }


    }

    public fun openNavigationDrawer(){
        activityHomeBinding.drawerLayout.open()
    }

    fun closeNavigationDrawer(){
        activityHomeBinding.drawerLayout.closeDrawers()
    }
    /*override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/
}