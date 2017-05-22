package com.example.kepler.lesssmarteditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.kepler.lesssmarteditor.component.ImageComponent;
import com.example.kepler.lesssmarteditor.component.TextComponent;
import com.example.kepler.lesssmarteditor.component.Type;
import com.example.kepler.lesssmarteditor.myrecyclerview.ItemTouchHelperCallback;
import com.example.kepler.lesssmarteditor.myrecyclerview.MyAdapter;

public class MainActivity extends AppCompatActivity {
    static int count=0;

    RecyclerView view;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (RecyclerView)findViewById(R.id.recyclerView);
        init();
    }

    public void onClickedAddText(View v){
        TextComponent t = new TextComponent(count, Type.TEXT, "");
        adapter.addComponent(t);
        notifyToAdapter();
    }
    public void onClickedAddImage(View v){
        ImageComponent i = new ImageComponent(count, Type.IMAGE, R.drawable.d1);
        adapter.addComponent(i);
        notifyToAdapter();
    }
    public void onClickedAddMap(View v){    //삭제기능
        init();
    }
    private void notifyToAdapter(){
        Toast.makeText(getApplicationContext(),"아이템 개수: "+adapter.getItemCount(),Toast.LENGTH_SHORT).show();
        adapter.notifyItemInserted(adapter.getItemCount()-1);
        view.scrollToPosition(adapter.getItemCount()-1);
    }
    private void init(){
        adapter = new MyAdapter();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(view);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
    }
}