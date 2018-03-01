package com.qingyii.hxt.circle;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 去除滑动属性
 *
 * @author Lee
 */
public class NonScrollGridView extends GridView {

    public NonScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setSelector(android.R.color.transparent);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}  
