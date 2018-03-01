package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.R;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.Book;
import com.qingyii.hxt.pojo.BooksParameter;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;
import com.qingyii.hxt.util.EmptyUtil;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.description;

public class shuwuAdapter extends BaseAdapter {
    private Context context;
    private List<BooksParameter.DataBean> list;
    private String condition;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public shuwuAdapter(Context context, List<BooksParameter.DataBean> list, String condition) {
        super();
        this.context = context;
        this.list = list;
        this.condition = condition;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BooksParameter.DataBean bDataBean = list.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.fragment_shuwu_item, null);
        TextView tv_shuwu_isgk = (TextView) convertView.findViewById(R.id.tv_shuwu_isgk);
        TextView tv_shujia_title = (TextView) convertView.findViewById(R.id.tv_shujia_title);
        TextView tv_shujia_time = (TextView) convertView.findViewById(R.id.tv_shujia_time);
        TextView tv_shujia_name = (TextView) convertView.findViewById(R.id.tv_shujia_name);
        TextView tv_shujia_content = (TextView) convertView.findViewById(R.id.tv_shujia_content);
        ImageView tv_shuwu_jurisdic = (ImageView) convertView.findViewById(R.id.tv_shuwu_jurisdic);
        ImageView iv_shujia_Image = (ImageView) convertView.findViewById(R.id.iv_shujia_Image);
//		tv_shuwu_isgk.setText(list.get(position).getShuwu_isgongkai());
//        if (EmptyUtil.IsNotEmpty(info.getBookname())) {
//            tv_shujia_title.setText(info.getBookname());
//        }
//        if (EmptyUtil.IsNotEmpty(info.getAuthor())) {
//            tv_shujia_name.setText("作者：  " + info.getAuthor());
//        }
//        if (EmptyUtil.IsNotEmpty(info.getBookdesc())) {
//            tv_shujia_content.setText(info.getBookdesc());
//        }
//        if (EmptyUtil.IsNotEmpty(info.getReadflag())) {
//            if ("1".equals(info.getReadflag())) {
//                tv_shuwu_isgk.setText("公开");
//            } else if ("2".equals(info.getReadflag())) {
//                tv_shuwu_isgk.setText("专有");
//            }
//        }
        if (EmptyUtil.IsNotEmpty(bDataBean.getBookname()))
            tv_shujia_title.setText(bDataBean.getBookname());

        if (condition.equals("paihang")) {
            tv_shujia_time.setVisibility(View.VISIBLE);
        } else {
            tv_shujia_time.setVisibility(View.GONE);
        }
        if (EmptyUtil.IsNotEmpty(bDataBean.getReadnums() + ""))
            tv_shujia_time.setText("阅读次数：  " + bDataBean.getReadnums());

        if (EmptyUtil.IsNotEmpty(bDataBean.getAuthor()))
            tv_shujia_name.setText("作者：  " + bDataBean.getAuthor());

        if (EmptyUtil.IsNotEmpty(bDataBean.getDescription()))
            tv_shujia_content.setText(bDataBean.getDescription());

        if (EmptyUtil.IsNotEmpty(bDataBean.getPublishstatus())) {
            if ("public".equals(bDataBean.getPublishstatus())) {
                tv_shuwu_jurisdic.setBackgroundResource(R.mipmap.shuwu_gongkai);
            } else if ("private".equals(bDataBean.getPublishstatus())) {
                tv_shuwu_jurisdic.setBackgroundResource(R.mipmap.shuwu_zhuanyou);
            } else if ("share".equals(bDataBean.getPublishstatus())) {
                tv_shuwu_jurisdic.setBackgroundResource(R.mipmap.shuwu_gongxiang);
            }
        }

//        if (EmptyUtil.IsNotEmpty(bDataBean.getReadflag())) {
//            if ("1".equals(bDataBean.getReadflag())) {
        tv_shuwu_isgk.setText("公开");
//            } else if ("2".equals(bDataBean.getReadflag())) {
//                tv_shuwu_isgk.setText("专有");
//            }
//        }

//        ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + info.getPicaddress(), iv_shujia_Image, MyApplication.options, animateFirstListener);
        ImageLoader.getInstance().displayImage(bDataBean.getBookcover(), iv_shujia_Image, MyApplication.options, animateFirstListener);
//		ImageLoader.getInstance().displayImage(info.getPicaddress(), iv_shujia_Image);

        return convertView;
    }

}
