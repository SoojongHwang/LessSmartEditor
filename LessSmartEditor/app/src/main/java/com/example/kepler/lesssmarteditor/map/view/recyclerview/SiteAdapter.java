package com.example.kepler.lesssmarteditor.map.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.map.model.Item;
import com.example.kepler.lesssmarteditor.map.presenter.MapPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kepler on 2017-05-24.
 */

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.SiteViewHolder> {
    MapPresenter mPresenter;
    List<Item> mList;

    public SiteAdapter(MapPresenter presenter, List<Item> list) {
        this.mPresenter = presenter;
        this.mList = list;
    }

    @Override
    public SiteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_site, parent, false);
        return new SiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SiteViewHolder holder, int position) {
        holder.tv_name.setText(getRefinedTitle(mList.get(position).title));
        holder.tv_address.setText(mList.get(position).roadAddress);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private String getRefinedTitle(String str) {
        str = str.replace("<b>", " ");
        return str.replace("</b>", " ");
    }

    public class SiteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_address)
        TextView tv_address;
        @BindView(R.id.btn_add)
        Button btn_add;

        public SiteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.onClickedSite(mList.get(getAdapterPosition()));
                }
            });
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.onClickedSiteAddButton(mList.get(getAdapterPosition()));
                }
            });
        }
    }

}
