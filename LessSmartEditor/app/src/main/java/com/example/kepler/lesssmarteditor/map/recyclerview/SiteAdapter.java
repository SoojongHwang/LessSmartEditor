package com.example.kepler.lesssmarteditor.map.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.kepler.lesssmarteditor.map.api.Item;
import com.example.kepler.lesssmarteditor.map.api.SearchResult;

import java.util.List;

/**
 * Created by Kepler on 2017-05-23.
 */

public class SiteAdapter extends RecyclerView.Adapter<SiteViewHolder> {
    public List<Item> list;
    ItemClickListener mListener;

    public SiteAdapter(List<Item> list) {
        this.list = list;
    }

    @Override
    public SiteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SiteViewHolder.getInstance(parent, new OnItemClickListener());
    }

    @Override
    public void onBindViewHolder(SiteViewHolder holder, final int position) {
        holder.mListener.updatePosition(position);
        holder.tv_name.setText(getRefinedTitle(list.get(position).title));
        holder.tv_address.setText(list.get(position).roadAddress);
    }

    private String getRefinedTitle(String str) {
        str = str.replace("<b>", " ");
        return str.replace("</b>", " ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OnItemClickListener implements View.OnClickListener {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if(v instanceof ViewGroup)
                mListener.onRecyclerViewItemClicked(position,-1);
            else
                mListener.onRecyclerViewItemClicked(position,v.getId());
        }
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }

}
