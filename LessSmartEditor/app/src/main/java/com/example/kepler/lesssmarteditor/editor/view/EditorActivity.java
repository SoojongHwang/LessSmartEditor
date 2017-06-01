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
import android.text.InputFilter;
import android.text.Spanned;
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
import com.example.kepler.lesssmarteditor.editor.model.database.Title;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenter;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenterImpl;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentAdapter;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ItemTouchHelperCallback;
import com.example.kepler.lesssmarteditor.editor.view.titleRecycler.ItemDivider;
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
    private AlertDialog mNewAlertDialog, mDeleteAlertDialog;

    private ComponentAdapter adapter;
    private EditorPresenter mPresenter;
    private LinearLayoutManager llm;
    private ItemTouchHelper itemTouchHelper;

    static final int REQ_CODE_IMAGE = 0;
    static final int REQ_CODE_MAP = 100;

    private InputFilter inputFilter;
    private InputFilter.LengthFilter lengthFilter;

    private TitleAdapter titleAdapter;
    @BindView(R.id.editor_title_recyclerView)
    RecyclerView title_view;
    @BindView(R.id.editor_open)
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

        makeSelectDialogs();
        makeInputFilters();
    }

    private void initRecyclerView() {
        adapter = new ComponentAdapter();
        eView.setAdapter(adapter);
        if (itemTouchHelper != null) {
            itemTouchHelper.attachToRecyclerView(null);
        }
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(eView);
        llm = new LinearLayoutManager(this);
        eView.setLayoutManager(llm);
    }

    private void initSlidingPage() {
        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);
        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);
    }

    @Override
    public void addComponentToAdapter(BaseComponent baseComponent) {
        adapter.addComponent(baseComponent);
        notifyToAdapter();
    }


    @Override
    public void showTitles(List<Title> list) {
        titleAdapter = new TitleAdapter(mPresenter, list);
        title_view.setLayoutManager(new LinearLayoutManager(this));
        title_view.setAdapter(titleAdapter);
        title_view.addItemDecoration(new ItemDivider(this));
    }

    @Override
    public void showDocument(List<BaseComponent> document) {
        adapter = new ComponentAdapter(document);
        eView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        itemTouchHelper.attachToRecyclerView(null);
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(eView);
        notifyToAdapter();

        page.startAnimation(translateRightAnim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                String path = data.getData().toString();
                mPresenter.addImage(path);
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
    public void onClickedAddComponent() {
        mSelectDialog.show();
    }

    @OnClick(R.id.btn_save)
    public void onClickedSave() {
        List<BaseComponent> list = adapter.getList();
        mPresenter.saveDocumentsToDatabase(list);
        Toast.makeText(this, " 글이 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_open:
                if (isPageOpen) {
                    page.startAnimation(translateRightAnim);
                } else {
                    mPresenter.getTitles();
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateLeftAnim);
                }
                break;
            case R.id.action_new:
                mNewAlertDialog.show();
                break;
            case R.id.action_delete:
                mDeleteAlertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void notifyToAdapter() {
        adapter.notifyItemInserted(adapter.getItemCount() - 1);
        eView.scrollToPosition(adapter.getItemCount() - 1);
    }

    private void makeSelectDialogs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] str = {"글", "그림", "지도"};
        builder
                .setTitle("컴포넌트 추가")
                .setNegativeButton("취소", null)
                .setItems(str, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mPresenter.addText();
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

        mSelectDialog = builder.create();

        builder = new AlertDialog.Builder(this);
        builder
                .setTitle("새로 작성하기")
                .setMessage("마지막 저장 이후의 내용들이 사라집니다.")
                .setNegativeButton("취소", null)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initRecyclerView();
                    }
                });
        mNewAlertDialog = builder.create();

        builder = new AlertDialog.Builder(this);
        builder
                .setTitle("메모 삭제")
                .setMessage("이 메모를 영구적으로 지웁니다.")
                .setNegativeButton("취소", null)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteDocumentsFromDatabase(0);
                        initRecyclerView();
                    }
                });
        mDeleteAlertDialog = builder.create();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("잠시만기다려주세요");
    }

    private void makeInputFilters() {
        inputFilter = new InputFilter() {
            private String blockCharacterSet = "~#^|$%'&*!;";

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (source != null && blockCharacterSet.contains(source)) {
                        return "";
                    }
                }
                return null;
            }
        };
        lengthFilter = new InputFilter.LengthFilter(20);
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
        //
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