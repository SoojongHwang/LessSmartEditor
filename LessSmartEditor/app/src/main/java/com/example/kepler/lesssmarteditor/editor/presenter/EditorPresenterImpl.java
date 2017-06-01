package com.example.kepler.lesssmarteditor.editor.presenter;

import android.content.Context;

import com.example.kepler.lesssmarteditor.editor.model.component.ComponentFactory;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.database.DatabaseManager;
import com.example.kepler.lesssmarteditor.editor.model.database.Title;
import com.example.kepler.lesssmarteditor.editor.view.EditorView;
import com.example.kepler.lesssmarteditor.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public class EditorPresenterImpl implements EditorPresenter{
    private EditorView mEditorView;
    private ComponentFactory mComponentFactory;
    private DatabaseManager mDatabaseManager;

    public EditorPresenterImpl(EditorView eView) {
        this.mEditorView = eView;
        this.mComponentFactory = new ComponentFactory();
        this.mDatabaseManager = new DatabaseManager((Context)eView);
    }

    @Override
    public void addText() {
        TextComponent tc = mComponentFactory.getTextInstance();
        mEditorView.addComponentToAdapter(tc);
    }

    @Override
    public void addImage(String path) {
        ImageComponent ic= mComponentFactory.getImageInstance(path);
        mEditorView.addComponentToAdapter(ic);
    }

    @Override
    public void addMap(Item item) {
        MapComponent mc = mComponentFactory.getMapInstance(item);
        mEditorView.addComponentToAdapter(mc);
    }

    @Override
    public void getTitles() {
        List<Title> list = mDatabaseManager.getTitleList();
        mEditorView.showTitles(list);
    }

    @Override
    public void getDocument(int titleId) {
        List<BaseComponent> document = mDatabaseManager.getDocumentFromDatabase(titleId);
        mEditorView.showDocument(document);
    }
    @Override
    public void saveDocumentsToDatabase(List<BaseComponent> list) {
        mDatabaseManager.saveToDatabase(list);
    }

    @Override
    public void deleteDocumentsFromDatabase(int id) {
        mDatabaseManager.deleteFromDatabase(id);
    }



}
