package com.example.resource_19.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.resource_19.Adapter.Helppostadapter
import com.example.resource_19.R
import com.example.resource_19.dataClasses.HelpPostDetailsDataClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_main_screen.*
import kotlinx.android.synthetic.main.helppost_fragment.*
import org.json.JSONException
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class Helppost_fragment : Fragment() {

    /// sorts by timing
    var TimeSorter = Comparator<HelpPostDetailsDataClass> { b1, b2 ->
        b2.posttimeadded.compareTo(b1.posttimeadded, true)
    }

    var PostDetails = arrayListOf<HelpPostDetailsDataClass>()


    var deletepostsp: Spinner? = null
    private val postlistfordelete =   ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.helppost_fragment, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val sharepref: SharedPreferences? = activity?.getSharedPreferences("uidpass",0)
        var currentusermobile = sharepref?.getString("currentusermobile","").toString()
        var currentusername = sharepref?.getString("name","").toString()
        var currentusercity = sharepref?.getString("city","").toString()


       gettingpostdetails()

        addpost.setOnClickListener {

            optionlyt.visibility = View.VISIBLE
            addpostlyt.visibility = View.VISIBLE
            deletepostlyt.visibility = View.GONE


        }

        deletepost?.setOnClickListener {


            gettingpostfordelete(currentusermobile)
            deletepostsp = this.deletepostspinner
            val PostnameNameArrayAdapter = context?.let { it1 -> ArrayAdapter<String>(it1,android.R.layout.simple_spinner_dropdown_item,postlistfordelete) }
            PostnameNameArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            deletepostspinner.setAdapter(PostnameNameArrayAdapter)

            optionlyt.visibility = View.VISIBLE
            addpostlyt.visibility = View.GONE
            deletepostlyt.visibility = View.VISIBLE


        }


        cancleicon.setOnClickListener {

            optionlyt.visibility = View.GONE
            addpostlyt.visibility = View.GONE
            deletepostlyt.visibility = View.GONE

        }

        publishpostbtn.setOnClickListener {

            if (addposttitle.text.isNullOrEmpty())
                addposttitle.setError("Enter title")

            else if (addpostET.text.isNullOrEmpty())
                addpostET.setError("Enter Post Body")

            else{

                val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

                if (isConnected==true){

                    createpost(addposttitle.text.toString(),addpostET.text.toString(),currentusername,currentusermobile,currentusercity)
                    addposttitle.setText(null)
                    addpostET.setText(null)
                    optionlyt.visibility = View.GONE
                }
                else{
                    Toast.makeText(context, "network error check or internet", Toast.LENGTH_LONG).show()
                }


            }

        }



        deletepostbtn.setOnClickListener {


            if (deletepostspinner.selectedItem.toString()=="select"){
                Toast.makeText(context, "Invalid post for delete", Toast.LENGTH_LONG).show()
            }
            else{

                deleteselectedpost(deletepostspinner.selectedItem.toString())

            }

        }
    }


//---------------------------------------------------------------create post function for adding post------------------------------------------------------------------

    private fun createpost(
        posttitle: String,
        postbodyvalue: String,
        publishername: String,
        publishermobile: String,
        publishercity: String
         ) {
        val db = FirebaseFirestore.getInstance()

        val datamap:MutableMap<String,Any> = HashMap<String,Any>()

        datamap.put("posttitle",posttitle)
        datamap.put("postbody",postbodyvalue)
        datamap.put("publishername",publishername)
        datamap.put("publishercity",publishercity)

        datamap.put("publishermobile",publishermobile)

        val df: DateFormat = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
        val date: String = df.format(Calendar.getInstance().time)+" by "+publishermobile

        Toast.makeText(context, "Creating Post....", Toast.LENGTH_LONG).show()

        db.collection("HELP-POSTS").document(date)
            .set(datamap)
            .addOnSuccessListener { documentReference ->

                /*posttitlename.add(datamap["posttitle"].toString())
                publishernamename.add(datamap["publishername"].toString())
                publishercityname.add(datamap["publishercity"].toString())
                postbody.add(datamap["postbody"].toString())
                posttimeadded.add(date.take(22))*/

                Toast.makeText(context, "ADDED Successfully", Toast.LENGTH_LONG).show()
                gettingpostdetails()
                sendnoti(context,publishercity,posttitle,postbodyvalue)
                //refreshfrag(Helppost_fragment())


            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error Encounter plz try after sometime", Toast.LENGTH_LONG).show()

            }


    }


