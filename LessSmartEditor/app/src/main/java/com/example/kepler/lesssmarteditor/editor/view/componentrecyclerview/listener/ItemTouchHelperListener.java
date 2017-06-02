package com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.listener;

/**
 * Created by Kepler on 2017-06-02.
 */

public interface ItemTouchHelperListener {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemRemove(int position);
}