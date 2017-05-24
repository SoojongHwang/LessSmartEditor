package com.example.kepler.lesssmarteditor.map.z_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.map.api.Item;
import com.example.kepler.lesssmarteditor.map.z_view.Z_OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kepler on 2017-05-24.
 */

public class Z_SiteAdapter extends RecyclerView.Adapter<Z_SiteAdapter.SiteViewHolder> implements Z_SiteAdapterModel,Z_SiteAdapterView {
    private final Context mContext;
    private List<Item> mList;
    Z_OnItemClickListener mListener;

    public Z_SiteAdapter(Context context, List<Item> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public SiteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_site,parent,false);
        return new SiteViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(SiteViewHolder holder, int position) {
        holder.tv_name.setText(getItem(position).title);
        holder.tv_address.setText(getItem(position).roadAddress);
    }

    @Override
    public int getItemCount() {
        return getListSize();
    }

    @Override
    public void setSiteList(List<Item> list) {
        this.mList = list;
    }

    @Override
    public int getListSize() {
        return mList.size();
    }

    @Override
    public Item getItem(int position) {
        return null;
    }
    public void setOnItemClickListener(Z_OnItemClickListener listener){
        this.mListener = listener;
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    static class SiteViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_address)
        TextView tv_address;
        @BindView(R.id.btn_add)
        Button btn_add;

        public SiteViewHolder(View itemView, Z_OnItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(listener);
            btn_add.setOnClickListener(listener);
        }
    }
}
