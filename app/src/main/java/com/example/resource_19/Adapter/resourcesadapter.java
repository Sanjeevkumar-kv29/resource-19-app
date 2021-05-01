package com.example.resource_19.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.resource_19.R;

import java.util.ArrayList;


/**
 * Created by User on 2/12/2018.
 */

public class resourcesadapter extends RecyclerView.Adapter<resourcesadapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> resourceName = new ArrayList<>();
    private ArrayList<String> cityname = new ArrayList<>();
    private ArrayList<String> statename = new ArrayList<>();
    private ArrayList<String> providername = new ArrayList<>();

    private ArrayList<String> providercontact = new ArrayList<>();
    private ArrayList<String> provideraddress = new ArrayList<>();
    private ArrayList<String> verifiedby = new ArrayList<>();
    private ArrayList<String> moredetail = new ArrayList<>();
    private ArrayList<String> timeadded = new ArrayList<>();

    private Context mContext;

    public resourcesadapter(Context context,
                            ArrayList<String> mresourceName,
                            ArrayList<String> mcityname,
                            ArrayList<String> mstatename,
                            ArrayList<String> mprovidername,
                            ArrayList<String> mprovidercontact,
                            ArrayList<String> mprovideraddress,
                            ArrayList<String> mverifiedby,
                            ArrayList<String> mmoredetails,
                            ArrayList<String> mtimeadded) {

        resourceName = mresourceName;
        cityname = mcityname;
        statename = mstatename;
        providername = mprovidername;
        providercontact = mprovidercontact;
        provideraddress = mprovideraddress;
        verifiedby = mverifiedby;
        moredetail = mmoredetails;
        timeadded = mtimeadded;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainrvlyt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.tvres.setText(resourceName.get(position));
        holder.tvcity.setText(cityname.get(position));
        holder.tvstate.setText(statename.get(position));
        holder.tvprovidername.setText(providername.get(position));
        holder.tvprovideraddress.setText(provideraddress.get(position));
        holder.tvprovidercontact.setText(providercontact.get(position));
        holder.tvverifierdetail.setText(verifiedby.get(position));
        holder.tvtimeadded.setText(timeadded.get(position));
        holder.tvmoredetails.setText(moredetail.get(position));


    }

    @Override
    public int getItemCount() {
        return resourceName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvres;
        TextView tvcity;
        TextView tvstate;
        TextView tvprovidername;
        TextView tvprovidercontact;
        TextView tvprovideraddress;
        TextView tvverifierdetail;
        TextView tvtimeadded;
        TextView tvmoredetails;

        public ViewHolder(View itemView) {
            super(itemView);
            tvres = itemView.findViewById(R.id.mainrvresourcename);
            tvcity = itemView.findViewById(R.id.mainrvcity);
            tvstate = itemView.findViewById(R.id.mainrvstate);
            tvprovidername = itemView.findViewById(R.id.mainrvprovidersname);
            tvprovidercontact = itemView.findViewById(R.id.mainrvproviderscontact);
            tvprovideraddress = itemView.findViewById(R.id.mainrvprovidersaddress);
            tvverifierdetail = itemView.findViewById(R.id.mainrvvarifiedby);
            tvtimeadded = itemView.findViewById(R.id.mainrvtimeadded);
            tvmoredetails = itemView.findViewById(R.id.mainrvmoredetail);
        }
    }
}
