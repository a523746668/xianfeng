package com.qingyii.hxt.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxt.AuditGroupListActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.pojo.AuditGroupList;

import java.util.List;

/**
 * Created by 63264 on 16/9/22.
 */

public class AuditGroupListAdapter extends BaseAdapter {
    private AuditGroupListActivity auditGroupListActivity;
    private List<AuditGroupList.DataBean.GroupsBean> aGroupsBeanList;

    public AuditGroupListAdapter(AuditGroupListActivity auditGroupListActivity, List<AuditGroupList.DataBean.GroupsBean> aGroupsBeanList) {
        this.auditGroupListActivity = auditGroupListActivity;
        this.aGroupsBeanList = aGroupsBeanList;
    }

    @Override
    public int getCount() {
        return aGroupsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return aGroupsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = convertView.inflate(auditGroupListActivity, R.layout.audit_group_list_item, null);
            holder = new ViewHolder();
            holder.audit_group_name = (TextView) convertView.findViewById(R.id.audit_group_name);
            holder.audit_group_state = (TextView) convertView.findViewById(R.id.audit_group_state);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            AuditGroupList.DataBean.GroupsBean aGroupsBean = aGroupsBeanList.get(position);
            holder.audit_group_name.setText(aGroupsBean.getName()+"");
            holder.audit_group_state.setText("共"+aGroupsBean.getPeople_cnt()+"人，"+aGroupsBean.getSubmit_cnt()+"人提交，共"+aGroupsBean.getDocs_cnt()+"条记录");

        } catch (Exception e) {
            Log.e("AuditCrewListAdapter", e.toString());
        }

        return convertView;
    }

    class ViewHolder {
        TextView audit_group_name;
        TextView audit_group_state;
    }
}