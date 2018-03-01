package com.qingyii.hxt.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andbase.library.app.base.AbBaseFragment;
import com.qingyii.hxt.myjifenActivity;

import java.util.List;

//import com.ab.fragment.AbFragment;

public class chengjiFragment extends AbBaseFragment {
    private Activity mActivity = null;
    private List<myjifenActivity> list;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    @Override
//    public NotifyListView onCreateContentView(LayoutInflater inflater,
//                                    ViewGroup container, Bundle savedInstanceState) {
//        mActivity = this.getActivity();
////	    	NotifyListView view=inflater.inflate(R.layout, null);
//        return super.onCreateContentView(inflater, container, savedInstanceState);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = this.getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
