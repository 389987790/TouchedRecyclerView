package com.example.kongjian.touchedrecyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by user on 2017/8/28.
 * 自定义一个拖拽的接口
 */

public interface StartDragListener {
    public void onStartDragListener(RecyclerView.ViewHolder viewHolder);
}
