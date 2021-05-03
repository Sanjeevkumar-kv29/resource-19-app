package com.example.resource_19.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resource_19.R
import com.example.resource_19.dataClasses.ResourceDataClass

class ResourceAdapter(val context: Context, val data: ArrayList<ResourceDataClass>) :
    RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.mainrvresourcename)
        val city: TextView = view.findViewById(R.id.mainrvcity)
        val state: TextView = view.findViewById(R.id.mainrvstate)
        val provider: TextView = view.findViewById(R.id.mainrvprovidersname)
        val contact: TextView = view.findViewById(R.id.mainrvproviderscontact)
        val address: TextView = view.findViewById(R.id.mainrvprovidersaddress)
        val time: TextView = view.findViewById(R.id.mainrvtimeadded)
        val more: TextView = view.findViewById(R.id.mainrvmoredetail)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mainrvlyt, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.name.text = item.Name
        holder.city.text = item.city
        holder.state.text = item.state
        holder.provider.text = item.provider
        holder.contact.text = item.contact
        holder.address.text = item.address
        holder.more.text = item.more
        holder.time.text = item.time


    }
}