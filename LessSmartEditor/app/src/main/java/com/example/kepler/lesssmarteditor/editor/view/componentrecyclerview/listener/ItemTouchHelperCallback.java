package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.listener;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.TitleComponent;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder.TitleViewHolder;

/**
 * Created by Kepler on 2017-05-22.
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    ItemTouchHelperListener listener;

    public ItemTouchHelperCallback(ItemTouchHelperListener listener) {
        this.listener = listener;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        if (viewHolder instanceof TitleViewHolder)
            return 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (target instanceof TitleViewHolder) {
            return false;
        }
        return listener.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onItemRemove(viewHolder.getAdapterPosition());
    }

    /**
     * Created by Kepler on 2017-05-22.
     */
}
