package com.example.soulmatetest

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.soulmatetest.models.ChatUser
import com.example.soulmatetest.models.User
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.livedata.ChatDomain

class FirstActivity : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    private lateinit var userchat: io.getstream.chat.android.client.models.User

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
            connectChat()

        }

        }

   private fun connectChat() {


        userchat = io.getstream.chat.android.client.models.User(
            id = mSharedPref.getString(ID,"").toString(),
            extraData = mutableMapOf(
                "name" to mSharedPref.getString(USERNAME,"").toString(),
                "image" to "https://firebasestorage.googleapis.com/v0/b/soulmateios.appspot.com/o/images%2F" + mSharedPref.getString(PICTURE,"").toString() + "?alt=media"

            )
        )
        val client = ChatClient.instance()
        val token = client.devToken(userchat.id)
        client.connectUser(
            user = userchat,
            token = token
        ).enqueue { result ->
            if (result.isSuccess) {
                Log.d("ChannelFragment", "Success Connecting the User")
            } else {
                Log.d("ChannelFragment", result.error().message.toString())
            }
        }
    }

    }
