package com.qingyii.hxt.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.circle.BigPhotoFragment;
import com.qingyii.hxt.circle.NonScrollGridView;
import com.qingyii.hxt.circle.PhotoAdapter;
import com.qingyii.hxt.customview.MyGridView;
import com.qingyii.hxt.pojo.MyUserList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 63264 on 16/9/22.
 */

public class UserListMyAdapter extends BaseAdapter {

    private List<MyUserList.DataBean> list;
    private AbBaseActivity abBaseActivity;

    public UserListMyAdapter(AbBaseActivity abBaseActivity, List<MyUserList.DataBean> list) {
        this.abBaseActivity = abBaseActivity;
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = convertView.inflate(abBaseActivity, R.layout.user_context_list_item, null);
            holder = new ViewHolder();
            holder.context_main_content = (TextView) convertView.findViewById(R.id.context_main_content);
            holder.context_main_time = (TextView) convertView.findViewById(R.id.context_main_time);
//            holder.context_main_integral = (TextView) convertView.findViewById(R.id.context_main_integral);
//            holder.context_main_gist = (TextView) convertView.findViewById(R.id.context_main_gist);
            holder.context_main_photo = (MyGridView) convertView.findViewById(R.id.context_main_photo);
            holder.photos = new ArrayList<String>();
            holder.photoAdapter = new PhotoAdapter(abBaseActivity, holder.photos);
            //这是，避免遮挡ListView Item
            holder.context_main_photo.setPressed(false);
            holder.context_main_photo.setClickable(false);
            holder.context_main_photo.setEnabled(false);

            holder.context_main_photo.setAdapter(holder.photoAdapter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.photos.clear();
        holder.photoAdapter.notifyDataSetChanged();

        MyUserList.DataBean mInfo = list.get(position);

        holder.context_main_content.setText(mInfo.getContent());
        holder.context_main_time.setText(mInfo.getCreated_at());
//        holder.context_main_integral.setText(mInfo.);
//        holder.context_main_gist.setText();

        //内容图片
        MyUserList.DataBean.ChecklogBean images = mInfo.getChecklog();

//        Log.e("item" + "图片数量", position + "   " + images.size());
        if (images != null) {
//            for (int size = 0; size < images.size(); size++) {
//                holder.photos.add(images.get(size).getUri());
//                if (holder.photos.size() >= 9) {
//                    break;
//                }
//            }
            holder.photos.add(images.getReasonpic());
            holder.photoAdapter.notifyDataSetChanged();
        }
//        holder.context_main_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, NotifyView arg1, int arg2, long arg3) {
//                BigPhotoFragment frag = BigPhotoFragment.getInstance(holder.photos, arg2);
//                frag.show(abBaseActivity.getSupportFragmentManager(), "BigPhotoFragment");
//            }
//        });
//        if (mInfo.getCheckdata() == null) {
//        ImageLoader.getInstance().displayImage(null, holder.manage_list_user_image, MyApplication.options);
//        holder.manage_list_user_tv.setText(mInfo.getTruename());
//        }
        return convertView;
    }

    class ViewHolder {
        TextView context_main_content;
        MyGridView context_main_photo;
        PhotoAdapter photoAdapter;
        ArrayList<String> photos;
        TextView context_main_time;
        TextView context_main_integral;
        TextView context_main_gist;
    }
}