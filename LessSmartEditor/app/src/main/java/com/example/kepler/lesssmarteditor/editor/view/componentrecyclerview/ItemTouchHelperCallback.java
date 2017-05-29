package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Kepler on 2017-05-22.
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    ItemTouchHelperListener listener;

    public ItemTouchHelperCallback(ItemTouchHelperListener listener){
        this.listener = listener;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlags, swipeFlags);
    }


    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        return listener.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onItemRemove(viewHolder.getAdapterPosition());
    }

    /**
     * Created by Kepler on 2017-05-22.
     */

    public interface ItemTouchHelperListener {
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemRemove(int position);
    }
}
