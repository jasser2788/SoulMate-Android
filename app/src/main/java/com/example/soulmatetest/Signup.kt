package com.example.soulmatetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.soulmatetest.models.User
import com.example.soulmatetest.utils.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Signup : AppCompatActivity() {
    lateinit var txtLogin: TextInputEditText

    lateinit var txtPassword: TextInputEditText
    lateinit var txtConfirmPassword: TextInputEditText
    lateinit var txtLayoutPassword: TextInputLayout
    lateinit var txtLayoutLogin: TextInputLayout
    lateinit var txtLayoutConfirmPassword: TextInputLayout
    lateinit var btnLogin: Button


    lateinit var btnSignup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        txtLogin = findViewById(R.id.txtLogin)


        txtLayoutLogin = findViewById(R.id.txtLayoutLogin)

        //   txtLayoutLogin = findViewById(R.id.txtLayoutLogin)

        txtPassword = findViewById(R.id.txtPassword)
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword)

        //  txtLayoutPassword = findViewById(R.id.txtLayoutPassword)
        txtConfirmPassword= findViewById(R.id.txtConfirmPassword)
        txtLayoutConfirmPassword = findViewById(R.id.txtLayoutConfirmPassword)

        // cbRememberMe = findViewById(R.id.cbRememberMe)
        btnSignup = findViewById(R.id.signup)
        btnLogin = findViewById(R.id.Login)


        //  progBar = findViewById(R.id.progBar)
        //progBar.visibility = View.INVISIBLE
        btnLogin.setOnClickListener{
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        btnSignup.setOnClickListener{
            doLogin()
        }



    }
    //disable back button
    override fun onBackPressed() {
        // Simply Do noting!
    }


    private fun doLogin(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (validate()){
            val apiInterface = ApiInterface.create()
            /* progBar.visibility = View.VISIBLE

             window.setFlags(
                 WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                 WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
             )*/val map:HashMap<String, String> = HashMap()

            map["username"]=txtLogin.text.toString()
            map["password"]=txtPassword.text.toString()

            apiInterface.signup(map).enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    val user = response.body()

                    if (user != null){
                        val intent=Intent(this@Signup,Login::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this@Signup, "You can login now", Toast.LENGTH_LONG).show()
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }else{
                        Toast.makeText(this@Signup, "Username already exist", Toast.LENGTH_SHORT).show()
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }

                    /*progBar.visibility = View.INVISIBLE
                    window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)*/
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@Signup, "Connexion error!", Toast.LENGTH_SHORT).show()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    /* progBar.visibility = View.INVISIBLE
                     window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)*/
                }

            })

        }
    }


    private fun validate(): Boolean {
        txtLogin.error = null
        txtPassword.error = null

        if (txtLogin.text!!.isEmpty()){
            txtLayoutLogin.error = "mustNotBeEmpty"
            return false
        }        else txtLayoutLogin.error= null


        if (txtPassword.text!!.isEmpty()){
            txtLayoutPassword.error ="mustNotBeEmpty"
            return false
        }        else txtLayoutPassword.error= null


        if (txtPassword.text.toString()!=txtConfirmPassword.text.toString()){
            val toast = Toast.makeText(applicationContext, "password is not the same", Toast.LENGTH_SHORT)
            toast.show()
            txtLayoutConfirmPassword.error ="password is not the same"
            return false
        }       else txtLayoutConfirmPassword.error= null


        return true
    }


}