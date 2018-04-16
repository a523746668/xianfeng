package com.zhf.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxtz.base.adapter.BaseRecyclerViewHolder;
import com.zhf.ImgandVideoActivity;
import com.zhf.bean.SendTask;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zhf on 2017/12/8.
 */

public class PicandVideoAdapter extends BaseRecyclerAdapter<SendTask> {
    private int editTextHasFocusPosition = -1;

    private NestedScrollView scrollView;
     int y;

    public void setScrollView(NestedScrollView scrollView) {
        this.scrollView = scrollView;
    }

    private Context context;
    public PicandVideoAdapter(List<SendTask> data,Context context) {
        super(data);
        this.context=context;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.picandvid;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, SendTask item) {
        ImageView iv= holder.getImageView(R.id.pviv);
        Glide.with(context).load(new File(item.getUri() )).into(iv);
       Button editText=holder.getButton(R.id.pved);
       /*if (item.getType().equals(SendTask.VIDEO)){
            editText.setHint("请输入视频描述");
        }  */
         editText.setText("");
          if(item.getContent()!=null){
              editText.setText(item.getContent());
          }
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((ViewGroup) v.getParent())
                        .setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                return false;
            }
        });
       editText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               View view= LayoutInflater.from(context).inflate(R.layout.drafit,null);
               AlertDialog dialog=new AlertDialog.Builder(context)
                       .setCancelable(false)
                       .setView(view)
                       .show();

               Button qr= (Button) view.findViewById(R.id.draftqueren);
               Button qx= (Button) view.findViewById(R.id.draftquxiao);
               EditText name= (EditText) view.findViewById(R.id.draftname);
               name.setHint("请输入文件描述");
             qr.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                        if(!TextUtils.isEmpty(name.getText().toString())){

                               editText.setText(name.getText().toString());
                                item.setContent(name.getText().toString());
                                 dialog.dismiss();

                        }  else {

                            dialog.dismiss();
                        }
                 }
             });
              qx.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      dialog.dismiss();

                  }
              });
           }
       });
                   iv.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent=new Intent(context, ImgandVideoActivity.class);
                           intent.putExtra("tasks", (Serializable) mData);
                           intent.putExtra("position",position);
                           context.startActivity(intent);
                       }
                   });




        //editText.addTextChangedListener(new );
    }



}
