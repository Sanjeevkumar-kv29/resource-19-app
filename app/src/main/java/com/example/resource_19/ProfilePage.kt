package com.example.resource_19

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile_page.*

class ProfilePage : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)


        val sharepref: SharedPreferences = getSharedPreferences("uidpass",0)
        var currentusermobile = sharepref.getString("currentusermobile","").toString()
        var currentusername = sharepref.getString("name","").toString()
        var currentusercity = sharepref.getString("city","").toString()
        //var currentuser = sharepref.getString("currentusermobile","").toString()

        profilename.setText(currentusername)
        profilecity.setText(currentusercity)
        profilemobilenumber.setText("+91 ${currentusermobile}")

        logoutbtn.setOnClickListener {

            val sharepref: SharedPreferences = getSharedPreferences("uidpass",0)
            val editor: SharedPreferences.Editor = sharepref.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        overridePendingTransition(R.anim.leftout,R.anim.rightin)

    }

}