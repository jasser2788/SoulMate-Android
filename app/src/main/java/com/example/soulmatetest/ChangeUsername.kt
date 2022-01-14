package com.example.soulmatetest

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.example.soulmatetest.models.Catalogue
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
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        val apiInterface = ApiInterface.create()

        val map:HashMap<String, String> = HashMap()
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        map["username"]=newusernametxt.text.toString()
        apiInterface.update(mSharedPref.getString(ID,"").toString(),map).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null){

                    mSharedPref.edit().apply{

                        putString(USERNAME, user.username)

                    }.apply()
                    window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    saveUsernameChat()
                    updatecatalogue()
                    finish()

                }else{
                    Toast.makeText(this@ChangeUsername, "Username already exist", Toast.LENGTH_SHORT).show()
                    window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }

                /*progBar.visibility = View.INVISIBLE
                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)*/
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@ChangeUsername, "Connexion error!", Toast.LENGTH_SHORT).show()
                window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                /* progBar.visibility = View.INVISIBLE
                 window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)*/
            }

        })
    }

    private fun updatecatalogue() {

        val apiInterface = ApiInterface.create()
        val map: java.util.HashMap<String, String> = java.util.HashMap()
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        map["username"] = newusernametxt.text.toString()
        map["user_id"]=mSharedPref.getString(ID,"").toString()




        apiInterface.updateNameCatalogue(map).enqueue(object :
            Callback<Catalogue> {

            override fun onResponse(call: Call<Catalogue>, response: Response<Catalogue>) {
                val catalogue = response.body()

            }

            override fun onFailure(call: Call<Catalogue>, t: Throwable) {


            }
        })

    }

    private fun saveUsernameChat() {
        userchat = io.getstream.chat.android.client.models.User(
            id = mSharedPref.getString(ID, "").toString(),
            extraData = mutableMapOf(
                "name" to mSharedPref.getString(USERNAME, "").toString(),
                "image" to "https://firebasestorage.googleapis.com/v0/b/soulmate-fce7d.appspot.com/o/images%2F" + mSharedPref.getString(PICTURE, "").toString() + "?alt=media"


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
        newusernametxtLayout.error = null
        newusernametxt.setText(newusernametxt.text?.replace("\\s+".toRegex(), " ")?.trim())

        if (newusernametxt.length()< 4){
            newusernametxtLayout.error = "Minimum 4 Characters"

            return false
        }
        else newusernametxtLayout.error= null

        if (newusernametxt.text!!.isEmpty()){
            newusernametxtLayout.error = "Must No tBe Empty"

            return false
        }
        else newusernametxtLayout.error= null





        return true
    }
}