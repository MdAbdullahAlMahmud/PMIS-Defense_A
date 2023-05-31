package com.mkrlabs.pmisdefence.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.ActivityHomeBinding
import com.mkrlabs.pmisdefence.databinding.NavDesignBinding
import com.mkrlabs.pmisdefence.model.User
import com.mkrlabs.pmisdefence.util.CommonFunction
import com.mkrlabs.pmisdefence.view_model.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var activityHomeBinding: ActivityHomeBinding
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navCustomLayout:NavDesignBinding
     lateinit var authenticationViewModel: AuthenticationViewModel



    lateinit var navUserNameTV :TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
         navCustomLayout = NavDesignBinding.inflate(LayoutInflater.from(activityHomeBinding.root.context))

       // navinit()
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        setContentView(activityHomeBinding.root)
        navController = findNavController(R.id.newsNavHostFragment)


        activityHomeBinding.navView.setupWithNavController(navController)




        setUpNavDrawer(User(),this)
         navUserNameTV = findViewById<TextView>(R.id.nav_user_name)
        /*

         navUserDeveloperTV = findViewById<TextView>(R.id.navDeveloperTV)
         navUserRateTV = findViewById<TextView>(R.id.navRateTv)
         navUserAboutTV = findViewById<TextView>(R.id.navAboutTv)
         navUserLogoutTV = findViewById<TextView>(R.id.navLogoutTV)
         navUserVersionTV = findViewById<TextView>(R.id.navVersion)

         */

    }

    private fun navinit(){
        var navUserNameTV = findViewById<TextView>(R.id.nav_user_name)
        var navUserDeveloperTV = findViewById<TextView>(R.id.navDeveloperTV)
        var navUserRateTV = findViewById<TextView>(R.id.navRateTv)
        var navUserAboutTV = findViewById<TextView>(R.id.navAboutTv)
        var navUserLogoutTV = findViewById<TextView>(R.id.navLogoutTV)
        var navUserVersionTV = findViewById<TextView>(R.id.navVersion)
    }
    private fun setUpNavDrawer(user: User, context: Context){


           activityHomeBinding.navigationMenu.navLogoutTV.setOnClickListener {
              CommonFunction.successToast(context,"Logout")
              val alertDialogBuilder = AlertDialog.Builder(context)

              alertDialogBuilder.setTitle("Logout")
              alertDialogBuilder.setMessage("Do you want logout ?")
              alertDialogBuilder.setCancelable(true)

              alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
                  FirebaseAuth.getInstance().signOut()
                  dialog.dismiss()
                  closeNavigationDrawer()
                  navController.navigate(R.id.action_homeFragment_to_loginFragment)

              }
              alertDialogBuilder.setNegativeButton("Cancel"){dialog, which ->
                  dialog.dismiss()
              }

              val alertDialog = alertDialogBuilder.create()
              alertDialog.show()

          }

        activityHomeBinding.navigationMenu.navRateTv.setOnClickListener {
              CommonFunction.successToast(context,"Rate Us")

          }


    }

    public fun openNavigationDrawer(){
        activityHomeBinding.drawerLayout.open()
    }
    public fun closeNavigationDrawer(){
        activityHomeBinding.drawerLayout.close()
    }


    /*override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/
}