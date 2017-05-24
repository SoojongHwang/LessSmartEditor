package com.example.kepler.lesssmarteditor.map.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kepler.lesssmarteditor.R;

/**
 * Created by Kepler on 2017-05-23.
 */

public class SiteViewHolder extends RecyclerView.ViewHolder{
    TextView tv_name;
    TextView tv_address;
    Button btn_add;

    SiteAdapter.OnItemClickListener mListener;

    static public SiteViewHolder getInstance(ViewGroup parent, SiteAdapter.OnItemClickListener listener){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_site, parent, false);
        return new SiteViewHolder(view, listener);
    }

    private SiteViewHolder(View itemView, SiteAdapter.OnItemClickListener listener) {
        super(itemView);
        mListener =listener;
        tv_name = (TextView)itemView.findViewById(R.id.tv_name);
        tv_address = (TextView)itemView.findViewById(R.id.tv_address);
        btn_add = (Button)itemView.findViewById(R.id.btn_add);
        itemView.setOnClickListener(mListener);
        btn_add.setOnClickListener(mListener);
    }

    static class OnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

        }
    }

}
