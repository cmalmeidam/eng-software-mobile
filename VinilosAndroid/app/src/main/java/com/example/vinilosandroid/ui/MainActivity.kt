package com.example.vinilosandroid.ui

import android.app.ActivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomnav.setupWithNavController(navController)

        val initialFragment = intent.getIntExtra("initialFragment",R.id.albumFragment)
        navController.navigate(initialFragment)

        val appBarConfiguration = AppBarConfiguration(        topLevelDestinationIds = setOf(),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp)
        binding.myToolbar.setupWithNavController(navController, appBarConfiguration)

        getMemoryInfo()
    }

    private fun getMemoryInfo() {
        val activityManager = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        if (!memoryInfo.lowMemory) {
            TRIM_MEMORY_RUNNING_LOW
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}