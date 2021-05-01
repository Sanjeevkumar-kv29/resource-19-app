package com.example.resource_19

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resource_19.Adapter.resourcesadapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_main_screen.*
import kotlinx.android.synthetic.main.addnewresource_fragment.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class Home_main_screen : AppCompatActivity() {

    private val resourceName =  ArrayList<String>()
    private val cityname = ArrayList<String>()
    private val statename = ArrayList<String>()
    private val providername =  ArrayList<String>()
    private val providercontact =   ArrayList<String>()
    private val provideraddress =   ArrayList<String>()
    private val verifiedby =   ArrayList<String>()
    private val moredetail =  ArrayList<String>()
    private val timeadded = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_main_screen)
        val resourceselected = intent.getStringExtra("ClickedOption").toString()
        val cityselected = intent.getStringExtra("City").toString()
        toolbartitlehome.setText(resourceselected)
        selectedcity.setText("city - ${cityselected}")
        getalldata(cityselected,resourceselected)


        goback.setOnClickListener {
            finish()
        }
    }




    private fun getalldata(city: String,resource: String) {

        val db = FirebaseFirestore.getInstance()
        resourceName.clear()
        cityname.clear()
        statename.clear()
        providername.clear()
        providercontact.clear()
        provideraddress.clear()
        verifiedby.clear()
        timeadded.clear()
        moredetail.clear()

        db.collection("ADDED-RESOURCES").addSnapshotListener {
                snapshot, e ->
            // if there is an exception we want to skip.
            if (e != null) {
                Log.w("Failed", "Listen Failed", e)
                return@addSnapshotListener
            }
            // if we are here, we did not encounter an exception
            if (snapshot != null) {
                // now, we have a populated shapshot
                val documents = snapshot.documents
                documents.forEach {

                    Log.w("state",it.get("city").toString(), e)

                    if ((it.get("city").toString()==city) and (it.get("resource").toString()==resource)){

                        resourceName.add(it.get("resource").toString())
                        cityname.add(it.get("city").toString())
                        statename.add(it.get("State").toString())
                        providername.add(it.get("providername").toString())
                        providercontact.add(it.get("providercontact").toString())
                        provideraddress.add(it.get("provideraddress").toString())
                        verifiedby.add(it.get("verifiedBY").toString())
                        timeadded.add(it.id.take(22))
                        moredetail.add(it.get("comment").toString())

                    }

                }


                Log.w("details",resourceName.count().toString()+cityname.count().toString()+statename.count().toString()+providername.count().toString()+providercontact.count().toString()+provideraddress.count().toString(), e)
                Log.w("details",verifiedby.count().toString()+timeadded.count().toString()+moredetail.count().toString(), e)


                Log.w("cityfound", resourceName.toString(), e)

                if (resourceName.count()==0){
                    Toast.makeText(this,"No data Available or Network Error Plz try again", Toast.LENGTH_SHORT).show()
                }

                else{
                    foorvadap()
                }

            }
        }


    }

    private fun foorvadap() {

        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val recyclerView: RecyclerView = mainRV
        recyclerView.layoutManager = layoutManager
        val adapter = resourcesadapter(
            this,
            resourceName,
            cityname,
            statename,
            providername,
            providercontact,
            provideraddress,
            verifiedby,
            moredetail,
            timeadded
        )
        recyclerView.adapter = adapter

    }


}