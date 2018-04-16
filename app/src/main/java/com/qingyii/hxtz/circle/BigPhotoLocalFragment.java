package com.qingyii.hxtz.circle;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andbase.library.cache.image.AbImageCache;
import com.andbase.library.cache.image.AbImageCacheImpl;
import com.andbase.library.util.AbFileUtil;
import com.qingyii.hxtz.R;

import java.io.File;
import java.util.ArrayList;

//import com.ab.image.AbImageCache;
//import com.ab.util.AbFileUtil;
//import com.ab.util.AbImageUtil;

/**
 * 大图查看类,只针对本地
 *
 * @author Lee
 */
public class BigPhotoLocalFragment extends DialogFragment implements
        OnPageChangeListener {

    /**
     * dismiss回调
     *
     * @author lijiang
     */
    public interface Callback {
        public void photoManagerDismiss();
    }

    private Callback mCallback;

    private ExtendedViewPager mViewPager;

    private ArrayList<String> mPhotos = new ArrayList<String>();

    private BigPhotoAdapter mBigPhotoAdapter;
    private ArrayList<TouchImageView> mViews;

    private AbImageCache abImageCache = null;

    private View mViewNav;
    private LinearLayout mImgLeft;
    private TextView mTextTitle;

    private int mNowIndex = 0;

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        // getDialog().getWindow().getAttributes().windowAnimations =
        // R.style.dialogfragment_push_style;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (Callback) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public static BigPhotoLocalFragment getInstance(ArrayList<String> photos,
                                                    int currentItem) {
        BigPhotoLocalFragment frag = new BigPhotoLocalFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("photos", photos);
        bundle.putInt("index", currentItem);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Light_NoTitleBar);
        Bundle b = getArguments();
        ArrayList<String> photos = b.getStringArrayList("photos");
        if (photos == null) {
            dismissAllowingStateLoss();
        }
        for (String bigphoto : photos) {
            mPhotos.add(bigphoto);
        }
        mViews = new ArrayList<TouchImageView>();
        for (int i = 0; i < mPhotos.size(); i++) {
            TouchImageView imageView = new TouchImageView(getActivity());
            imageView.setScaleType(ScaleType.FIT_CENTER);
            mViews.add(imageView);
        }
        mBigPhotoAdapter = new BigPhotoAdapter();
        mNowIndex = b.getInt("index");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_bigphoto, null);
        findView(view);
        setListener();
        setData();
        return view;
    }

    private void findView(View view) {
        mViewPager = (ExtendedViewPager) view
                .findViewById(R.id.bigphoto_viewpager);
        mViewNav = view.findViewById(R.id.bigphoto_nav);
        mImgLeft = (LinearLayout) view.findViewById(R.id.bigphoto_left);
        mTextTitle = (TextView) view.findViewById(R.id.bigphoto_title);
    }

    private void setListener() {
        mImgLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dismissAllowingStateLoss();
            }
        });
        mViewPager.setOnPageChangeListener(this);
    }

    private void setData() {
        mTextTitle.setText((mNowIndex + 1) + "/" + mPhotos.size());
        mViewPager.setAdapter(mBigPhotoAdapter);
        mViewPager.setCurrentItem(mNowIndex);
    }

    private class BigPhotoAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPhotos.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TouchImageView imageView = mViews.get(position);
            String photo = mPhotos.get(position);
            // 从缓存中获取图片，很重要否则会导致页面闪动
//            Bitmap bitmap = AbImageCache.getInstance().getBitmap(photo);
            if (abImageCache == null)
                abImageCache = new AbImageCacheImpl(BigPhotoLocalFragment.this.getActivity());
            Bitmap bitmap = abImageCache.getBitmap(photo);

            // 缓存中没有则从网络和SD卡获取
            if (bitmap == null) {
                imageView.setImageResource(R.drawable.image_loading);
                Bitmap mBitmap = AbFileUtil.getBitmapFromSD(new File(photo));
                if (mBitmap != null) {
                    imageView.setImageBitmap(mBitmap);
                } else {
                    // 无图片时显示
                    imageView.setImageResource(R.drawable.image_empty);
                }
            } else {
                // 直接显示
                imageView.setImageBitmap(bitmap);
            }

            container.addView(imageView);
            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (mViewNav.getVisibility() == View.VISIBLE) {
                        mViewNav.setVisibility(View.GONE);
                    } else {
                        mViewNav.setVisibility(View.VISIBLE);
                    }
                }
            });
            return imageView;
        }

    }

    /**
     * dismiss当前fragment并且回调
     */
    private void dismissWithCallback() {
        BigPhotoLocalFragment.this.dismiss();
        if (mCallback != null) {
            mCallback.photoManagerDismiss();
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        mNowIndex = arg0;
        mTextTitle.setText((mNowIndex + 1) + "/" + mPhotos.size());
    }

}
