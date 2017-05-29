package com.example.kepler.lesssmarteditor.editor.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.ImageComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.MapComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.database.TitleWithId;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenter;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenterImpl;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentAdapter;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ItemTouchHelperCallback;
import com.example.kepler.lesssmarteditor.editor.view.titleRecycler.TitleAdapter;
import com.example.kepler.lesssmarteditor.map.model.Item;
import com.example.kepler.lesssmarteditor.map.view.MapActivity;

import java.util.List;

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

    private Dialog mSelectDialog;
    private ProgressDialog mProgressDialog;

    private ComponentAdapter adapter;
    private EditorPresenter mPresenter;


    final String[] str = {"글", "그림", "지도"};
    static final int REQ_CODE_IMAGE = 0;
    static final int REQ_CODE_MAP = 100;


    private TitleAdapter titleAdapter;
    private RecyclerView title_view;
    LinearLayout page;
    Animation translateLeftAnim;
    Animation translateRightAnim;
    boolean isPageOpen = false;

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
    public void showComponents(List<BaseComponent> cList) {
        adapter = new ComponentAdapter(cList);
        eView.setLayoutManager(new LinearLayoutManager(this));
        eView.setAdapter(adapter);
    }

    @Override
    public void showTitle(List<TitleWithId> list) {
        title_view = (RecyclerView)findViewById(R.id.editor_title_recyclerView);
        titleAdapter = new TitleAdapter(mPresenter, list);
        title_view.setLayoutManager(new LinearLayoutManager(this));
        title_view.setAdapter(titleAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
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
        mSelectDialog.show();
    }
    @OnClick(R.id.btn_save)
    public void onClickedSaveComponent(View v){
        List<BaseComponent> list = adapter.getList();
        mPresenter.saveComponentListToDatabase("hi",list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_open){
            if (isPageOpen) {
                page.startAnimation(translateRightAnim);

            } else {
                page.setVisibility(View.VISIBLE);
                page.startAnimation(translateLeftAnim);
                mPresenter.getTitleListFromDatabase();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        mPresenter = new EditorPresenterImpl(this);
        adapter = new ComponentAdapter();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(eView);
        eView.setLayoutManager(new LinearLayoutManager(this));
        eView.setAdapter(adapter);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("잠시만기다려주세요");
        mSelectDialog = makeDialog();


        page = (LinearLayout) findViewById(R.id.editor_open);

        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);
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
                                startActivityForResult(mapIntent, REQ_CODE_MAP);
                                break;
                        }
                    }
                });

        return builder.create();
    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener {
        public void onAnimationEnd(Animation animation) {
            if (isPageOpen) {
                page.setVisibility(View.INVISIBLE);
                isPageOpen = false;
            } else {
                isPageOpen = true;
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }
}