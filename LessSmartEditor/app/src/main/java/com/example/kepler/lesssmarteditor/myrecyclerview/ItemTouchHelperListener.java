package com.example.kepler.lesssmarteditor.myrecyclerview;

/**
 * Created by Kepler on 2017-05-22.
 */

public interface ItemTouchHelperListener {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemRemove(int position);
}
