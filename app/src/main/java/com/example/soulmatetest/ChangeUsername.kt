package com.example.soulmatetest

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.soulmatetest.models.User
import com.example.soulmatetest.utils.ApiInterface
import io.getstream.chat.android.client.ChatClient
import kotlinx.android.synthetic.main.activity_catalogue_user_detail.*
import kotlinx.android.synthetic.main.activity_change_username.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeUsername : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    private val client = ChatClient.instance()
    private lateinit var userchat: io.getstream.chat.android.client.models.User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_username)

        TopHeaderViewUsername.setBackButtonClickListener(){
            onBackPressed()

        }
        updateUsernameBtn.setOnClickListener(){
            if (validate())
            changeusername()
        }
    }

    private fun changeusername() {


        val apiInterface = ApiInterface.create()

        val map:HashMap<String, String> = HashMap()
        mSharedPref = getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        map["username"]=newusernametxt.text.toString()
        apiInterface.update(mSharedPref.getString(ID,"").toString(),map).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null){

                    mSharedPref.edit().apply{

                        putString(USERNAME, user.username)

                    }.apply()
                    saveUsernameChat()
                    finish()

                }else{
                    Toast.makeText(this@ChangeUsername, "Username already exist", Toast.LENGTH_SHORT).show()

                }

                /*progBar.visibility = View.INVISIBLE
                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)*/
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@ChangeUsername, "Connexion error!", Toast.LENGTH_SHORT).show()

                /* progBar.visibility = View.INVISIBLE
                 window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)*/
            }

        })
    }

    private fun saveUsernameChat() {
        userchat = io.getstream.chat.android.client.models.User(
            id = mSharedPref.getString(ID, "").toString(),
            extraData = mutableMapOf(
                "name" to mSharedPref.getString(USERNAME, "").toString()

            )
        )
        val client = ChatClient.instance()
        client.updateUser(

            user = userchat
        ).enqueue { result ->
            if (result.isSuccess) {
                Log.d("ChannelFragment", "Success changing Username")
            } else {
                Log.d("ChannelFragment", result.error().message.toString())
            }
        }
    }
    private fun validate(): Boolean {
        newusernametxt.error = null

        if (newusernametxt.length()< 4){
            newusernametxt.error = "Minimum 4 Characters"

            return false
        }
        else newusernametxt.error= null

        if (newusernametxt.text!!.isEmpty()){
            newusernametxt.error = "Must No tBe Empty"

            return false
        }
        else newusernametxt.error= null





        return true
    }
}