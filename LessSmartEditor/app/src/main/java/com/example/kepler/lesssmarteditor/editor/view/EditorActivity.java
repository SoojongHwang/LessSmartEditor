package com.example.kepler.lesssmarteditor.editor.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenter;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenterImpl;
import com.example.kepler.lesssmarteditor.editor.view.recyclerview.ComponentAdapter;
import com.example.kepler.lesssmarteditor.editor.view.recyclerview.ItemTouchHelperCallback;
import com.example.kepler.lesssmarteditor.map.model.Item;
import com.example.kepler.lesssmarteditor.map.view.MapActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends AppCompatActivity implements EditorView {
    @BindView(R.id.editor_recyclerView)
    RecyclerView eView;
    @BindView(R.id.btn_add_component)
    Button btn_addComponent;
    @BindView(R.id.btn_save)
    Button btn_save;

    EditorPresenter mPresenter;
    Dialog mDialog;

    ComponentAdapter adapter;
    final String[] str = {"글", "그림", "지도"};
    static final int REQ_CODE_IMAGE = 0;
    static final int REQ_CODE_MAP = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);
        init();
    }


    @Override
    public void addTextToAdapter(TextComponent textComponent) {
        adapter.addComponent(textComponent);
        notifyToAdapter();
    }

    @Override
    public void addImageToAdapter(ImageComponent imageComponent) {
        adapter.addComponent(imageComponent);
        notifyToAdapter();
    }

    @Override
    public void addMapToAdapter(MapComponent mapComponent) {
        adapter.addComponent(mapComponent);
        notifyToAdapter();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE_IMAGE){
            if(resultCode == Activity.RESULT_OK){
                mPresenter.addImage(data.getData());
            }
        }

        if (requestCode == REQ_CODE_MAP) {
            if (resultCode == Activity.RESULT_OK) {
                Item item = data.getParcelableExtra("data");
                mPresenter.addMap(item);
            }
        }
    }


    @OnClick(R.id.btn_add_component)
    public void onClickedAddComponent(View v) {
        mDialog.show();
    }

    private void init() {
        mPresenter = new EditorPresenterImpl(this);
        adapter = new ComponentAdapter();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(eView);
        eView.setLayoutManager(new LinearLayoutManager(this));
        eView.setAdapter(adapter);
        mDialog = makeDialog();
    }

    private void notifyToAdapter() {
        adapter.notifyItemInserted(adapter.getItemCount() - 1);
        eView.scrollToPosition(adapter.getItemCount() - 1);
    }

    private Dialog makeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("컴포넌트 추가")
                .setNegativeButton("취소", null)
                .setItems(str, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mPresenter.selectText();
                                break;
                            case 1:
                                Intent imageIntent = new Intent(Intent.ACTION_PICK);
                                imageIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                                imageIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(imageIntent, REQ_CODE_IMAGE);
                                break;
                            case 2:
                                Intent mapIntent = new Intent(EditorActivity.this, MapActivity.class);
                                startActivityForResult(mapIntent,REQ_CODE_MAP);
                                break;
                        }
                    }
                });

        return builder.create();
    }
}
