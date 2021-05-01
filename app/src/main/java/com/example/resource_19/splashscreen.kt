package com.example.resource_19

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class splashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed(
            {
                startActivity(Intent(this,MainActivity::class.java))
                overridePendingTransition(R.anim.leftout,R.anim.rightin)
                finish()

            },3000 )
    }
}