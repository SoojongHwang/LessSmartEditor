package com.example.kepler.lesssmarteditor.editor.presenter;

import android.net.Uri;

import com.example.kepler.lesssmarteditor.editor.model.ComponentManager;
import com.example.kepler.lesssmarteditor.editor.model.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.view.EditorView;
import com.example.kepler.lesssmarteditor.map.model.Item;

/**
 * Created by Kepler on 2017-05-25.
 */

public class EditorPresenterImpl implements EditorPresenter{
    EditorView mEditorView;
    ComponentManager mManager;
    public EditorPresenterImpl(EditorView eView) {
        this.mEditorView = eView;
        mManager = new ComponentManager();
    }

    @Override
    public void selectText() {
        TextComponent tc = mManager.getTextInstance();
        mEditorView.addTextToAdapter(tc);
    }

    @Override
    public void addImage(Uri uri) {
        ImageComponent ic= mManager.getImageInstance(uri);
        mEditorView.addImageToAdapter(ic);
    }


    @Override
    public void addMap(Item item) {
        MapComponent mc = mManager.getMapInstance(item);
        mEditorView.addMapToAdapter(mc);
    }
}