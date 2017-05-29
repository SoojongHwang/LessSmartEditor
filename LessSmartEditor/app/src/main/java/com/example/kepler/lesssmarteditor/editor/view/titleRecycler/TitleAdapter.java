package com.example.kepler.lesssmarteditor.editor.view.titleRecycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.database.TitleWithId;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kepler on 2017-05-29.
 */

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.TitleViewHolder>{
    EditorPresenter mPresenter;
    List<TitleWithId> list;

    public TitleAdapter(EditorPresenter presenter,List<TitleWithId> list) {
        this.mPresenter = presenter;
        this.list = list;
    }

    @Override
    public TitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_title, parent, false);
        return new TitleViewHolder(view);

    }

    @Override
    public void onBindViewHolder(TitleViewHolder holder, int position) {
        holder.tv.setText(list.get(position).title);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.view_title_tv)
        TextView tv;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.getComponentListFromDatabase(list.get(getAdapterPosition()).id) ;
                }
            });
        }
    }
}
