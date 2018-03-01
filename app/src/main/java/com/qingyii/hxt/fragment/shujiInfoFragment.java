package com.qingyii.hxt.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.pojo.BooksParameter;

@SuppressLint("NewApi")
public class shujiInfoFragment extends Fragment {
    private Activity mActivity = null;
    private TextView tv_shujiinfo_info;
    private Bundle b;
    private BooksParameter.DataBean bookInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bookInfo = (BooksParameter.DataBean) b.getParcelable("bookInfo");
        mActivity = this.getActivity();
        View view = inflater.inflate(R.layout.fragment_shujiinfo, null);
        tv_shujiinfo_info = (TextView) view.findViewById(R.id.tv_shujiinfo_info);
        tv_shujiinfo_info.setText(bookInfo.getDescription());
        tv_shujiinfo_info.setMovementMethod(ScrollingMovementMethod.getInstance());

        return view;
    }

    /**
     * activity向fragment传值
     *
     * @param b
     */
    public void setBundle(Bundle b) {
        this.b = b;
    }
}
