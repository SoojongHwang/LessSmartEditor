package com.example.kepler.lesssmarteditor.editor.presenter;

import android.content.Context;
import android.net.Uri;

import com.example.kepler.lesssmarteditor.editor.model.component.ComponentManager;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.database.DatabaseManager;
import com.example.kepler.lesssmarteditor.editor.model.database.TitleWithId;
import com.example.kepler.lesssmarteditor.editor.view.EditorView;
import com.example.kepler.lesssmarteditor.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public class EditorPresenterImpl implements EditorPresenter{
    private EditorView mEditorView;
    private ComponentManager mComponentManager;
    private DatabaseManager mDatabaseManager;

    public EditorPresenterImpl(EditorView eView) {
        this.mEditorView = eView;
        this.mComponentManager = new ComponentManager();
        this.mDatabaseManager = new DatabaseManager((Context)eView);
    }

    @Override
    public void selectText() {
        TextComponent tc = mComponentManager.getTextInstance();
        mEditorView.addTextToAdapter(tc);
    }

    @Override
    public void addImage(Uri uri) {
        ImageComponent ic= mComponentManager.getImageInstance(uri);
        mEditorView.addImageToAdapter(ic);
    }


    @Override
    public void addMap(Item item) {
        MapComponent mc = mComponentManager.getMapInstance(item);
        mEditorView.addMapToAdapter(mc);
    }

    @Override
    public void getTitleListFromDatabase() {
        List<TitleWithId> list = mDatabaseManager.getTitleList();
        mEditorView.showTitle(list);
    }

    @Override
    public void getComponentListFromDatabase(int id) {
        List<BaseComponent> list = mDatabaseManager.getComponentList(id);
        mEditorView.showComponents(list);
    }

    @Override
    public void saveComponentListToDatabase(String title, List<BaseComponent> list) {
        mDatabaseManager.saveToDatabase(title, list);
    }

}
