package com.example.begumpasinli.watchdatasending.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.view.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.begumpasinli.watchdatasending.R;

/**
 * Created by begumpasinli on 2.04.2017.
 */

public class StringListAdapter extends WearableRecyclerView.Adapter{
    private String[] data;
    private LayoutInflater mLayoutInflanter;
    private CustomOnClickListener listener;

    public StringListAdapter(Context context, String[] data,CustomOnClickListener mListener){

        mLayoutInflanter = LayoutInflater.from(context);
        this.data= data;
        listener = mListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new RecyclerView.ViewHolder(mLayoutInflanter.inflate(R.layout.string_list_adapter_view,parent,false)){
            @Override
            public String toString() { return super.toString();}
        };
    }
    @Override
    public void onBindViewHolder(WearableRecyclerView.ViewHolder viewHolder, final int i){
        TextView text = (TextView) viewHolder.itemView.findViewById(R.id.stringlist_item_text);
        text.setText(data[i]);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCustomClickListener(data[i]);

            }
        });

    }
    @Override
    public int getItemCount(){
        return data.length;
    }

    public interface  CustomOnClickListener{
        void onCustomClickListener(String itemText);
    }
}
