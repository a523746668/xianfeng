package com.qingyii.hxtz.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.MyTongXunLuDetailsActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.home.mvp.model.entity.HomeInfo;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.pojo.AddressList;
import com.qingyii.hxtz.pojo.AddressUnitList;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;
import com.qingyii.hxtz.view.PinnedHeaderExpandableListView;
import com.qingyii.hxtz.view.RoundedImageView;

import java.util.List;

public class PinnedHeaderExpandableAdapter extends BaseExpandableListAdapter implements PinnedHeaderExpandableListView.HeaderAdapter {
    //    private String[][] childrenData;
    private List<List<AddressList.DataBean>> aDataBeanList;
    private AddressUnitList groupData;
    private HomeInfo.AccountBean moduletitle;
    private Activity activity;
    private PinnedHeaderExpandableListView listView;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public PinnedHeaderExpandableAdapter(List<List<AddressList.DataBean>> aDataBeanList, AddressUnitList groupData
            , HomeInfo.AccountBean moduletitle, Activity activity, PinnedHeaderExpandableListView listView) {
        this.groupData = groupData;
        this.moduletitle = moduletitle;
        this.aDataBeanList = aDataBeanList;
        this.activity = activity;
        this.listView = listView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return aDataBeanList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.my_tong_xun_lu_child, parent, false);
            holder = new ChildViewHolder();
            holder.my_tong_xun_lu_child = (RelativeLayout) convertView.findViewById(R.id.my_tong_xun_lu_child);
            holder.child_icon = (RoundedImageView) convertView.findViewById(R.id.child_icon);
            holder.child_name = (TextView) convertView.findViewById(R.id.child_name);
            holder.child_department = (TextView) convertView.findViewById(R.id.child_department);

            convertView.setTag(holder);
        } else
            holder = (ChildViewHolder) convertView.getTag();

        final AddressList.DataBean aDataBean = aDataBeanList.get(groupPosition).get(childPosition);

        ImageLoader.getInstance().displayImage(aDataBean.getAvatar(), holder.child_icon, MyApplication.options, animateFirstListener);
        holder.child_name.setText(aDataBean.getTruename());
        holder.child_department.setText(aDataBean.getJobname() + "");
//        text.setText(aDataBeanList.get(groupPosition).get(childPosition).getTruename());

        holder.my_tong_xun_lu_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MyTongXunLuDetailsActivity.class);
                intent.putExtra("aDataBean", aDataBean);
                intent.putExtra("moduletitle", moduletitle);
                activity.startActivity(intent);
            }
        });
        return convertView;
    }

    class ChildViewHolder {
        RelativeLayout my_tong_xun_lu_child;
        RoundedImageView child_icon;
        TextView child_name;
        TextView child_department;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return aDataBeanList.get(groupPosition).size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.getData().get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groupData.getData().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.my_tong_xun_lu_group, parent, false);
            holder = new GroupViewHolder();
            holder.group_name = (TextView) convertView.findViewById(R.id.group_name);

            convertView.setTag(holder);
        } else
            holder = (GroupViewHolder) convertView.getTag();

//        ImageView iv = (ImageView) view.findViewById(R.id.groupIcon);

//        if (isExpanded) {
//            iv.setImageResource(R.drawable.btn_browser2);
//        } else {
//            iv.setImageResource(R.drawable.btn_browser);
//        }

        holder.group_name.setText(groupData.getData().get(groupPosition).getName());

        return convertView;
    }

    class GroupViewHolder {
        TextView group_name;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1 && !listView.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {
        String groupData = this.groupData.getData().get(groupPosition).getName();
        ((TextView) header.findViewById(R.id.group_name)).setText(groupData);
    }

    private SparseIntArray groupStatusMap = new SparseIntArray();

    @Override
    public void setGroupClickStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        return groupStatusMap.get(groupPosition);

//        Log.e("groupPosition", groupPosition + "");
//        Log.e("groupStatusMap.size()", groupStatusMap.size() + "");
//        Log.e("groupStatusMap.get()", groupStatusMap.get(groupPosition) + "");
//        Log.e("groupStatusMap.keyAt()", groupStatusMap.keyAt(groupPosition) + "");

//        if (groupStatusMap.keyAt(groupPosition) >= 0) {
//            return groupStatusMap.get(groupPosition);
//        } else {
//            return 0;
//        }
    }
}
