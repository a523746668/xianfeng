package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.pojo.BooksParameter;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;
import com.qingyii.hxtz.util.EmptyUtil;

import java.util.List;

public class ZuanTiItemAdapter extends BaseAdapter {
    private Context context;
    private List<BooksParameter.DataBean> list;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public ZuanTiItemAdapter(Context context, List<BooksParameter.DataBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override

    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BooksParameter.DataBean bDataBean = list.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.fenleizhuanti_item, null);
        ImageView iv_fenleiZti_Image = (ImageView) convertView.findViewById(R.id.iv_fenleiZti_Image);
        ImageView tv_fenleiZti_jurisdic = (ImageView) convertView.findViewById(R.id.tv_fenleiZti_jurisdic);
        TextView tv_fenleiZti_isgk = (TextView) convertView.findViewById(R.id.tv_fenleiZti_isgk);
        TextView tv_fenleiZti_title = (TextView) convertView.findViewById(R.id.tv_fenleiZti_title);
        TextView tv_fenleiZti_content = (TextView) convertView.findViewById(R.id.tv_fenleiZti_content);
//        TextView tv_fenleiZti_read=(TextView) convertView.findViewById(R.id.tv_fenleiZti_read);
        TextView tv_zuozhe = (TextView) convertView.findViewById(R.id.tv_zuozhe);


//        if (EmptyUtil.IsNotEmpty(szinfo.getBookname())) {
//            tv_fenleiZti_title.setText(list.get(position).getBookname());
//        }
//
//        if (EmptyUtil.IsNotEmpty(szinfo.getAuthor())) {
//            tv_zuozhe.setText("作者: " + list.get(position).getAuthor());
//        }
//
//        if (EmptyUtil.IsNotEmpty(szinfo.getBookdesc())) {
//            tv_fenleiZti_content.setText(list.get(position).getBookdesc());
//        }
//        if (EmptyUtil.IsNotEmpty(szinfo.getReadflag())) {
//            if ("1".equals(szinfo.getReadflag())) {
//                tv_fenleiZti_isgk.setText("公开");
//            } else if ("2".equals(szinfo.getReadflag())) {
//                tv_fenleiZti_isgk.setText("专有");
//            }
//        }

        if (EmptyUtil.IsNotEmpty(bDataBean.getBookname())) {
            tv_fenleiZti_title.setText(bDataBean.getBookname());
        }

        if (EmptyUtil.IsNotEmpty(bDataBean.getAuthor())) {
            tv_zuozhe.setText("作者: " + bDataBean.getAuthor());
        }

        if (EmptyUtil.IsNotEmpty(bDataBean.getDescription())) {
            tv_fenleiZti_content.setText(bDataBean.getDescription());
        }

        if (EmptyUtil.IsNotEmpty(bDataBean.getPublishstatus())) {
            if ("public".equals(bDataBean.getPublishstatus())) {
                tv_fenleiZti_jurisdic.setBackgroundResource(R.mipmap.shuwu_gongkai);
            } else if ("private".equals(bDataBean.getPublishstatus())) {
                tv_fenleiZti_jurisdic.setBackgroundResource(R.mipmap.shuwu_zhuanyou);
            } else if ("share".equals(bDataBean.getPublishstatus())) {
                tv_fenleiZti_jurisdic.setBackgroundResource(R.mipmap.shuwu_gongxiang);
            }
        }
//        if (EmptyUtil.IsNotEmpty(bDataBean.getReadflag())) {
//            if ("1".equals(bDataBean.getReadflag())) {
//                tv_fenleiZti_isgk.setText("公开");
//            } else if ("2".equals(bDataBean.getReadflag())) {
//                tv_fenleiZti_isgk.setText("专有");
//            }
//        }

//        if(EmptyUtil.IsNotEmpty(list.get(position).getReadcount())){
//        	tv_fenleiZti_read.setText("阅读次数: "+list.get(position).getReadcount());
//        }else{
//        	tv_fenleiZti_read.setText("阅读次数: "+"0次");
//        }


//        ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + szinfo.getPicaddress(), iv_fenleiZti_Image, MyApplication.options, animateFirstListener);
        ImageLoader.getInstance().displayImage(bDataBean.getBookcover(), iv_fenleiZti_Image, MyApplication.options, animateFirstListener);
//        ImageLoader.getInstance().displayImage(fzinfo.getFleiZt_imageUrl(), iv_fenleiZti_Image);
        return convertView;
    }

}
