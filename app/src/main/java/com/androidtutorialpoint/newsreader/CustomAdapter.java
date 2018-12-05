package com.androidtutorialpoint.newsreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assad.newsreader.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.DataModel;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    onItemClickListener listener;
    private ArrayList<DataModel> dataSet;
    Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public CustomAdapter(Context context, ArrayList<DataModel> data) {
        this.context=context;
        this.dataSet = data;
    }


    public void onItemClickListener(onItemClickListener listener){
        this.listener=listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

       // view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getTitle());
        textViewVersion.setText(dataSet.get(listPosition).getDescription());


        if(dataSet.get(listPosition).getImageUrl()==null)
           holder.imageViewIcon.setVisibility(View.GONE);
        else {
            holder.imageViewIcon.setVisibility(View.VISIBLE);
            Picasso.with(context).load(dataSet.get(listPosition).getImageUrl()).into(imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItem(listPosition);
            }
        });
    }




    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}