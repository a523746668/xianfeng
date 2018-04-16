package com.qingyii.hxtz.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView extends GridView{
	public MyGridView(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
        // TODO Auto-generated constructor stub 
    } 
    public MyGridView(Context context, AttributeSet attrs) { 
        super(context, attrs); 
        // TODO Auto-generated constructor stub 
    } 
    public MyGridView(Context context) { 
        super(context); 
        // TODO Auto-generated constructor stub 
    } 
    @Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
        // TODO Auto-generated method stub
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
        int heightSpec;

        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            // The great Android "hackatlon", the love, the magic.
            // The two leftmost bits in the height measure spec have
            // a special meaning, hence we can't use them to describe height.
            heightSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }
        else {
            // Any other height should be respected as is.
            heightSpec = heightMeasureSpec;
        }

        super.onMeasure(widthMeasureSpec, heightSpec);
 
    } 
}
