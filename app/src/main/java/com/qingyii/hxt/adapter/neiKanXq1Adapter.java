package com.qingyii.hxt.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.R;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.pojo.ArticleListNK;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;

import java.util.List;

public class neiKanXq1Adapter extends BaseAdapter {
    private Context context;
    private List<ArticleListNK.DataBean> list;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public neiKanXq1Adapter(Context context, List<ArticleListNK.DataBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArticleListNK.DataBean aDataBean = list.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.neikanxq1_item, null);

//        ImageView neikanxq1_image_iv = (ImageView) convertView.findViewById(R.id.neikanxq1_image_iv);


        ImageView tv_neikanxq1_Image = (ImageView) convertView.findViewById(R.id.tv_neikanxq1_Image);
        TextView tv_neikanxq1_title = (TextView) convertView.findViewById(R.id.tv_neikanxq1_title);
        TextView tv_neikanxq1_content = (TextView) convertView.findViewById(R.id.tv_neikanxq1_content);
        TextView tv_neikanxq1_type = (TextView) convertView.findViewById(R.id.tv_neikanxq1_type);
        TextView tv_neikanxq1_size = (TextView) convertView.findViewById(R.id.tv_neikanxq1_size);
        //TextView tv_neikan_date = (TextView) convertView.findViewById(R.id.tv_neikan_date);

        tv_neikanxq1_title.setText(aDataBean.getTitle());
        tv_neikanxq1_content.setText(aDataBean.getDescription());
        tv_neikanxq1_content.setVisibility(TextUtils.isEmpty(aDataBean.getDescription())?View.GONE:View.VISIBLE);

//        tv_neikan_date.setText(aDataBean.getAuthor()+" <"+ aDataBean.getType() +">" );
        //tv_neikan_date.setText(aDataBean.getAuthor());

        if (aDataBean.getArticlecover().equals(""))
            tv_neikanxq1_Image.setVisibility(View.GONE);
        else
            tv_neikanxq1_Image.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(aDataBean.getArticlecover(), tv_neikanxq1_Image, MyApplication.options, animateFirstListener);

        if (aDataBean.getType().equals("pdf")) {
            tv_neikanxq1_type.setVisibility(View.VISIBLE);
            tv_neikanxq1_size.setVisibility(View.VISIBLE);
            if (aDataBean.getSize() > 1024*1024)
                tv_neikanxq1_size.setText("大小：" + aDataBean.getSize() / 1024*1024 + "M");
            else
                tv_neikanxq1_size.setText("大小：" + aDataBean.getSize() / 1024 + "K");
        } else {
            tv_neikanxq1_type.setVisibility(View.GONE);
            tv_neikanxq1_size.setVisibility(View.GONE);
        }
//        if (!EmptyUtil.IsNotEmpty(aDataBean.getPicaddress())) {
//            neikanxq1_image_iv.setVisibility(convertView.GONE);
//        }
//        if (EmptyUtil.IsNotEmpty(p.getPicaddress())) {
//            neikanxq1_image_iv.setVisibility(NotifyView.VISIBLE);
//            ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + p.getPicaddress(), neikanxq1_image_iv, MyApplication.options, animateFirstListener);
//        }
//        if (EmptyUtil.IsNotEmpty(aDataBean.getArticlecover())) {
//            neikanxq1_image_iv.setVisibility(NotifyView.VISIBLE);
//            ImageLoader.getInstance().displayImage(aDataBean.getArticlecover(), neikanxq1_image_iv, MyApplication.options, animateFirstListener);
//        } else {
//            neikanxq1_image_iv.setVisibility(convertView.GONE);
//        }
        return convertView;
    }

}
