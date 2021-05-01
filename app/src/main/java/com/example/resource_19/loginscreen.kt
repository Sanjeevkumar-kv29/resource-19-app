package com.example.resource_19

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_home.*

class loginscreen : AppCompatActivity() {


    private var progressDialog: ProgressDialog? = null
    val fsdb = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressDialog = ProgressDialog(this)



        val sharepref: SharedPreferences = getSharedPreferences("uidpass",0)
        var user = sharepref.getString("currentusermobile","")

        /*if (user != "") {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this,user,Toast.LENGTH_LONG).show()

        }*/


        loginbttn?.setOnClickListener(View.OnClickListener {


            if (loginmobile.text.toString().isEmpty()){
                loginmobile.setError("Enter Mobile No")
            }
            else if (loginpass.text.toString().isEmpty()){
                loginpass.setError("Enter The Password")
            }
            else if (loginmobile.text?.length!! <10){

                loginmobile.setError("Enter Valid Mobile No")

            }
            else{

                validate(
                    loginmobile?.getText().toString(),
                    loginpass?.getText().toString()
                )

            }



        })


        log2reg.setOnClickListener {

            startActivity(Intent(this,Register::class.java))

        }

        forgetpass.setOnClickListener {

            startActivity(Intent(this,Forgetpass::class.java))
            overridePendingTransition(R.anim.leftout,R.anim.rightin)

        }


    }


    private fun validate(mobile: String, userPassword: String) {


            val docRef = fsdb.collection("REGISTERED-USERS").document(mobile)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document.data != null) {

                        var pass = document.get("password")
                        var name = document.get("name")
                        var city = document.get("city")
                        if (pass==userPassword){
                            Toast.makeText(this,"Login Successfull",Toast.LENGTH_LONG).show()

                            val sharepref: SharedPreferences = getSharedPreferences("uidpass",0)
                            sharepref.edit().putString("currentusermobile",mobile).apply()
                            sharepref.edit().putString("password",userPassword).apply()

                            sharepref.edit().putString("name", name.toString()).apply()
                            sharepref.edit().putString("city", city.toString()).apply()

                            finish()

                        }
                        else{
                            Toast.makeText(this,"Password Incorrect",Toast.LENGTH_LONG).show()
                        }


                    } else {

                        Log.d("User Not EXIST", "user not registered in firestore")

                        Toast.makeText(this,"Not registerd yet Pls regester",Toast.LENGTH_LONG).show()

                        //pD?.dismiss()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("ERROR OCCURRED","get failed with ", exception)

                    Toast.makeText(this,"An error encounter please try again after sometime",Toast.LENGTH_LONG).show()

                }



    }










}