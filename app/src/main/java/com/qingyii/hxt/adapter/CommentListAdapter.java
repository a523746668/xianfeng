package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxt.R;
import com.qingyii.hxt.bean.CommentInfo;

import java.util.ArrayList;

public class CommentListAdapter extends BaseAdapter {

    private ArrayList<CommentInfo> list;
    private Context context;
    private static ViewHolder holder;

    public CommentListAdapter(Context context, ArrayList<CommentInfo> list) {
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

//		if(convertView == null){
        holder = new ViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.comment_list_item, null);
        holder.icon_comment_item = (ImageView) convertView.findViewById(R.id.icon_comment_item);
        holder.tv_content = (TextView) convertView.findViewById(R.id.content_comment_item);
        holder.tv_name = (TextView) convertView.findViewById(R.id.name_comment_item);
        holder.tv_time = (TextView) convertView.findViewById(R.id.time_comment_item);
        holder.tv_ok = (ImageView) convertView.findViewById(R.id.ok_comment_item);
//			holder.tv_support = (TextView) convertView.findViewById(R.id.support_num_comment);
//			holder.tv_oppose = (TextView) convertView.findViewById(R.id.oppose_num_comment);
//			holder.iv_support = (ImageView) convertView.findViewById(R.id.support_comment);
//			holder.iv_oppose = (ImageView) convertView.findViewById(R.id.oppose_comment);
        convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
        CommentInfo cinfo = list.get(position);
        holder.tv_name.setText(list.get(position).getYijian_name());
        holder.tv_time.setText(list.get(position).getTime());
        holder.tv_content.setText(list.get(position).getCommentContent());
        ImageLoader.getInstance().displayImage(cinfo.getYijian_ImageUrl(), holder.icon_comment_item);
        return convertView;
    }

    class ViewHolder {
        TextView tv_name, tv_content, tv_support, tv_oppose, tv_time;
        ImageView iv_icon, iv_support, iv_oppose, icon_comment_item, tv_ok;
    }
}
