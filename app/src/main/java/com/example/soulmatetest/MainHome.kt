package com.example.soulmatetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.soulmatetest.fragments.*
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
        val MyPost_Fragement = MyPostFragment()
        val favorite_Fragement = FavoriteFragment()
        val friend_Chat_Fragment = FriendChatFragment()

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
                R.id.navigation_post ->{
                    setCurrentFragment(MyPost_Fragement)
                }
                R.id.navigation_panier ->{
                    setCurrentFragment(favorite_Fragement)
                }
                R.id.navigation_users ->{
                    setCurrentFragment(friend_Chat_Fragment)
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
