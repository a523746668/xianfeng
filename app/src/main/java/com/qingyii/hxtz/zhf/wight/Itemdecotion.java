package com.qingyii.hxtz.zhf.wight;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


//给Recyclerview添加分割线

public class Itemdecotion extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom=15;
    }
}
