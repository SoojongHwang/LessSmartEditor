package com.example.kepler.lesssmarteditor.editor.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
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
    private boolean[] spanLights;
    private Toast toast;
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

    static public InputFilter[] filters;

    private TitleAdapter titleAdapter;
    @BindView(R.id.editor_title_recyclerView)
    RecyclerView title_view;
    @BindView(R.id.editor_open)
    LinearLayout page;
    @BindView(R.id.generalMode)
    LinearLayout generalMode;
    @BindView(R.id.spanMode)
    LinearLayout spanMode;

    private Animation translateLeftAnim;
    private Animation translateRightAnim;

    private boolean isPageOpen = false;
    private Menu mMenu;

    private InputMethodManager imm;


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
        spanLights = new boolean[3];
        initRecyclerView();
        initSlidingPage();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        makeSelectDialogs();
        makeInputFilters();
        toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
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
        toast.setText(str);
        toast.show();
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
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_IMAGE:
                    String path = data.getData().toString();
                    mPresenter.addImage(path);
                    break;
                case REQ_CODE_MAP:
                    Item item = data.getParcelableExtra("data");
                    mPresenter.addMap(item);
                    break;
                case 333:
            }
        }
    }

    @OnClick(R.id.btn_add_component)
    public void onClickedAddComponent() {
        mSelectDialog.show();
    }

    @OnClick(R.id.btn_save)
    public void onClickedSave() {
        if (et != null)
            et.clearFocus();
        clearCurrentFocus();
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
                clearCurrentFocus();
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
        filters = new InputFilter[2];
        filters[0] = new InputFilter() {
            private String blockCharacterSet = "~#^|$%'&*!;";

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (source != null && blockCharacterSet.contains(source)) {
                        toast.setText(source+" 는 입력할 수 없습니다.");
                        toast.show();
                        return "";
                    }
                }
                return null;
            }
        };
        filters[1] = new InputFilter(){
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                final int mMax = 15;
                int keep = mMax - (dest.length() - (dend - dstart));
                if (keep <= 0) {
                    toast.setText("15자를 초과하면 안됩니다.");
                    toast.show();
                    return "";
                } else if (keep >= end - start) {
                    return null; // keep original
                } else {
                    keep += start;
                    if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                        --keep;
                        if (keep == start) {
                            return "";
                        }
                    }
                    return source.subSequence(start, keep);
                }

            }
        };
    }

    @OnClick(R.id.tb1)
    public void onClickedBold() {
        if (b1_bold.isChecked()) {
            applySpanToEditText(SpanType.BOLD);
        } else {
            clearSpanFromEditText(SpanType.BOLD);
        }
    }

    @OnClick(R.id.tb2)
    public void onClickedItalic() {
        if (b2_italic.isChecked()) {
            applySpanToEditText(SpanType.ITALIC);
        } else {
            clearSpanFromEditText(SpanType.ITALIC);
        }
    }

    @OnClick(R.id.tb3)
    public void onClickedUnderline() {
        if (b3_underline.isChecked()) {
            applySpanToEditText(SpanType.UNDERLINE);
        } else {
            clearSpanFromEditText(SpanType.UNDERLINE);
        }
    }
    private void applySpanToEditText(SpanType type) {
        et.clearComposingText();
        eSpan = et.getText();
        int s = et.getSelectionStart();
        int e = et.getSelectionEnd();

        if (s == e) {
            et.getText().insert(e++, " ");
            et.setSelection(s, e);
        }
        switch (type) {
            case BOLD:
                eSpan.setSpan(new StyleSpan(Typeface.BOLD), s, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                break;
            case ITALIC:
                eSpan.setSpan(new StyleSpan(Typeface.ITALIC), s, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                break;
            case UNDERLINE:
                eSpan.setSpan(new MyUnderlineSpan(), s, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                break;
        }
    }

    private void clearSpanFromEditText(SpanType type) {
        et.clearComposingText();
        eSpan = et.getText();
        int s = et.getSelectionStart();
        int e = et.getSelectionEnd();

        switch (type) {
            case BOLD:
            case ITALIC:
                StyleSpan[] sArr = eSpan.getSpans(s, e, StyleSpan.class);
                for (StyleSpan ss : sArr) {
                    if (ss.getStyle() == type.getTypeValue()) {
                        int spanStart = eSpan.getSpanStart(ss);
                        int spanEnd = eSpan.getSpanEnd(ss);

                        eSpan.removeSpan(ss);

                        if (s == e) {
                            et.getText().insert(e++, " ");
                            et.setSelection(s, e);
                        }
                        if (spanStart < s)
                            eSpan.setSpan(new StyleSpan(type.getTypeValue()), spanStart, s, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                        if (spanEnd > e)
                            eSpan.setSpan(new StyleSpan(type.getTypeValue()), e, spanEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    }
                }
                break;
            case UNDERLINE:
                MyUnderlineSpan[] uArr = eSpan.getSpans(s, e, MyUnderlineSpan.class);
                for (MyUnderlineSpan us : uArr) {
                    int spanStart = eSpan.getSpanStart(us);
                    int spanEnd = eSpan.getSpanEnd(us);

                    eSpan.removeSpan(us);

                    if (s == e) {
                        et.getText().insert(e++, " ");
                        et.setSelection(s, e);
                    }
                    if (spanStart < s)
                        eSpan.setSpan(new MyUnderlineSpan(), spanStart, s, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    if (spanEnd > e)
                        eSpan.setSpan(new MyUnderlineSpan(), e, spanEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                }
                break;
        }
        et.onSelectionChanged(et.getSelectionStart(), et.getSelectionEnd());
    }


    private void clearCurrentFocus() {
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
                            generalMode.setVisibility(View.GONE);
                            spanMode.setVisibility(View.VISIBLE);
                            TextViewHolder tvh = (TextViewHolder) rv.getChildViewHolder(v);
                            et = tvh.mEditText;
                            et.setCurrentPos(position);
                            break;

                        case IMAGE:
                        case MAP:
                            generalMode.setVisibility(View.VISIBLE);
                            spanMode.setVisibility(View.GONE);
                            break;
                        case TITLE:
                            break;
                    }
                } else {
                    generalMode.setVisibility(View.VISIBLE);
                    spanMode.setVisibility(View.GONE);
                    clearCurrentFocus();
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

    public class MySpanChecker implements MyEditText.MySpanDetectListener {
        @Override
        public void onSpanDetected(boolean[] bit) {
            spanLights = bit;
            b1_bold.setChecked(bit[0]);
            b2_italic.setChecked(bit[1]);
            b3_underline.setChecked(bit[2]);
        }
    }
}