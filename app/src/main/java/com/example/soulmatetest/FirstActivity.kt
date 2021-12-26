package com.example.soulmatetest

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class FirstActivity : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        if (mSharedPref.getBoolean(IS_REMEMBRED, true))
        {
            val intent = Intent(this,SplashScreen::class.java)
            startActivity(intent)
            finish()

            }else{
            val intent = Intent(this,MainHome::class.java)
            startActivity(intent)
            finish()

            }

        }

    }
