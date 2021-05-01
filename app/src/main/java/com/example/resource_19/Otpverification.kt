package com.example.resource_19

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_otpverification.*
import java.util.concurrent.TimeUnit

class Otpverification : AppCompatActivity() {

    var mobile = ""
    var pass = ""
    var name = ""
    var city = ""

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var mVerificationId = ""
    private var pD: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification)
        pD = ProgressDialog(this)


        val intent = intent
        mobile = intent.getStringExtra("mobileno").toString()
        pass = intent.getStringExtra("password").toString()
        name = intent.getStringExtra("name").toString()
        city = intent.getStringExtra("city").toString()


        sendVerificationCode(mobile);
        pD?.setMessage("Please Wait ")
        pD?.setCanceledOnTouchOutside(false);
        pD?.show()
        Toast.makeText(this,"Please Wait Sending OTP...", Toast.LENGTH_LONG).show()

        otpverify.setOnClickListener {
            if (enterotp.text.toString().isNullOrEmpty())
                enterotp.setError("Enter Otp")
            else
            verifyVerificationCode(enterotp.text.toString())
        }
    }

    override fun onBackPressed() {

    }

    private fun sendVerificationCode(mobile: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91$mobile",
            60,
            TimeUnit.SECONDS,
            this,
            mCallbacks
        )
    }

    //the callback to detect the verification status
    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                //Getting the code sent by SMS
                val code = phoneAuthCredential.smsCode

                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code
                if (code != null) {

                    //automatic verify is sim card exist in same phone
                    enterotp.setText(code)
                    //verifying the code
                    verifyVerificationCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                pD?.dismiss()
                Toast.makeText(this@Otpverification, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {

                super.onCodeSent(s, forceResendingToken)
                pD?.dismiss()
                otpwaitlyt.visibility = View.VISIBLE
                otpmsgtv.setText("Otp sent Successfully")



                //storing the verification id that is sent to the user
                mVerificationId = s

            }
        }


    private fun verifyVerificationCode(code: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)

        //signing the user
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(
                this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        //verification successful we will start the profile activity
                        FireStoreReg()
                        Toast.makeText(this,"OTP verified",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {

                        //verification unsuccessful.. display an error message
                        var message ="Somthing is wrong, we will fix it soon..."

                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }

                        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
                    }
                })
    }


    fun FireStoreReg(){

        val FSDB : FirebaseFirestore = FirebaseFirestore.getInstance()

        val datamap:MutableMap<String,Any> = HashMap<String,Any>()

        datamap.put("mobile",mobile)
        datamap.put("password",pass)
        datamap.put("name",name)
        datamap.put("city",city)

        FSDB.collection("REGISTERED-USERS").document(mobile).set(datamap)
            .addOnSuccessListener {
                Toast.makeText(this,"Register SucessFull",Toast.LENGTH_LONG).show()
                val sharepref: SharedPreferences = getSharedPreferences("uidpass",0)
                sharepref.edit().putString("currentusermobile",mobile).apply()
                sharepref.edit().putString("password",pass).apply()
                sharepref.edit().putString("name",name).apply()
                sharepref.edit().putString("city",city).apply()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Connection ERROR",Toast.LENGTH_LONG).show()
            }


    }



}