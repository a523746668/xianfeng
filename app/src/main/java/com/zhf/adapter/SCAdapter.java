package com.zhf.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.base.adapter.BaseRecyclerAdapter;
import com.qingyii.hxt.base.adapter.BaseRecyclerViewHolder;
import com.qingyii.hxt.neiKanWebActivity;
import com.qingyii.hxt.wmcj.mvp.ui.WorkParkDetailsActivity;
import com.zhf.Sqlitehelper.Drafitbean;
import com.zhf.bean.SCbean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by zhf on 2018/1/15.
 */

public class SCAdapter extends BaseRecyclerAdapter<SCbean.DataBean> {

     Context mcontext;
    public SCAdapter(List<SCbean.DataBean> data ,Context mcontext) {
        super(data);
        this.mcontext=mcontext;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.lattestrecyc;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, SCbean.DataBean item) {
        TextView tv= holder.getTextView(R.id.lattext);
         tv.setText(item.getTitle());
         tv.setTextSize(62);
        tv.setHeight(140);
          holder.setClickListener(R.id.lattext, new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                   if(item.getType().equalsIgnoreCase("article")){
                      Intent intent=new Intent(mcontext,neiKanWebActivity.class);
                      intent.putExtra("data",item);
                      mcontext.startActivity(intent);
                  } else {
                       Intent intent=new Intent(mcontext,WorkParkDetailsActivity.class);
                       intent.putExtra("actid",item.getId());
                       mcontext.startActivity(intent);
                  }
              }
          });
    }
}
