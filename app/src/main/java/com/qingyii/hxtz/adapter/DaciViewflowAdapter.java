package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.DitiChuangg;
import com.qingyii.hxtz.util.EmptyUtil;

import java.util.ArrayList;

public class DaciViewflowAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<DitiChuangg> list;


    public DaciViewflowAdapter(Context context, ArrayList<DitiChuangg> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final DaCiAdapter adapter;


        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.viewflow_item, null);
            holder.viewflow_item_title = (TextView) convertView.findViewById(R.id.viewflow_item_title);
            holder.viewflow_item_list = (ListView) convertView.findViewById(R.id.viewflow_item_list);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (EmptyUtil.IsNotEmpty(list.get(position).getRedios())) {
            if ("2".equals(list.get(position).getRedios())) {
                holder.viewflow_item_title.setText("(多选题)"
                        + list.get(position).getQuestiondesc());
            } else if ("1".equals(list.get(position).getRedios())) {
                holder.viewflow_item_title.setText("(单选题)"
                        + list.get(position).getQuestiondesc());
            } else if ("3".equals(list.get(position).getRedios())) {
                holder.viewflow_item_title.setText("(判断题)"
                        + list.get(position).getQuestiondesc());
            }
        }

        //实例化适配器
        adapter = new DaCiAdapter(context, list.get(position));
        holder.viewflow_item_list.setAdapter(adapter);

        final String a = list.get(position).getRedios();

        //list点击事件
        holder.viewflow_item_list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int myposition, long id) {
                // TODO Auto-generated method stub

                String itemid = list.get(position).getQuestionOptionList().get(myposition).getOptionid();

                if ("1".equals(a) || "3".equals(a)) {
                    if (list.get(position).getXxid().contains(itemid)) {
                        list.get(position).getXxid().remove(itemid);
                    } else {
                        list.get(position).getXxid().clear();
                        list.get(position).getXxid().add(itemid);
                    }
                } else if ("2".equals(a)) {
                    if (list.get(position).getXxid().contains(itemid)) {
                        list.get(position).getXxid().remove(itemid);
                    } else {
                        list.get(position).getXxid().add(itemid);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView viewflow_item_title;
        ListView viewflow_item_list;
    }
}
