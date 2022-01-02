package com.example.soulmatetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.soulmatetest.fragments.ChatFragment

class SingleChat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_chat)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val chatFragment = ChatFragment()
        val bundle = Bundle()
        bundle.putString("selectedUser", intent.getStringExtra("selectedUser").toString())
        bundle.putString("channelId", intent.getStringExtra("channelId").toString())

        chatFragment.arguments = bundle
        fragmentTransaction.add(R.id.fragmentContainerView2, chatFragment).commit()
    }
}