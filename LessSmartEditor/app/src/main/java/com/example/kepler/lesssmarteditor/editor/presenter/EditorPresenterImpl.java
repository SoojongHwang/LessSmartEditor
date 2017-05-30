package com.example.kepler.lesssmarteditor.editor.presenter;

import android.content.Context;
import android.net.Uri;

import com.example.kepler.lesssmarteditor.editor.model.component.ComponentManager;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.database.ContentWithTitle;
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
    public void onTextAddSelected() {
        TextComponent tc = mComponentManager.getTextInstance();
        mEditorView.addSingleTextToAdapter(tc);
    }

    @Override
    public void onImageAddSelected(String path) {
        ImageComponent ic= mComponentManager.getImageInstance(path);
        mEditorView.addSingleImageToAdapter(ic);
    }

    @Override
    public void onMapAddSelected(Item item) {
        MapComponent mc = mComponentManager.getMapInstance(item);
        mEditorView.addSingleMapToAdapter(mc);
    }

    @Override
    public void onClickedLoadButton() {
        List<TitleWithId> list = mDatabaseManager.getTitleList();
        mEditorView.showTitles(list);
    }

    @Override
    public void onClickedTitle(int titleId) {
        ContentWithTitle cwt = mDatabaseManager.getComponentList(titleId);
        mEditorView.showComponents(titleId, cwt.list);
        mEditorView.setTitle(cwt.title);
        mEditorView.dismissSlidingPage();
    }

    @Override
    public void onClickedSaveButton(int id, String title, List<BaseComponent> list) {
        mDatabaseManager.saveToDatabase(id, title, list);
    }

}
