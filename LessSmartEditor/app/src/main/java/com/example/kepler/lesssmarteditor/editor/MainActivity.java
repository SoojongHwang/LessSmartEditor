package com.example.kepler.lesssmarteditor.editor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.kepler.lesssmarteditor.R;
import com.example.kepler.lesssmarteditor.component.ImageComponent;
import com.example.kepler.lesssmarteditor.component.TextComponent;
import com.example.kepler.lesssmarteditor.component.Type;
import com.example.kepler.lesssmarteditor.editor.recyclerview.ComponentAdapter;
import com.example.kepler.lesssmarteditor.editor.recyclerview.ItemTouchHelperCallback;
import com.example.kepler.lesssmarteditor.navermap.MapActivity;

public class MainActivity extends AppCompatActivity {
    RecyclerView view;
    ComponentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void onClickedAddText(View v){
        TextComponent t = new TextComponent(Type.TEXT, "");
        adapter.addComponent(t);
        notifyToAdapter();
    }
    public void onClickedAddImage(View v){
        ImageComponent i = new ImageComponent(Type.IMAGE, R.drawable.d1);
        adapter.addComponent(i);
        notifyToAdapter();
    }

    public void onClickedAddMap(View v){    //삭제기능
        Intent intent = new Intent(this,MapActivity.class);
        startActivity(intent);
    }
    private void notifyToAdapter(){
        adapter.notifyItemInserted(adapter.getItemCount()-1);
        view.scrollToPosition(adapter.getItemCount()-1);
    }
    private void init(){
        view = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new ComponentAdapter();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(view);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
    }
}