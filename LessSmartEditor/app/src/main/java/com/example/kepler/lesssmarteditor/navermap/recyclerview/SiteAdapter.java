package com.example.kepler.lesssmarteditor.navermap.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.kepler.lesssmarteditor.navermap.api.SearchResult;

import java.util.List;

/**
 * Created by Kepler on 2017-05-23.
 */

public class SiteAdapter extends RecyclerView.Adapter<SiteViewHolder>{
    List<SearchResult.Item> list;

    public SiteAdapter(List<SearchResult.Item> list) {
        this.list = list;
    }

    @Override
    public SiteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SiteViewHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(SiteViewHolder holder, int position) {
        holder.tv_name.setText(getRefinedTitle(list.get(position).title));
        holder.tv_address.setText(list.get(position).roadAddress);
    }

    private String getRefinedTitle(String str){
        str = str.replace("<b>"," ");
        return str.replace("</b>"," ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
