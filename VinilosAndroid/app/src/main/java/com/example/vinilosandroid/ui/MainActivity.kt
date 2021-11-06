package com.example.vinilosandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.ActivityMainBinding
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val albumFragment = AlbumFragment()
    private val musicianFragment = MusicianFragment()
    private val collectorFragment = CollectorFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(albumFragment)

        val bottomNavigationView =
            findViewById<View>(R.id.bottomNav) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_album -> replaceFragment(albumFragment)
                R.id.action_musician -> replaceFragment(musicianFragment)
                R.id.action_collector -> replaceFragment(collectorFragment)
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        if(fragment !=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }
    }
}