package com.example.soulmatetest

import android.app.Application
import android.content.SharedPreferences
import android.widget.Toast
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.livedata.ChatDomain

class MyApplication: Application() {
    private lateinit var mSharedPref: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        val client =
            ChatClient.Builder(getString(R.string.api_key), this).logLevel(ChatLogLevel.ALL)
                .build()
        ChatDomain.Builder(client, this).build()


        }
}