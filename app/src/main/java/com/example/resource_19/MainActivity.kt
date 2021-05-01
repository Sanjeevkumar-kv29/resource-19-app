package com.example.resource_19

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.resource_19.fragments.Addnewresource_fragment
import com.example.resource_19.fragments.Helppost_fragment
import com.example.resource_19.fragments.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()  {

    var currentuser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)


            openFragment(HomeFragment())
            navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


            profileicon.setOnClickListener {


                val sharepref: SharedPreferences = getSharedPreferences("uidpass",0)
                currentuser = sharepref.getString("currentusermobile","").toString()

                if (currentuser == "") {
                    Toast.makeText(this,"You need to login first",Toast.LENGTH_LONG)
                    startActivity(Intent(this, loginscreen::class.java))

                }
                else{

                    Toast.makeText(this,currentuser,Toast.LENGTH_LONG)
                    Log.d("currentuser", currentuser)
                    startActivity(Intent(this,ProfilePage::class.java))
                    overridePendingTransition(R.anim.leftin,R.anim.rightout)

                }

            }


        }




    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                toolbarlyt.visibility = View.VISIBLE
                toolbartitle.text = "Resource-19"
                openFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_addresources -> {

                val sharepref: SharedPreferences = getSharedPreferences("uidpass",0)
                currentuser = sharepref.getString("currentusermobile","").toString()

                if (currentuser == "") {
                    Toast.makeText(this,"You need to login first",Toast.LENGTH_LONG)
                    startActivity(Intent(this, loginscreen::class.java))

                }
                else{
                    toolbarlyt.visibility = View.VISIBLE
                    toolbartitle.text = "Add Resource"
                    openFragment(Addnewresource_fragment())
                    return@OnNavigationItemSelectedListener true

                }
            }
            R.id.navigation_posthelp -> {


                val sharepref: SharedPreferences = getSharedPreferences("uidpass",0)
                currentuser = sharepref.getString("currentusermobile","").toString()

                if (currentuser == "") {
                    Toast.makeText(this,"You need to login first",Toast.LENGTH_LONG)
                    startActivity(Intent(this, loginscreen::class.java))

                }
                else{

                    toolbarlyt.visibility = View.GONE
                    openFragment(Helppost_fragment())
                    return@OnNavigationItemSelectedListener true

                }


            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }



}