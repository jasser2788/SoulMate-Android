package com.example.soulmatetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.soulmatetest.fragments.HomeFragment
import com.example.soulmatetest.fragments.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main_home.*


class MainHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)

        toolbar54.visibility = View.GONE

      val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val user_Fragement = UserFragment()
        val home_Fragement = HomeFragment()
        val menu = bottomNavigationView.menu
        setCurrentFragment(home_Fragement)
        bottomNavigationView.setOnItemSelectedListener() {

            when(it.itemId) {

                R.id.navigation_profil ->{

                    setCurrentFragment(user_Fragement)
                }
                R.id.navigation_home ->{
                    setCurrentFragment(home_Fragement)

                }

            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container,fragment)
            addToBackStack("")
            commit()
        }
    }
