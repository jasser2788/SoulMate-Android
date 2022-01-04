package com.example.soulmatetest.fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.soulmatetest.*
import com.example.soulmatetest.models.User
import com.example.soulmatetest.utils.ApiInterface
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import io.getstream.chat.android.client.ChatClient
import kotlinx.android.synthetic.main.activity_catalogue_user_detail.*
import kotlinx.android.synthetic.main.fragment_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.streams.asSequence


class UserFragment : Fragment() {
    private lateinit var mSharedPref: SharedPreferences
    private var selectedImageUri: Uri? = null
    lateinit var storage: FirebaseStorage
    lateinit var formater: String
    private val client = ChatClient.instance()
    private lateinit var userchat: io.getstream.chat.android.client.models.User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val pictureName = mSharedPref.getString(PICTURE, "").toString()
      //  val storageRef = FirebaseStorage.getInstance().reference.child("images/$pictureName")
       // val localFile = File.createTempFile("tempImage", "jpg")

        if(pictureName!="No Picture")
        {
            /*activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/
         /*   storageRef.getFile(localFile).addOnSuccessListener {

                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                imageuser.setImageBitmap(bitmap)
            }.addOnFailureListener{
                Toast.makeText(activity, "Failed to get image", Toast.LENGTH_SHORT).show()
            }*/
            //OTHER METHODE TO IMPORT IMAGE
            val filename = mSharedPref.getString(PICTURE, "").toString()
            val path =
                "https://firebasestorage.googleapis.com/v0/b/soulmate-fce7d.appspot.com/o/images%2F" + filename + "?alt=media"
            Glide.with(context)
                .load(path)
                .into(imageuser)

          //  activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }
        //fireBase
       // storage = Firebase.storage

        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        usernameProfile.setText(mSharedPref.getString(USERNAME, "").toString())

        updatepass.setOnClickListener() {
            updatepass.isClickable = false
            val intent = Intent (context, ChangePassword::class.java)
            context?.startActivity(intent)
        }
        updateusername.setOnClickListener(){
            updateusername.isClickable = false
            val intent = Intent (context, ChangeUsername::class.java)
            context?.startActivity(intent)
        }
        logoutbtn.setOnClickListener(){
            val builder = AlertDialog.Builder(view.context)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want to logout ?")
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
                    .clear().apply()
                //logout chat
                client.disconnect()
                val intent = Intent(context, Login::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            builder.create().show()
        }

        imageuser.setOnClickListener(){
            imageuser.isClickable=false

            openGallery()
        }

    }

    override fun onResume() {
        super.onResume()

        updateusername.isClickable = true
        updatepass.isClickable = true

        imageuser.isClickable=true
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        usernameProfile.setText(mSharedPref.getString(USERNAME, "").toString())
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK)
        {
            selectedImageUri = data?.data!!
            imageuser!!.setImageURI(selectedImageUri)
            uploadImage()
        }
    }

    private fun uploadImage() {
     formater = java.util.UUID.randomUUID().toString()

//        val formater = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
//        val now = Date()
//        val fileName = formater.format(now)

        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Uploading Image ...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val storageReference = FirebaseStorage.getInstance().reference.child("images/$formater")
        storageReference.putFile(selectedImageUri!!).
        addOnSuccessListener {

            imageuser.setImageURI(selectedImageUri)
            if(progressDialog.isShowing)
            {
                progressDialog.dismiss()
            }
            Toast.makeText(activity,"Image successfuly uploaded", Toast.LENGTH_SHORT).show()
            saveImageChat(formater)
            saveImageServer()
        }.addOnFailureListener{
            if(progressDialog.isShowing)
            {
                progressDialog.dismiss()
            }
            Toast.makeText(activity,"upload error", Toast.LENGTH_LONG).show()

        }
    }

   private fun saveImageChat(formater : String) {
        userchat = io.getstream.chat.android.client.models.User(
            id = mSharedPref.getString(ID, "").toString(),
            extraData = mutableMapOf(
                "name" to mSharedPref.getString(USERNAME, "").toString(),
                "image" to "https://firebasestorage.googleapis.com/v0/b/soulmate-fce7d.appspot.com/o/images%2F" + formater + "?alt=media"

            )
        )
        val client = ChatClient.instance()
        client.updateUser(
            user = userchat
        ).enqueue { result ->
            if (result.isSuccess) {
                Log.d("ChannelFragment", "Success Connecting the User")
            } else {
                Log.d("ChannelFragment", result.error().message.toString())
            }
        }
    }

    private fun saveImageServer() {
        val apiInterface = ApiInterface.create()
        val map: HashMap<String, String> = HashMap()

        map["picture"] = formater
        apiInterface.update(mSharedPref.getString(ID, "").toString(),map).enqueue(object :
            Callback<User> {

            override fun onResponse(call: Call<User>, response:
            Response<User>
            ) {
                val user = response.body()
                if(user !=null)
                {
                    mSharedPref.edit().apply{

                        putString(PICTURE, user.picture)

                    }.apply()

                }
                else
                {
                    Toast.makeText(activity,"error!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(activity,t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

}