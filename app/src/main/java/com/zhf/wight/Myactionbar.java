package com.zhf.wight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qingyii.hxtz.R;


/**
 * Created by admin on 2017/7/7.
 */
//统一的标题栏
public class Myactionbar  extends RelativeLayout{
    private TextView title;
    private ImageView right,left;
    private LayoutInflater inflater;
    private View view;

    public Myactionbar(Context context) {
        super(context);
    }


    public Myactionbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        if(attrs!=null){
           TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.Myactionbar);
           String title=array.getText(R.styleable.Myactionbar_title).toString();
           int left=array.getResourceId(R.styleable.Myactionbar_limage,0);
           int right=array.getResourceId(R.styleable.Myactionbar_rimage,0) ;
            if(title!=null){
                showtitle(title);
            }
            if(left!=0){
                showleft(left);
            }
            if(right!=0){
                showright(right);
            }

        }
    }

    private void showright(int right) {
       this.right.setImageResource(right);
        this.right.setVisibility(VISIBLE);
    }

    private void showleft(int left) {
     this.left.setImageResource(left);
        this.left.setVisibility(VISIBLE);
    }

    private void showtitle(String title) {
         this.title.setText(title);
        this.title.setVisibility(VISIBLE);

            this.title.setTextColor(getResources().getColor(R.color.textColorWhite));

    }




    private void init(Context context) {
         if(view==null){
             view=inflater.from(context).inflate(R.layout.myactionbar,null);
             title= (TextView) view.findViewById(R.id.bar_title);
             right= (ImageView) view.findViewById(R.id.bar_ivright);
             left= (ImageView) view.findViewById(R.id.bar_ivleft);
             LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
             addView(view,params);

         }
    }

    //添加点击事件

    public void setleftonlick(OnClickListener ol){
      left.setOnClickListener(ol);
    }

   public void setrightonclick(OnClickListener ol){
      right.setOnClickListener(ol);
   }
}
