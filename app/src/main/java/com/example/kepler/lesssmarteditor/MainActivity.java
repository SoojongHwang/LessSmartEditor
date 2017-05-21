package com.example.kepler.lesssmarteditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.kepler.lesssmarteditor.component.ImageComponent;
import com.example.kepler.lesssmarteditor.component.TextComponent;
import com.example.kepler.lesssmarteditor.component.Type;
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
        adapter = new MyAdapter();

        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);

    }

    public void onClickedAddText(View v){
        TextComponent t = new TextComponent(count, Type.TEXT, "aaa");
        adapter.addComponent(t);
        adapter.notifyDataSetChanged();
    }
    public void onClickedAddImage(View v){
        ImageComponent i = new ImageComponent(count, Type.IMAGE, R.drawable.d1);
        adapter.addComponent(i);
        adapter.notifyDataSetChanged();
    }
}