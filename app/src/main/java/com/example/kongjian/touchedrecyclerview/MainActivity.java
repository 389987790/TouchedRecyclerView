package com.example.kongjian.touchedrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity implements StartDragListener,ItemMoveListener{
    private ArrayList<String> datas;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new RecyclerViewAdapter(datas,this);
        recyclerView.setAdapter(adapter);
        //条目帮助类
        itemTouchHelper = new ItemTouchHelper(new MyItemTouchHelpCallBack(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void getData() {
        datas = new ArrayList<>();
        for(int i=0;i<20;i++) {
            datas.add("lkadsjfasdfsadfsa   " + i);
        }
    }

    @Override
    public void onStartDragListener(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemMove(int fromPos, int toPos) {
        //1.数据交换 2.刷新
        Collections.swap(datas,fromPos,toPos);
        adapter.notifyItemMoved(fromPos,toPos);
    }

    @Override
    public void onItemRemove(int pos) {
        //1.数据移除 2.舒心
        datas.remove(pos);
        adapter.notifyItemRemoved(pos);
    }
}
