package com.example.resource_19

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val fsdb = FirebaseFirestore.getInstance()

    var mobile =""
    var pass =""
    var name =""
    var city =""

    private var pD: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        pD = ProgressDialog(this)


        regbtn.setOnClickListener{

          //  val regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$".toRegex()

            if (regmobile.text.toString().isEmpty()){
                regmobile.setError("Enter mobile number")
            }
            else if (regmobile.text.toString().length<10){
                regmobile.setError("Enter a valid Mobile no.")
            }
            else if (regpass.text.toString().isEmpty()){
                regpass.setError("Enter The Password")
            }
            else if (regpass.text.toString().length<6){
                regpass.setError("Password length should be greater the 6")
            }
            else if (regname.text.toString().isEmpty()){
                regname.setError("Enter Your Name.")
            }

            else if (regcity.text.toString().isEmpty()){
                regcity.setError("Enter Your City")
            }
            else{


                pD?.setMessage("Please Wait ")
                pD?.setCanceledOnTouchOutside(false);
                pD?.show()

                mobile = regmobile.text.toString()
                pass = regpass.text.toString()
                name = regname.text.toString()
                city= regcity.text.toString()

                fbfsprecheck(mobile)
            }

        }



        reg2log.setOnClickListener {

            finish()
            startActivity(Intent(this,loginscreen::class.java))
        }

    }


    override fun onBackPressed() {
        finish()
        startActivity(Intent(this,loginscreen::class.java))

    }



    private fun fbfsprecheck(mobilecheck:String){

        val docRef = fsdb.collection("REGISTERED-USERS").document(mobilecheck)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    Log.d("email EXIST", "DocumentSnapshot data: ${document.data}")
                    pD?.dismiss()
                    regmobile.setError("Mobile no. already registered")
                    Toast.makeText(this,"ALREADY REGISTERED PLEASE LOGIN ", Toast.LENGTH_LONG).show()


                } else {

                    Log.d("User Not EXIST", "user not registered in firestore")

                    // Toast.makeText(this,"bsdk registration kr",Toast.LENGTH_LONG).show()
                   val intent = Intent(this, Otpverification::class.java)    /*MOBILE NUMBER AUTHENTICATION OTP AUTHENTICATION*/
                   intent.putExtra("mobileno", mobile)
                   intent.putExtra("password", pass)
                   intent.putExtra("name", name)
                   intent.putExtra("city",city)
                   startActivity(intent)


                    pD?.dismiss()

                    finish()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("ERROR OCCURRED","get failed with ", exception)
            }


    }


}