package com.example.kongjian.touchedrecyclerview;

/**
 * Created by user on 2017/8/28.
 * 该接口方法在拖拽的时候调用
 */

public interface ItemMoveListener {
    public void onItemMove(int fromPos, int toPos);

    public void onItemRemove(int pos);
}
