package com.example.resource_19.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.resource_19.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.addnewresource_fragment.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class Addnewresource_fragment : Fragment() {

    var ResourceSelect= arrayOf("Select Resource","Oxygen cylinder","ICU and Ventilators","Plasma","Hospital Beds","Remedesivir","FabiFlue")
    var Indianstates= arrayOf("Select State","Andhra Pradesh", "Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa", "Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir",
        "Jharkhand","Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra",  "Manipur", "Meghalaya", "Mizoram", "Nagaland","Odisha","Punjab","Rajasthan","Sikkim",
        "Tamil Nadu","Telangana", "Tripura", "Uttarakhand","Uttar Pradesh","West Bengal","Andaman and Nicobar Islands","Chandigarh","Dadra and Nagar Haveli",
        "Daman and Diu", "Delhi", "Lakshadweep","Puducherry")


    var StateSpinner: Spinner? = null
    var ResourceType: Spinner? = null





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.addnewresource_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)





        StateSpinner = this.spinnerselectState
        val States = context?.let { ArrayAdapter<String>(it,android.R.layout.simple_spinner_dropdown_item,Indianstates) }
        States?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        StateSpinner!!.setAdapter(States)

        ResourceType = this.spinnerselectresource
        val resourcetype = context?.let { ArrayAdapter<String>(it,android.R.layout.simple_spinner_dropdown_item,ResourceSelect) }
        States?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ResourceType!!.setAdapter(resourcetype)


        val adddata = HashMap<String, Any>()







        addresourceBtn.setOnClickListener {

            if (spinnerselectState.selectedItem.toString()=="Select State"){
                Toast.makeText(context,"Select state",Toast.LENGTH_SHORT).show()

            }
            else{

                if ( cityname.text.toString()!!.isNullOrEmpty()){
                    cityname.setError("Enter city name")

                    Toast.makeText(context,"Enter city name",Toast.LENGTH_SHORT).show()
                }
                else{

                    if (spinnerselectresource!!.selectedItem=="Select Resource"){
                        Toast.makeText(context,"Select Resource",Toast.LENGTH_SHORT).show()

                    }

                    else{

                        if (providername.text.toString()!!.isNullOrEmpty()){
                            providername.setError("Enter provider name")
                            Toast.makeText(context,"Enter provider name",Toast.LENGTH_SHORT).show()

                        }

                        else{

                            if (providercontact.text.toString()!!.isNullOrEmpty()){
                                providername.setError("Enter provider contact")
                                Toast.makeText(context,"Enter provider contact",Toast.LENGTH_SHORT).show()

                            }
                            if (providercontact.text.length < 10){
                                providercontact.setError("Enter Valid contact no.")
                                Toast.makeText(context,"Enter Valid contact no.",Toast.LENGTH_SHORT).show()

                            }

                            else{
                                if (provideraddress.text.toString()!!.isNullOrEmpty()){
                                    provideraddress.setError("Enter provider address")
                                    Toast.makeText(context,"Enter provider address",Toast.LENGTH_SHORT).show()

                                }
                                else{
                                    if (verifiedby.text.toString()!!.isNullOrEmpty()){
                                        verifiedby.setError("Enter the name who are verifying it else enter none")
                                        Toast.makeText(context,"Error encounter",Toast.LENGTH_SHORT).show()

                                    }

                                    else{


                                        Toast.makeText(context,"Please wait adding resource...",Toast.LENGTH_SHORT).show()

                                        adddata["resource"] = spinnerselectresource.selectedItem

                                        adddata["State"] = spinnerselectState!!.selectedItem

                                        adddata["city"] = cityname.text.toString().toLowerCase()

                                        adddata["providername"] = providername.text.toString()

                                        adddata["providercontact"] = providercontact.text.toString()

                                        adddata["provideraddress"] = provideraddress.text.toString()

                                        adddata["verifiedBY"] = verifiedby.text.toString()

                                        adddata["comment"] = comment.text.toString()





                                        addUploadRecordToDb(adddata)
                                        myprogressbar.visibility = View.VISIBLE

                                    }
                                }
                            }
                        }

                    }

                }

            }

        }

    }


    private fun addUploadRecordToDb(data:HashMap<String, Any>){

        val db = FirebaseFirestore.getInstance()
        val sharepref: SharedPreferences? = activity?.getSharedPreferences("uidpass",0)
        var addeduser = sharepref?.getString("currentusermobile","").toString()

        val df: DateFormat = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
        val date: String = df.format(Calendar.getInstance().time)+" by "+addeduser


        db.collection("ADDED-RESOURCES").document(date)
            .set(data)
            .addOnSuccessListener { documentReference ->
                // pD?.dismiss()


                Toast.makeText(context, "ADDED Successfully", Toast.LENGTH_LONG).show()
                myprogressbar.visibility = View.GONE

            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error Encounter plz try after sometime", Toast.LENGTH_LONG).show()
                myprogressbar.visibility = View.GONE
            }
    }


}