package com.hooyee.stickrecycler;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycler;
    private MyAdapter mAdapter;
    private FloatingActionButton mFloatButton;
    private FloatingActionButton mReduceFloatButton;
    List<ParentItem> data;
    ParentItem<ChildItem> p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new ArrayList<>();
        data.add(new ParentItem<>("ZZEEEAAA"));
        data.add(new ParentItem<>("BBB"));
        data.add(new ParentItem<>("CCC"));

        p = new ParentItem<>("DDD");
        List<ChildItem> childItems = new ArrayList<>();
        childItems.add(new ChildItem("cccc"));
        childItems.add(new ChildItem("aaaa"));
        p.setChildren(childItems);
        data.add(p);

        mAdapter = new MyAdapter(this, data);
        mRecycler = findViewById(R.id.stick_recycler);
        mFloatButton = findViewById(R.id.floatButton);
        mReduceFloatButton = findViewById(R.id.reduce_floatButton);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);

        mFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentItem<ChildItem> p = new ParentItem<>("EEE");
                p.setName("insert");
                List<ChildItem> childItems = new ArrayList<>();
                childItems.add(new ChildItem("cccc"));
                childItems.add(new ChildItem("aaaa"));
                p.setChildren(childItems);
                mAdapter.insertParent(p);

//                mAdapter.insertChild(new ChildItem("SDFD"), p);
            }
        });

        mReduceFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.remove(new ChildItem("cccc"));
            }
        });

    }
}
