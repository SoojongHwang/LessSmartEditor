package com.example.kepler.lesssmarteditor.editor.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    @BindView(R.id.editor_et_title)
    EditText et_title;

    private Dialog mSelectDialog;
    private ProgressDialog mProgressDialog;

    private ComponentAdapter adapter;
    private EditorPresenter mPresenter;
    private LinearLayoutManager llm;
    ItemTouchHelper itemTouchHelper;

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

    private void init() {
        mPresenter = new EditorPresenterImpl(this);
        initRecyclerView();
        initSlidingPage();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("잠시만기다려주세요");
        mSelectDialog = makeSelectDialog();
    }

    private void initRecyclerView() {
        adapter = new ComponentAdapter();
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(eView);
        llm = new LinearLayoutManager(this);
        eView.setLayoutManager(llm);
        eView.setAdapter(adapter);
    }

    private void initSlidingPage() {
        page = (LinearLayout) findViewById(R.id.editor_open);
        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);
        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);
    }

    @Override
    public void addSingleTextToAdapter(TextComponent textComponent) {
        adapter.addComponent(textComponent);
        notifyToAdapter();
    }

    @Override
    public void addSingleImageToAdapter(ImageComponent imageComponent) {
        adapter.addComponent(imageComponent);
        notifyToAdapter();
    }

    @Override
    public void addSingleMapToAdapter(MapComponent mapComponent) {
        adapter.addComponent(mapComponent);
        notifyToAdapter();
    }

    @Override
    public void setTitle(String title) {
        et_title.setText(title);
    }

    @Override
    public void dismissSlidingPage() {
        page.startAnimation(translateRightAnim);
    }


    @Override
    public void showTitles(List<TitleWithId> list) {
        title_view = (RecyclerView) findViewById(R.id.editor_title_recyclerView);
        titleAdapter = new TitleAdapter(mPresenter, list);
        title_view.setLayoutManager(new LinearLayoutManager(this));
        title_view.setAdapter(titleAdapter);
    }

    @Override
    public void showComponents(List<BaseComponent> cList) {
        adapter = new ComponentAdapter(cList);
        eView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        itemTouchHelper.attachToRecyclerView(null);
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(eView);
        notifyToAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                String path = data.getData().toString();
                mPresenter.onImageAddSelected(path);
            }
        }
        if (requestCode == REQ_CODE_MAP) {
            if (resultCode == Activity.RESULT_OK) {
                Item item = data.getParcelableExtra("data");
                mPresenter.onMapAddSelected(item);
            }
        }
    }

    @OnClick(R.id.btn_add_component)
    public void onClickedAddComponent(View v) {
        mSelectDialog.show();
    }

    @OnClick(R.id.btn_save)
    public void onClickedSave(View v) {
        String title = et_title.getText().toString();
        if(title.length()==0){
            title = "제목없는 글";
        }
        List<BaseComponent> list = adapter.getList();
        mPresenter.onClickedSaveButton(title, list);
        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.action_open:
                if (isPageOpen) {
                    page.startAnimation(translateRightAnim);
                } else {
                    mPresenter.onClickedLoadButton();
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateLeftAnim);
                }
                break;
            case R.id.action_new:
                break;
        }
                return super.onOptionsItemSelected(item);
    }

    private void notifyToAdapter() {
        adapter.notifyItemInserted(adapter.getItemCount() - 1);
        eView.scrollToPosition(adapter.getItemCount() - 1);
    }

    private Dialog makeSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("컴포넌트 추가")
                .setNegativeButton("취소", null)
                .setItems(str, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mPresenter.onTextAddSelected();
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
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