package com.example.kepler.lesssmarteditor.editor.model;

import com.example.kepler.lesssmarteditor.editor.model.component.ComponentManager;
import com.example.kepler.lesssmarteditor.editor.model.database.DBManager;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenter;

/**
 * Created by Kepler on 2017-05-27.
 */

public class Manager {
    EditorPresenter mPresenter;

    DBManager mDbManager;
    ComponentManager mComponentManager;


    public Manager(EditorPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }


}
