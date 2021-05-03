package com.example.resource_19.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resource_19.R
import com.example.resource_19.dataClasses.HelpPostDetailsDataClass

class Helppostadapter(val context: Context?, val dataArray: ArrayList<HelpPostDetailsDataClass>) :
    RecyclerView.Adapter<Helppostadapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.posttitle)
        val name: TextView = view.findViewById(R.id.publishername)
        val city: TextView = view.findViewById(R.id.publishercity)
        val body: TextView = view.findViewById(R.id.postbodytv)
        val timeadded: TextView = view.findViewById(R.id.posttimeadded)

    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.helpchatrvlyt, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataArray[position]

        holder.title.text = item.posttitlename
        holder.name.text = item.publishername
        holder.city.text = item.publishercityname
        holder.body.text = item.postbody
        holder.timeadded.text = item.posttimeadded

        /*holder.btnVerify.setOnClickListener {
            context.startActivity(
                Intent(context, VerificationActivity::class.java)
                    .putExtra("id", item.id)
                    .putExtra("name", item.Name)
                    .putExtra("city", item.city)
                    .putExtra("state", item.state)
                    .putExtra("provider", item.provider)
                    .putExtra("address", item.address)
                    .putExtra("contact", item.contact)
                    .putExtra("time", item.time)
                    .putExtra("more", item.more)
            )
        }*/
    }
}