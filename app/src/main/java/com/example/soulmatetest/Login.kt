package com.example.soulmatetest

import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.soulmatetest.models.ChatUser
import com.example.soulmatetest.models.User
import com.example.soulmatetest.utils.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.livedata.ChatDomain
import kotlinx.android.synthetic.main.fragment_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
const val PREF_NAME = "DATA_PREF"
const val USERNAME = "USERNAME"
const val PASSWORD = "PASSWORD"
const val PICTURE = "PICTURE"
const val EMAIL = "EMAIL"
const val ID = "ID"
const val FAVORITE = "FAVORITE"

const val IS_REMEMBRED = "IS_REMEMBRED"


class Login : AppCompatActivity() {


    lateinit var txtLogin: TextInputEditText
    // lateinit var txtLayoutLogin: TextInputLayout

    lateinit var txtPassword: TextInputEditText
    // lateinit var txtLayoutPassword: TextInputLayout
    lateinit var txtLayoutLogin: TextInputLayout
    lateinit var txtLayoutPassword: TextInputLayout


    lateinit var cbRememberMe: CheckBox
    lateinit var btnLogin: Button
    lateinit var btnSignup: Button
    private lateinit var mSharedPref: SharedPreferences
    private lateinit var userchat: io.getstream.chat.android.client.models.User
   // private val client = ChatClient.instance()


    //lateinit var progBar: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var context = this
        var connectivity : ConnectivityManager? = null
        var info : NetworkInfo? = null
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);


        txtLogin = findViewById(R.id.txtLogin)
        txtLayoutLogin = findViewById(R.id.txtLayoutLogin)
        //   txtLayoutLogin = findViewById(R.id.txtLayoutLogin)

        txtPassword = findViewById(R.id.txtPassword)
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword)
        //  txtLayoutPassword = findViewById(R.id.txtLayoutPassword)

        // cbRememberMe = findViewById(R.id.cbRememberMe)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignup)

        //  progBar = findViewById(R.id.progBar)
        //progBar.visibility = View.INVISIBLE

        btnLogin.setOnClickListener{
            /* connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE)
                     as ConnectivityManager

             if ( connectivity != null)
             {
                 info = connectivity!!.activeNetworkInfo

                 if (info != null)
                 {
                     if (info!!.state == NetworkInfo.State.CONNECTED)
                     {
                         Toast.makeText(context, "CONNECTED", Toast.LENGTH_LONG).show()
                     }
                 }
                 else
                 {
                     Toast.makeText(context, "NOT CONNECTED", Toast.LENGTH_LONG).show()
                 }
             }

 */

            doLogin()
        }




        btnSignup.setOnClickListener {


            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }


    }
    //disable back button
    override fun onBackPressed() {
        // Simply Do noting!
    }

    private fun doLogin(){

        if (validate()){

            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);



            val apiInterface = ApiInterface.create()
            /* progBar.visibility = View.VISIBLE

             window.setFlags(
                 WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                 WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
             )*/val map:HashMap<String, String> = HashMap()

            map["username"]=txtLogin.text.toString()
            map["password"]=txtPassword.text.toString()

            apiInterface.login(map).enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    val user = response.body()
                    if (user != null){

                      connectChat(user)
                        mSharedPref.edit().apply{
                            putString(ID,user.id)
                           putString(USERNAME, user.username)
                            putString(PASSWORD, user.password)
                           // putString(EMAIL, user.email)
                          putString(PICTURE, user.picture)

                            putBoolean(IS_REMEMBRED, false)
                        }.apply()
                        Toast.makeText(this@Login, "Login Success", Toast.LENGTH_SHORT).show()
                        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        val intent = Intent(this@Login,MainHome::class.java)
                        startActivity(intent)
                        finish()

                    }else{

                        Toast.makeText(this@Login, "User not found", Toast.LENGTH_SHORT).show()
                        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }

                    /*progBar.visibility = View.INVISIBLE
                    window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)*/
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@Login, "Connexion error!", Toast.LENGTH_SHORT).show()
                    window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    /* progBar.visibility = View.INVISIBLE
                     window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)*/
                }

            })

        }
    }

    private fun connectChat(user: User) {


        userchat = io.getstream.chat.android.client.models.User(
            id = user.id,
            extraData = mutableMapOf(
                "name" to user.username,
                "image" to "https://firebasestorage.googleapis.com/v0/b/soulmate-fce7d.appspot.com/o/images%2F" + user.picture + "?alt=media"

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

    private fun validate(): Boolean {
        txtLogin.error = null
        txtPassword.error = null
        txtLogin.setText(txtLogin.text?.replace("\\s+".toRegex(), " ")?.trim())

      /*  if (txtLogin.length()< 4){
            txtLayoutLogin.error = "Minimum 4 Characters"

            return false
        }
        else txtLayoutLogin.error= null*/

        if (txtLogin.text!!.isEmpty()){
            txtLayoutLogin.error = "Must No tBe Empty"

            return false
        }
        else txtLayoutLogin.error= null

      /*  if (txtPassword.length()< 8){
            txtLayoutPassword.error = "Minimum 8 Characters"

            return false
        }
        else txtLayoutPassword.error= null*/

        if (txtPassword.text!!.isEmpty()){
            txtLayoutPassword.error = "Must No tBe Empty"

            return false
        }
        else txtLayoutPassword.error= null

        return true
    }

}