//-------------------------------------------------------------------fetching all the previous posts------------------------------------------------------------------

    private fun gettingpostdetails() {

        val db = FirebaseFirestore.getInstance()
        PostDetails.clear()

        db.collection("HELP-POSTS").addSnapshotListener {
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


                   /* if (PostDetails.posttimeadded.contains(it.id.take(22)))

                    else{



                    }*/

                    val postdata = HelpPostDetailsDataClass(
                            it.get("posttitle").toString(),
                            it.get("publishername").toString(),
                            it.get("publishercity").toString(),
                            it.get("postbody").toString(),
                            it.id.take(22)
                            )
                    PostDetails.add(postdata)

                }


                }



                if (PostDetails.count()==0){
                    Toast.makeText(context,"No Posts Available or Network Error Plz try again", Toast.LENGTH_SHORT).show()
                }

                else{
                    Collections.sort(PostDetails, TimeSorter)
                    postfoorvadap()  }

            }
        }


//----------------------------------------------------------------getting post id for deleting--------------------------------------------------------------------------

    private fun gettingpostfordelete(currentusermobile:String) {

        postlistfordelete.clear()
        postlistfordelete.add("select")

        val db = FirebaseFirestore.getInstance()
        db.collection("HELP-POSTS").addSnapshotListener {
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

                    if (it.get("publishermobile")==currentusermobile){

                        if (postlistfordelete.contains(it.id.take(22)))
                            //do nothing
                        else{ postlistfordelete.add(it.id)}

                    }
                }

            }

            if (postlistfordelete.count()==0){ Toast.makeText(context,"No Posts Available or Network Error Plz try again", Toast.LENGTH_SHORT).show() }
            else{ postfoorvadap() }

        }

    }



//----------------------------------------------------------------------delete post from database-----------------------------------------------------------------------

    private fun deleteselectedpost(postfordelete: String) {


        val db = FirebaseFirestore.getInstance()
        db.collection("HELP-POSTS").document(postfordelete)
                .delete()
                .addOnSuccessListener {

                    Toast.makeText(context, "Post ${postfordelete} Deleted SuccessFully", Toast.LENGTH_LONG).show()
                    optionlyt.visibility = View.GONE
                    gettingpostdetails()
                }
                .addOnFailureListener {
                    e -> Log.w("Error in Deleting Cat", "Error deleting document", e)
                    Toast.makeText(context, "Error While Deleting Post Please Check Your INTERNET CONNECTION", Toast.LENGTH_SHORT).show()
                }

    }


// ---------------------------------------------------adapter for post recycler view------------------------------------------------------------------------------------

    private fun postfoorvadap() {

        val layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recyclerView: RecyclerView = helppostRV
        recyclerView.layoutManager = layoutManager
        val adapter = Helppostadapter(context,PostDetails)
        //Log.d("dodo",postbody.count().toString())
        recyclerView.adapter = adapter


    }



    fun sendnoti(context: Context?,city: String,title: String,body: String){

        val mRequestQue: RequestQueue = Volley.newRequestQueue(context)

        val json = JSONObject()
        try {

            Log.d("massaging", "sending request ")

            val notificationObj = JSONObject()
            notificationObj.put("title", title)
            notificationObj.put("body", body)
            //replace notification with data when went send data
            json.put("to", "/topics/${city}")
            json.put("data", notificationObj)
            json.put("priority","high" )

            val URL = "https://fcm.googleapis.com/fcm/send"
            val request: JsonObjectRequest = object : JsonObjectRequest(
                Request.Method.POST, URL,
                json,
                { response -> Log.d("MUR", "onResponse: ") },
                { error -> Log.d("MUR", "onError: " + error.networkResponse) }
            ) {

                override fun getHeaders(): MutableMap<String, String> {
                    val header = HashMap<String, String>()
                    header["content-type"] = "application/json"
                    header["authorization"] = "key=AAAAsIYvIJw:APA91bFHvzAGe4n8UOnk-l5Y381jEOWn-ocGLLGzjQhkMBvNy-rFUHUWxfp8WVqNFZwjMi5kn0XJoAXgbIvMFAko-cq10HcwySjP-_0BDKUDUCliEr6UBAyb9kvI4cz9o18k7BzIFcER"
                    return header
                }
            }
            mRequestQue.add(request)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }





}




/* private fun refreshfrag(fragment: Fragment) {


     val currentFragment =activity?.supportFragmentManager?.findFragmentById(R.id.container)
     val transaction = activity?.supportFragmentManager?.beginTransaction()
     transaction?.replace(R.id.container, fragment)
     transaction?.addToBackStack(null)

     if (currentFragment != null) {
         transaction?.detach(currentFragment)
     }
     if (currentFragment != null) {
         transaction?.attach(currentFragment)
     }
     transaction?.commit()
 } */
