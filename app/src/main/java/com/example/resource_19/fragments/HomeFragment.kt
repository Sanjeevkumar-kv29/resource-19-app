package com.example.resource_19.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.resource_19.Home_main_screen
import com.example.resource_19.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList

class HomeFragment : Fragment() {

    var CityNameArray= ArrayList<String>()
    var Stateselect: Spinner? = null
    var Cityselect: Spinner? = null
    var Indianstates = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getStateName()



        stateselectspinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (stateselectspinner.selectedItem == "Select State")
                {
                   selectcitylyt.visibility = View.GONE
                }
                else
                {
                    selectcitylyt.visibility = View.VISIBLE
                    val state = stateselectspinner.selectedItem.toString()
                    getCityName(state)

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        oxygenavail.setOnClickListener {

            if ((cityselectspinner.selectedItem == "Select City") or (stateselectspinner.selectedItem == "Select State"))
            {
                Toast.makeText(context,"Please Choose City",Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent = Intent(context,Home_main_screen::class.java)
                intent.putExtra("ClickedOption","Oxygen cylinder")
                intent.putExtra("City",cityselectspinner.selectedItem.toString())
                startActivity(intent)


            }


        }

        icuandventilators.setOnClickListener {

            if ((cityselectspinner.selectedItem == "Select City") or (stateselectspinner.selectedItem == "Select State"))
            {
                Toast.makeText(context,"Please Choose City",Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent = Intent(context,Home_main_screen::class.java)
                intent.putExtra("ClickedOption","ICU and Ventilators")
                intent.putExtra("City",cityselectspinner.selectedItem.toString())
                startActivity(intent)


            }
        }

        plasmaavail.setOnClickListener {

            if ((cityselectspinner.selectedItem == "Select City") or (stateselectspinner.selectedItem == "Select State"))
            {
                Toast.makeText(context,"Please Choose City",Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent = Intent(context,Home_main_screen::class.java)
                intent.putExtra("ClickedOption","Plasma")
                intent.putExtra("City",cityselectspinner.selectedItem.toString())
                startActivity(intent)


            }

        }

        hospitalbed.setOnClickListener {

            if ((cityselectspinner.selectedItem == "Select City") or (stateselectspinner.selectedItem == "Select State"))
            {
                Toast.makeText(context,"Please Choose City",Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent = Intent(context,Home_main_screen::class.java)
                intent.putExtra("ClickedOption","Hospital Beds")
                intent.putExtra("City",cityselectspinner.selectedItem.toString())
                startActivity(intent)


            }

        }

        remdesivir.setOnClickListener {

            if ((cityselectspinner.selectedItem == "Select City") or (stateselectspinner.selectedItem == "Select State"))
            {
                Toast.makeText(context,"Please Choose City",Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent = Intent(context,Home_main_screen::class.java)
                intent.putExtra("ClickedOption","Remedesivir")
                intent.putExtra("City",cityselectspinner.selectedItem.toString())
                startActivity(intent)


            }
        }

        fabiflue.setOnClickListener {

            if ((cityselectspinner.selectedItem == "Select City") or (stateselectspinner.selectedItem == "Select State"))
            {
                Toast.makeText(context,"Please Choose City",Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent = Intent(context,Home_main_screen::class.java)
                intent.putExtra("ClickedOption","FabiFlue")
                intent.putExtra("City",cityselectspinner.selectedItem.toString())
                startActivity(intent)


            }
        }


    }

    private fun getCityName(state: String) {

        val db = FirebaseFirestore.getInstance()
        CityNameArray.clear()
        CityNameArray.add("Select City")
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

                    if (it.get("State").toString()==state){

                        if (CityNameArray.contains(it.get("city").toString()))

                        else{
                            CityNameArray.add(it.get("city").toString())
                        }

                    }

                }

                Log.w("cityfound", CityNameArray.toString(), e)

                if (CityNameArray.count()==0){
                    Toast.makeText(context,"Network Error Plz try again",Toast.LENGTH_SHORT).show()
                }
                else{

                    val City = context?.let { ArrayAdapter<String>(it,android.R.layout.simple_spinner_dropdown_item,CityNameArray) }
                    City?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    cityselectspinner?.setAdapter(City)

                }

            }
        }


    }

    private fun getStateName() {

        val db = FirebaseFirestore.getInstance()
        Indianstates.clear()
        Indianstates.add("Select State")

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

                    Log.w("state",it.get("State").toString(), e)

                    Log.w("state",it.id.toString(), e)

                    if (Indianstates.contains(it.get("State").toString()))

                    else{
                        Indianstates.add(it.get("State").toString())
                    }



                }

                val States = context?.let { ArrayAdapter<String>(it,android.R.layout.simple_spinner_dropdown_item,Indianstates) }
                States?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                stateselectspinner?.setAdapter(States)

                Log.w("catfound", CityNameArray.toString(), e)

            }
        }


    }


}