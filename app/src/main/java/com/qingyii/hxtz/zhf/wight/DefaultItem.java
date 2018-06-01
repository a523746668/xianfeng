package com.qingyii.hxtz.zhf.wight;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhf on 2017/10/12.
 */

public class DefaultItem extends RecyclerView.ItemDecoration  {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom=4;
    }
}
