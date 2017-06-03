package com.example.kepler.lesssmarteditor.editor.presenter;

import android.content.Context;

import com.example.kepler.lesssmarteditor.editor.model.EditorModel;
import com.example.kepler.lesssmarteditor.editor.model.EditorModelImpl;
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
    private EditorModel mEditorModel;

    public EditorPresenterImpl(EditorView eView) {
        this.mEditorView = eView;
        this.mEditorModel = new EditorModelImpl((Context)eView, this);
    }

    @Override
    public void notifyToView(String str) {
        mEditorView.showToast(str);
    }

    @Override
    public void enableMenu() {
        mEditorView.enableSelectMenu();
    }

    @Override
    public void disableMenu() {
        mEditorView.disableSelectMenu();
    }

    @Override
    public void addText() {
        TextComponent tc = mEditorModel.getTextComponent();
        mEditorView.addComponentToAdapter(tc);
    }

    @Override
    public void addImage(String path) {
        ImageComponent ic= mEditorModel.getImageComponent(path);
        mEditorView.addComponentToAdapter(ic);
    }

    @Override
    public void addMap(Item item) {
        MapComponent mc = mEditorModel.getMapComponent(item);
        mEditorView.addComponentToAdapter(mc);
    }

    @Override
    public void loadTitles() {
        List<Title> list = mEditorModel.getTitles();
        mEditorView.showTitles(list);
    }

    @Override
    public void loadDocument(int titleId) {
        List<BaseComponent> document = mEditorModel.getDocument(titleId);
        mEditorView.showDocument(document);
    }
    @Override
    public void saveDocumentsToDatabase(List<BaseComponent> list) {
        mEditorModel.saveDocument(list);
    }

    @Override
    public void deleteDocumentFromDatabase(int id) {
        mEditorModel.deleteDocument();
    }

    @Override
    public void notifyDocumentChanged() {
        mEditorModel.updateDocumentState();
    }

    @Override
    public void notifyNewDocument() {
        mEditorModel.updateDocumentForNew();
    }
}
