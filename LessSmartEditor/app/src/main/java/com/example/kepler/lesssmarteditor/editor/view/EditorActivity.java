package com.example.kepler.lesssmarteditor.editor.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
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
import android.widget.ToggleButton;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.BaseComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.SpanInfo;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.SpanType;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.TextComponent;
import com.example.kepler.lesssmarteditor.editor.model.component.domain.Type;
import com.example.kepler.lesssmarteditor.editor.model.database.Title;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenter;
import com.example.kepler.lesssmarteditor.editor.presenter.EditorPresenterImpl;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.ComponentAdapter;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.listener.ItemTouchHelperCallback;
import com.example.kepler.lesssmarteditor.editor.view.componentrecyclerview.viewholder.TextViewHolder;
import com.example.kepler.lesssmarteditor.editor.view.titleRecycler.ItemDivider;
import com.example.kepler.lesssmarteditor.editor.view.titleRecycler.TitleAdapter;
import com.example.kepler.lesssmarteditor.map.model.Item;
import com.example.kepler.lesssmarteditor.map.view.MapActivity;

import java.util.ArrayList;
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
    //
    @BindView(R.id.tb1)
    ToggleButton b1_bold;
    @BindView(R.id.tb2)
    ToggleButton b2_italic;
    @BindView(R.id.tb3)
    ToggleButton b3_underline;
    private static MyEditText et;
    private Spannable eSpan;
    private MySpanChecker spanChecker;
    //
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
    @BindView(R.id.generalMode)
    LinearLayout generalMode;
    @BindView(R.id.spanMode)
    LinearLayout spanMode;

    Animation translateLeftAnim;
    Animation translateRightAnim;

    boolean isPageOpen = false;
    private Menu mMenu;

    InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mPresenter = new EditorPresenterImpl(this);
        spanChecker = new MySpanChecker();
        initRecyclerView();
        initSlidingPage();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        makeSelectDialogs();
        makeInputFilters();
    }

    private void initRecyclerView() {
        adapter = new ComponentAdapter(mPresenter);
        adapter.setMySpanListener(spanChecker);
        eView.setAdapter(adapter);
        eView.addOnItemTouchListener(new MyRecyclerViewTouchListener());
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
    public void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void enableSelectMenu() {
        mMenu.findItem(R.id.action_delete).setEnabled(true);
        mMenu.findItem(R.id.action_new).setEnabled(true);
    }

    @Override
    public void disableSelectMenu() {
        mMenu.findItem(R.id.action_delete).setEnabled(false);
        mMenu.findItem(R.id.action_new).setEnabled(false);
    }

    @Override
    public void addComponentToAdapter(BaseComponent baseComponent) {
        adapter.addComponent(baseComponent);
        notifyToAdapter();
        if (baseComponent.getType() == Type.TEXT) {
            View v = getCurrentFocus();
            imm.showSoftInput(v, 0);
        }
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
        adapter = new ComponentAdapter(mPresenter, document);
        adapter.setMySpanListener(spanChecker);
        eView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        itemTouchHelper.attachToRecyclerView(null);
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(eView);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
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
                    mPresenter.loadTitles();
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
                        mPresenter.notifyNewDocument();
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
                        mPresenter.deleteDocumentFromDatabase(0);
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
    @OnClick(R.id.tb1)
    public void onClickedBold(){
        eSpan = et.getText();
        int s = et.getSelectionStart();
        int e = et.getSelectionEnd();

        if (b1_bold.isChecked()) {
            if (s == e) {
                et.getText().insert(e++, " ");
                et.setSelection(s, e);
            }
            eSpan.setSpan(new StyleSpan(Typeface.BOLD), s, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            b1_bold.setChecked(true);
        } else {
            StyleSpan[] sArr = eSpan.getSpans(s, e, StyleSpan.class);
            for (StyleSpan ss : sArr) {
                if (ss.getStyle() == Typeface.BOLD) {
                    int spanStart = eSpan.getSpanStart(ss);
                    int spanEnd = eSpan.getSpanEnd(ss);

                    eSpan.removeSpan(ss);

                    if (s == e) {
                        et.getText().insert(e++, " ");
                        et.setSelection(s, e);
                    }
                    if (spanStart < s)
                        eSpan.setSpan(new StyleSpan(Typeface.BOLD), spanStart, s, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    if (spanEnd > e)
                        eSpan.setSpan(new StyleSpan(Typeface.BOLD), e, spanEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }
        }
        et.onSelectionChanged(et.getSelectionStart(), et.getSelectionEnd());
    }
    @OnClick(R.id.tb2)
    public void onClickedItalic(){
        eSpan = et.getText();
        int s = et.getSelectionStart();
        int e = et.getSelectionEnd();

        if (b2_italic.isChecked()) {
            if (s == e) {
                et.getText().insert(e++, " ");
                et.setSelection(s, e);
            }
            eSpan.setSpan(new StyleSpan(Typeface.ITALIC), s, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            b2_italic.setChecked(true);
        } else {
            StyleSpan[] sArr = eSpan.getSpans(s, e, StyleSpan.class);
            for (StyleSpan ss : sArr) {
                if (ss.getStyle() == Typeface.ITALIC) {
                    int spanStart = eSpan.getSpanStart(ss);
                    int spanEnd = eSpan.getSpanEnd(ss);

                    eSpan.removeSpan(ss);

                    if (s == e) {
                        et.getText().insert(e++, " ");
                        et.setSelection(s, e);
                    }
                    if (spanStart < s)
                        eSpan.setSpan(new StyleSpan(Typeface.ITALIC), spanStart, s, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    if (spanEnd > e)
                        eSpan.setSpan(new StyleSpan(Typeface.ITALIC), e, spanEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }
        }
        et.onSelectionChanged(et.getSelectionStart(), et.getSelectionEnd());
    }
    @OnClick(R.id.tb3)
    public void onClickedUnderline(){
        et.clearComposingText();
        eSpan = et.getText();
        int s = et.getSelectionStart();
        int e = et.getSelectionEnd();

        if (b3_underline.isChecked()) {
            if (s == e) {
                et.getText().insert(e++, " ");
                et.setSelection(s, e);
            }
            eSpan.setSpan(new UnderlineSpan(), s, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            b3_underline.setChecked(true);
        } else {
            UnderlineSpan[] uArr = eSpan.getSpans(s, e, UnderlineSpan.class);
            for (UnderlineSpan us : uArr) {
                int spanStart = eSpan.getSpanStart(us);
                int spanEnd = eSpan.getSpanEnd(us);

                eSpan.removeSpan(us);

                if (s == e) {
                    et.getText().insert(e++, " ");
                    et.setSelection(s, e);
                }
                if (spanStart < s)
                    eSpan.setSpan(new UnderlineSpan(), spanStart, s, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                if (spanEnd > e)
                    eSpan.setSpan(new UnderlineSpan(), e, spanEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        et.onSelectionChanged(et.getSelectionStart(), et.getSelectionEnd());
    }
    private void clearCurrentFocus(){
        View currentView = getCurrentFocus();
        imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
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

    public class MyRecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                View v = rv.findChildViewUnder(e.getX(), e.getY());
                if (v != null) {
                    int position = rv.getChildAdapterPosition(v);
                    int itemType = rv.getAdapter().getItemViewType(position);
                    Type type = Type.getType(itemType);
                    switch (type) {
                        case TEXT:
//                        case TITLE:
                            generalMode.setVisibility(View.GONE);
                            spanMode.setVisibility(View.VISIBLE);
                            TextViewHolder tvh = (TextViewHolder)rv.getChildViewHolder(v);
                            et = tvh.mEditText;

                            break;

                        case IMAGE:
                        case MAP:
                            generalMode.setVisibility(View.VISIBLE);
                            spanMode.setVisibility(View.GONE);
                            break;
                    }
                } else {
                    generalMode.setVisibility(View.VISIBLE);
                    spanMode.setVisibility(View.GONE);
                    clearCurrentFocus();
                    et.clearFocus();
                }
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public class MySpanChecker implements MyEditText.MySpanListener {
        @Override
        public void onSpanDetected(String bit) {
            boolean isBold = (int) (bit.charAt(0)) - 48 == 1 ? true : false;
            boolean isItalic = (int) (bit.charAt(1)) - 48 == 1 ? true : false;
            boolean isUnderlined = (int) (bit.charAt(2)) - 48 == 1 ? true : false;

            b1_bold.setChecked(isBold);
            b2_italic.setChecked(isItalic);
            b3_underline.setChecked(isUnderlined);
        }
    }
}