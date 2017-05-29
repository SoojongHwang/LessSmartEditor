package com.example.kepler.lesssmarteditor.editor.presenter;

import android.net.Uri;

import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.map.model.Item;

import java.util.List;

/**
 * Created by Kepler on 2017-05-25.
 */

public interface EditorPresenter {
    void onTextAddSelected();
    void onImageAddSelected(String path);
    void onMapAddSelected(Item item);

    void onClickedLoadButton();
    void onClickedTitle(int id);
    void onClickedSaveButton(String title, List<BaseComponent> list);
}
