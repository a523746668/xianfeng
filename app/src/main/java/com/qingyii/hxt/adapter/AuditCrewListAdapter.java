package com.qingyii.hxt.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxt.AuditCrewListActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.circle.NonScrollGridView;
import com.qingyii.hxt.customview.MyGridView;
import com.qingyii.hxt.pojo.AuditCrewList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 63264 on 16/9/22.
 */

public class AuditCrewListAdapter extends BaseAdapter {
    private AuditCrewListActivity auditCrewListActivity;
    private List<AuditCrewList.DataBean.UsresBean> aUsresBeanList;

    public AuditCrewListAdapter(AuditCrewListActivity auditCrewListActivity, List<AuditCrewList.DataBean.UsresBean> aUsresBeanList) {
        this.auditCrewListActivity = auditCrewListActivity;
        this.aUsresBeanList = aUsresBeanList;
    }

    @Override
    public int getCount() {
        return aUsresBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return aUsresBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = convertView.inflate(auditCrewListActivity, R.layout.audit_crew_list_item, null);
            holder = new ViewHolder();
            holder.audit_crew_name = (TextView) convertView.findViewById(R.id.audit_crew_name);
            holder.audit_crew_GridView = (MyGridView) convertView.findViewById(R.id.audit_crew_GridView);
//            holder.audit_crew_resumption = (TextView) convertView.findViewById(R.id.audit_crew_resumption);
//            holder.audit_crew_study = (TextView) convertView.findViewById(R.id.audit_crew_study);
//            holder.audit_crew_activity = (TextView) convertView.findViewById(R.id.audit_crew_activity);
//            holder.audit_crew_integrity = (TextView) convertView.findViewById(R.id.audit_crew_integrity);
//            holder.audit_crew_state = (TextView) convertView.findViewById(R.id.audit_crew_state);
            holder.audit_crew_state1 = (TextView) convertView.findViewById(R.id.audit_crew_state1);
            holder.audit_crew_state2 = (TextView) convertView.findViewById(R.id.audit_crew_state2);
            holder.audit_crew_state3 = (TextView) convertView.findViewById(R.id.audit_crew_state3);
            holder.audit_crew_state4 = (TextView) convertView.findViewById(R.id.audit_crew_state4);
            //这是，避免遮挡ListView Item
            holder.audit_crew_GridView.setPressed(false);
            holder.audit_crew_GridView.setClickable(false);
            holder.audit_crew_GridView.setEnabled(false);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            AuditCrewList.DataBean.UsresBean aDataBean = aUsresBeanList.get(position);

            holder.audit_crew_name.setText(aDataBean.getUsername() + "");

            List<String> count = new ArrayList<>();
            for (int i = 0; i < aDataBean.getCount().size(); i++) {
                count.add(aDataBean.getCount().get(i).getName() + "类 " + aDataBean.getCount().get(i).getNum() + " 条");
            }
            TextGridAdapter textGridAdapter = new TextGridAdapter(auditCrewListActivity, holder.audit_crew_GridView, count);
            holder.audit_crew_GridView.setAdapter(textGridAdapter);
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text_list_item,data);
//            holder.audit_crew_resumption.setText("诚信类 "+aDataBean.getUsername()+" 条");
//            holder.audit_crew_study.setText("诚信类 "+aDataBean.getUsername()+" 条");
//            holder.audit_crew_activity.setText("诚信类 "+aDataBean.getUsername()+" 条");
//            holder.audit_crew_integrity.setText("诚信类 "+aDataBean.getUsername()+" 条");

//            String state = "";
//            if (aDataBean.getAppeal() == 1)
//                state += "有申诉";
//            if (aDataBean.getUnchecked() == 1) {
//                if (aDataBean.getAppeal() == 1)
//                    state += "\n";
//                state += "待审核";
//            }
//            if (aDataBean.getUnconfirm() == 1) {
//                if (aDataBean.getUnchecked() == 1 || aDataBean.getAppeal() == 1)
//                    state += "\n";
//                state += "待确认";
//            }
//            if (aDataBean.getAppeal() == 0 && aDataBean.getUnchecked() == 0 && aDataBean.getUnconfirm() == 0)
//                state = "已确认";
//            holder.audit_crew_state.setText(state);


            if (aDataBean.getAppeal() == 1)
                holder.audit_crew_state1.setVisibility(View.VISIBLE);
            else
                holder.audit_crew_state1.setVisibility(View.GONE);
            if (aDataBean.getUnchecked() == 1)
                holder.audit_crew_state2.setVisibility(View.VISIBLE);
            else
                holder.audit_crew_state2.setVisibility(View.GONE);
            if (aDataBean.getUnconfirm() == 1)
                holder.audit_crew_state3.setVisibility(View.VISIBLE);
            else
                holder.audit_crew_state3.setVisibility(View.GONE);
            if (aDataBean.getAppeal() == 0 && aDataBean.getUnchecked() == 0 && aDataBean.getUnconfirm() == 0)
                holder.audit_crew_state4.setVisibility(View.VISIBLE);
            else
                holder.audit_crew_state4.setVisibility(View.GONE);

        } catch (Exception e) {
            Log.e("AuditCrewListAdapter", e.toString());
        }

        return convertView;
    }

    class ViewHolder {
        TextView audit_crew_name;
        MyGridView audit_crew_GridView;
        //TextView audit_crew_resumption;
//        TextView audit_crew_study;
//        TextView audit_crew_activity;
//        TextView audit_crew_integrity;
//        TextView audit_crew_state;
        TextView audit_crew_state1;
        TextView audit_crew_state2;
        TextView audit_crew_state3;
        TextView audit_crew_state4;
    }
}