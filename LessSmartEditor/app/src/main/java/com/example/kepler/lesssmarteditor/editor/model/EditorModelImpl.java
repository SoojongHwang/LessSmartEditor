package com.example.kepler.lesssmarteditor.editor.model;

import android.content.Context;
import android.util.Log;

import com.example.kepler.lesssmarteditor.editor.model.component.ComponentFactory;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.database.DatabaseManager;
import com.example.kepler.lesssmarteditor.editor.model.database.Title;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenter;
import com.example.kepler.lesssmarteditor.map.model.Item;

import java.util.List;

public class EditorModelImpl implements EditorModel{
    enum DocumentState{
        EMPTY, UPDATED
    }
    private EditorPresenter mPresenter;

    private DatabaseManager mDatabaseManager;
    private DocumentState mDocumentState;
    private int mDocId;

    public EditorModelImpl(Context context, EditorPresenter mPresenter) {
        this.mPresenter = mPresenter;
        mDatabaseManager = new DatabaseManager(context, this);

        this.mDocumentState = DocumentState.EMPTY;
        this.mDocId = -1;
    }


    @Override
    public TextComponent getTextComponent() {
        setDocumentState(DocumentState.UPDATED);
        return ComponentFactory.getTextInstance();
    }

    @Override
    public ImageComponent getImageComponent(String path) {
        setDocumentState(DocumentState.UPDATED);
        return ComponentFactory.getImageInstance(path);
    }

    @Override
    public MapComponent getMapComponent(Item item) {
        setDocumentState(DocumentState.UPDATED);
        return ComponentFactory.getMapInstance(item);
    }

    @Override
    public List<Title> getTitles() {
        return mDatabaseManager.getTitleList();
    }

    @Override
    public List<BaseComponent> getDocument(int docId) {
        setDocumentId(docId);
        setDocumentState(DocumentState.UPDATED);

        return mDatabaseManager.getDocumentFromDatabase(docId);
    }

    @Override
    public void updateDocumentState() {
        switch(mDocumentState){
            case EMPTY:
                setDocumentState(DocumentState.UPDATED);
                break;
            case UPDATED:
                //nothing to do
                break;
        }
    }

    @Override
    public void updateDocumentForNew() {
        setDocumentId(-1);
        setDocumentState(DocumentState.EMPTY);
    }

    @Override
    public void saveDocument(List<BaseComponent> list) {
        switch(mDocumentState){
            case EMPTY:
                //nothing to do with database
                mPresenter.notifyToView("제목이나 본문을 입력하세요.");
                break;
            case UPDATED:
                //insert to database (list)
                mDatabaseManager.saveDocument(mDocId,list);
                mPresenter.notifyToView("저장되었습니다.");
                break;
        }
    }

    @Override
    public void deleteDocument() {
        if(mDocumentState == DocumentState.EMPTY){
            mPresenter.notifyToView("빈 문서는 삭제할 수 없습니다.");
            return;
        }
        mDatabaseManager.deleteFromDatabase(this.mDocId);

        setDocumentId(-1);
        setDocumentState(DocumentState.EMPTY);

        mPresenter.notifyToView("삭제되었습니다.");
    }

    @Override
    public void setDocumentId(int docId) {
        this.mDocId = docId;
    }

    private void setDocumentState(DocumentState state){
        Log.d("Com/MODEL", "Document state changed from "+ mDocumentState+" to "+state);
        this.mDocumentState =state;

        if(state == DocumentState.EMPTY)
            mPresenter.disableMenu();
        else
            mPresenter.enableMenu();
    }
}
