package com.ayein.bitebuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ayein.bitebuddy.databinding.ActivityMainBinding
import com.ayein.bitebuddy.databinding.FragmentNotificationBottomSheetBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: FIX CRASHING OF NOTIFICATION DRAWER

class MainActivity : AppCompatActivity() {

    //    APP CRASH
//    private lateinit var binding: ActivityMainBinding

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navController = findNavController(R.id.fragmentContainerView)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setupWithNavController(navController)
        bottomNav.itemActiveIndicatorColor = getColorStateList(R.color.primaryContainer)

        binding.homeNotificationButton.setOnClickListener {
            val bottomSheetDialog = NotificationBottomSheetFragment()
            bottomSheetDialog.show(supportFragmentManager, "TEST")
        }

    // APP CRASHES HERE - NOTIFICATION BINDING
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.homeNotificationButton.setOnClickListener {
//            val bottomSheetDialog = NotificationBottomSheetFragment()
//            bottomSheetDialog.show(supportFragmentManager, "TEST")
//        }


    }

}