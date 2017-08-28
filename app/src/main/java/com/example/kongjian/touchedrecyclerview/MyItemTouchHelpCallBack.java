package com.example.kongjian.touchedrecyclerview;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by user on 2017/8/28.
 */

public class MyItemTouchHelpCallBack extends ItemTouchHelper.Callback {

    private final ItemMoveListener itemMoveListener;

    public MyItemTouchHelpCallBack(ItemMoveListener itemMoveListener) {
        this.itemMoveListener = itemMoveListener;
    }

    //callback回调监听时先调用这个方法，判断当前是什么动作，例如判断当前动作的方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //方向有up down left right
        int up = ItemTouchHelper.UP;//0x0001
        int down = ItemTouchHelper.DOWN;//0x0010
        int left = ItemTouchHelper.LEFT;//0x0100
        int right = ItemTouchHelper.RIGHT;//0x1000
        //如果只判断上下方向就应该返回0x0011   up|down
        //同理只判断左右方向就应该返回0x1100   left|right
        //那系统怎么判断呢？比如用0x0011 & 0x0001 = 0x0001  0x0011 & 0x0010 = 0x0010 这样就判断出上下方向了
        //源码这里也提供了方法很方便，上面解释方便理解

        //监听上下方向的拖拽
        int dragFlags = up | down;
        //监听左右方向的滑动
        int swipeFlags = left | right;
        int flags = makeMovementFlags(dragFlags, swipeFlags);
        return flags;
    }

    //拖拽移动
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder srcHold, RecyclerView.ViewHolder targetHold) {
        //在此方法中不停调用adapter.notifyItemChanged方法刷新recyclerview中的数据
        if (srcHold.getItemViewType() != targetHold.getItemViewType()) {
            return false;
        }
        itemMoveListener.onItemMove(srcHold.getAdapterPosition(),targetHold.getAdapterPosition());
        return false;
    }
    //侧滑 滑动
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //监听侧滑1.调用adapter.notifyRemove
        itemMoveListener.onItemRemove(viewHolder.getAdapterPosition());
    }

    //选中某一项的时候可以做背景等处理
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //半透明
            viewHolder.itemView.setAlpha(0.6f);
        }
        super.onSelectedChanged(viewHolder, actionState);

    }
    //将那一项的背景等清除
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //恢复背景
        viewHolder.itemView.setAlpha(1f);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //dX 水平方向移动  往左为负  往右为正
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //透明度动画
            viewHolder.itemView.setAlpha(1-Math.abs(dX)/viewHolder.itemView.getWidth());
        }else{
            viewHolder.itemView.setAlpha(1f);
        }


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }
}
