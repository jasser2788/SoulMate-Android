package com.example.soulmatetest

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.soulmatetest.models.User
import com.example.soulmatetest.utils.ApiInterface
import io.getstream.chat.android.client.ChatClient
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_change_username.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassword : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences
    private val client = ChatClient.instance()
    private lateinit var userchat: io.getstream.chat.android.client.models.User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        TopHeaderViewPass.setBackButtonClickListener(){
            onBackPressed()

        }
        UpdatePassBtn.setOnClickListener(){
            if(validate())
            {
                changepassword()
            }
        }

    }

    private fun changepassword() {


        val apiInterface = ApiInterface.create()

        val map:HashMap<String, String> = HashMap()
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        map["password"]=newpasswordtxt.text.toString()
        apiInterface.update(mSharedPref.getString(ID,"").toString(),map).enqueue(object :
            Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null){

                    mSharedPref.edit().apply{

                        putString(PASSWORD, user.password)

                    }.apply()
                    finish()

                }else{
                    Toast.makeText(this@ChangePassword, "Error", Toast.LENGTH_SHORT).show()

                }

                /*progBar.visibility = View.INVISIBLE
                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)*/
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@ChangePassword, "Connexion error!", Toast.LENGTH_SHORT).show()

                /* progBar.visibility = View.INVISIBLE
                 window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)*/
            }

        })
    }


    private fun validate(): Boolean {
        newpasswordtxt.error = null
        confirmnewpasswordtxt.error = null


        if (newpasswordtxt.length()< 8){
                newpasswordtxt.error = "Minimum 8 Characters"

            return false
        }
        else newpasswordtxt.error= null

        if (newpasswordtxt.text!!.isEmpty()){
            newpasswordtxt.error = "Must No tBe Empty"

            return false
        }
        else newpasswordtxt.error= null

        if (newpasswordtxt.text.toString() != confirmnewpasswordtxt.text.toString()){
            confirmnewpasswordtxt.error = "Password not the same"

            return false
        }
        else confirmnewpasswordtxt.error= null



        return true
    }
}