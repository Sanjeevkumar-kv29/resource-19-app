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

public class helppostadapter extends RecyclerView.Adapter<helppostadapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> posttitle = new ArrayList<>();
    private ArrayList<String> publishername = new ArrayList<>();
    private ArrayList<String> publishercity = new ArrayList<>();
    private ArrayList<String> postbody = new ArrayList<>();
    private ArrayList<String> posttime = new ArrayList<>();

    private Context mContext;

    public helppostadapter(Context context,
                           ArrayList<String> mposttitle,
                           ArrayList<String> mpublishername,
                           ArrayList<String> mpublishercity,
                           ArrayList<String> mpostbody,
                           ArrayList<String> mposttime) {

        posttitle = mposttitle;
        publishername = mpublishername;
        publishercity = mpublishercity;
        postbody = mpostbody;
        posttime = mposttime;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.helpchatrvlyt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.tvposttitle.setText(posttitle.get(position));
        holder.tvpublishername.setText(publishername.get(position));
        holder.tvpublishercity.setText(publishercity.get(position));
        holder.tvpostbody.setText(postbody.get(position));
        holder.tvposttime.setText(posttime.get(position));


    }

    @Override
    public int getItemCount() {
        return postbody.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvposttitle;
        TextView tvpublishername;
        TextView tvpublishercity;
        TextView tvpostbody;
        TextView tvposttime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvposttitle = itemView.findViewById(R.id.posttitle);
            tvpublishername = itemView.findViewById(R.id.publishername);
            tvpublishercity = itemView.findViewById(R.id.publishercity);
            tvpostbody = itemView.findViewById(R.id.postbodytv);
            tvposttime = itemView.findViewById(R.id.posttimeadded);
        }
    }
}
