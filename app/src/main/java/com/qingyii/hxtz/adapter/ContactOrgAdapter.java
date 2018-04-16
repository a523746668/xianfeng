package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.ContactOrg;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.util.ImageUtil;

import java.util.ArrayList;

/**
 * 通讯录组织列表
 *
 * @author Lee
 */
public class ContactOrgAdapter extends BaseAdapter {

    private ArrayList<ContactOrg> mList;
    private Context mContext;
    private DisplayImageOptions mOptions;

    public ContactOrgAdapter(Context context, ArrayList<ContactOrg> list) {
        mContext = context;
        this.mList = list;
        mOptions = ImageUtil.newDisplayOptions(R.mipmap.ic_head);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        final ViewHolder holder;
        if (arg1 == null) {
            arg1 = View.inflate(mContext,
                    R.layout.item_contact_org, null);
            holder = new ViewHolder();
            holder.imgHead = (ImageView) arg1
                    .findViewById(R.id.contact_org_header);
            holder.textName = (TextView) arg1
                    .findViewById(R.id.contact_org_name);
            arg1.setTag(holder);
        } else {
            holder = (ViewHolder) arg1.getTag();
        }
        ContactOrg info = mList.get(arg0);
        ImageLoader.getInstance().displayImage(
                HttpUrlConfig.photoDir + info.getPicaddress(),
                holder.imgHead, mOptions);
        holder.textName.setText("" + info.getName());
        return arg1;
    }

    class ViewHolder {
        ImageView imgHead;
        TextView textName;
    }

}
