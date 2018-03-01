package com.zhf.zfragment;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.commonadapter.rv.HeaderFooterAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.commonadapter.rv.mul.BaseMulTypeAdapter;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.widget.AutoLoadMoreRecyclerView;
import com.qingyii.hxt.wmcj.mvp.model.entity.FooterBean;
import com.qingyii.hxt.wmcj.mvp.model.entity.HeaderBean;
import com.qingyii.hxt.wmcj.mvp.ui.WorkParkDetailsActivity;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.zhf.Util.HintUtil;
import com.zhf.bean.Headbean;
import com.zhf.bean.WorkParkitembean;
import com.zhf.http.Urlutil;
import com.zhf.present.Implpresent.WorkParkitemPresenter;
import com.zhf.present.Implview.WorkParkitemview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by zhf on 2017/9/29.
 */

public class WorkParkItemFragment extends Fragment implements WorkParkitemview {
     @BindView(R.id.workpark_item_recyclerView)
     AutoLoadMoreRecyclerView recyclerView;

    @BindView(R.id.workpark_item_refresh)
    PtrClassicFrameLayout  ptr;

   private HeaderBean<Headbean.DataBean.SilderBean> headerbean;

    private ArrayList<WorkParkitembean.DataBean.AllactivityBean> list =new ArrayList<>();
    ArrayList<Headbean.DataBean.SilderBean>  list2=new ArrayList<>();

     HeaderFooterAdapter adapter;
     private  int id;
      private int system_id=-99;
    Unbinder unbinder;

    String time=null;

    public void setId(int id) {
        this.id = id;
    }

    public void setSystem_id(int system_id) {
        this.system_id = system_id;
    }

    WorkParkitemPresenter presenter;
    boolean flag=false;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View  view =inflater.inflate(R.layout.fragment_workpark_item, container, false);
        unbinder= ButterKnife.bind(this,view);
        return  view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
        initptr();
    }

    private void initptr() {
       ptr.setLastUpdateTimeRelateObject(this);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                if(system_id!=-99){
                    presenter.getdata(id,system_id);
                } else {
                    presenter.getdata(id);
                }

                if(flag){
                    presenter.getimages();
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, content, header);
            }
        });

       recyclerView.setOnLoadMoreListener(new AutoLoadMoreRecyclerView.OnLoadMoreListener() {
           @Override
           public void loadMore() {
               Log.i("tmdloadmore", time);
                if(time!=null){

                   if(system_id!=-99){
                       presenter.getdata(time,id,system_id);
                   }  else {
                       presenter.getdata(time,id);
                   }
              }
           }
       });
    }

    private void inithead() {
         headerbean=new HeaderBean<Headbean.DataBean.SilderBean>(list2) {
             @Override
             public void bindData(Banner banner, List<? extends Headbean.DataBean.SilderBean> list) {
                 ArrayList<String > imgurls=new ArrayList<>();
                 ArrayList<String> titiles=new ArrayList<>();
                  for(Headbean.DataBean.SilderBean bean:list){
                      imgurls.add(Urlutil.baseimgurl+bean.getImg());
                      Log.i("tmdimage",bean.getImg());
                      titiles.add(bean.getA_name());
                  }
                 banner.setImages(imgurls);
                 banner.setBannerTitles(titiles);
                 banner.setOnBannerClickListener(new OnBannerClickListener() {
                     @Override
                     public void OnBannerClick(int position) {
                        Intent intent=new Intent(getActivity(),WorkParkDetailsActivity.class);
                         intent.putExtra("actid",list.get(position-1).getId());
                         Log.i("tmdactid",list.get(position-1).getId()+"------"+position );
                         startActivity(intent);
                     }
                 });

                 banner.start();

             }
         };
        adapter.setHeaderView(0,headerbean);
    }




    private void initview() {

        adapter=new HeaderFooterAdapter(new BaseMulTypeAdapter<WorkParkitembean.DataBean.AllactivityBean>(getActivity(),list){
            @Override
            public void convert(ViewHolder holder, WorkParkitembean.DataBean.AllactivityBean allactivityBean) {
                super.convert(holder, allactivityBean);
                setDrawLeft(new Rect(0, 0, 35, 36), R.drawable.workpark_time, holder.getView(R.id.workpark_include_tv_time));
                setDrawLeft(new Rect(0, 0, 27, 27), R.drawable.workpark_comment, holder.getView(R.id.workpark_include_tv_comment));
                setDrawLeft(new Rect(0, 0, 35, 23), R.drawable.workpark_read, holder.getView(R.id.workpark_include_tv_read));
                TextView times= holder.getView(R.id.workpark_include_tv_time);
                times.setText(allactivityBean.getCreated_at());
                TextView name=holder.getView(R.id.workpark_lv_title);
                name.setText(allactivityBean.getA_name());
                TextView readsun=holder.getView(R.id.workpark_include_tv_read);

                readsun.setText(String.valueOf(allactivityBean.getNum_show()));
                TextView comment=holder.getView(R.id.workpark_include_tv_comment);
                comment.setText(String.valueOf(allactivityBean.getNum_comment()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), WorkParkDetailsActivity.class);
                        intent.putExtra("actid",allactivityBean.getId());
                        startActivity(intent);
                    }
                });
                switch(allactivityBean.getItemLayoutId()){
                    case R.layout.workpark_content_layout1:
                     ImageView iv1=holder.getView(R.id.workpark_lv1_iv);
                        String url1= Urlutil.baseimgurl+ allactivityBean.getImgs().get(0);
                        Log.i("tmdurl",url1);
                        Glide.with(getActivity()).load(url1).error(R.mipmap.error_default).into(iv1);
                        break;
                    case R.layout.workpark_content_layout2:
                        ImageView  imageView1=holder.getView(R.id.workpark_lv2_iv1);
                        ImageView  imageView2=holder.getView(R.id.workpark_lv2_iv2);
                        ImageView  imageView3=holder.getView(R.id.workpark_lv2_iv3);
                         Glide.with(getActivity()).load(Urlutil.baseimgurl+allactivityBean.getImgs().get(0))
                                 .error(R.mipmap.error_default)
                                 .into(imageView1);
                        Glide.with(getActivity()).load(Urlutil.baseimgurl+allactivityBean.getImgs().get(1))
                                .error(R.mipmap.error_default)
                                .into(imageView2);
                        Glide.with(getActivity()).load(Urlutil.baseimgurl+allactivityBean.getImgs().get(2))
                                .error(R.mipmap.error_default)
                                .into(imageView3);
                        break;
                    case R.layout.alltext:

                        break;
                }

            }

        }

        );
        recyclerView.setAutoLayoutManager(new LinearLayoutManager(getActivity()))
                .setAutoItemAnimator(new DefaultItemAnimator())
                .addAutoItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL))
                .setAutoAdapter(adapter);


        presenter=new WorkParkitemPresenter(getActivity(),this);
        if(flag){
          presenter.getimages();
        }

        if(system_id!=-99){
            presenter.getdata(id,system_id);
          }
           else {
            Log.i("tmdvipager","----"+id);
            presenter.getdata(id);
        }


    }



    @Override
    public void onDestroy() {
        super.onDestroy();
       unbinder.unbind();
        presenter.unbind();
        list.clear();
        list2.clear();

    }

    @Override
    public void showerrow(Exception e) {

    }

    @Override
    public void getdatasuccess(ArrayList<WorkParkitembean> list) {

    }


    //刷新数据的回调
    @Override
    public void getdatasuccess(WorkParkitembean workParkitembean) {

        list.clear();
           list.addAll(workParkitembean.getData().getAllactivity());
           time=workParkitembean.getData().getCreated_at();
             adapter.notifyDataSetChanged();
    }



    //动态设置drawableLeft大小
    private void setDrawLeft(Rect bond, int resid, TextView tv) {

            Drawable drawable = getActivity().getResources().getDrawable(resid);
        drawable.setBounds(bond);
        tv.setCompoundDrawables(drawable, null, null, null);

    }

    @Override
    public void startloadmore() {
        recyclerView.notifyMoreLoaded();
        FooterBean footbean=new FooterBean();

        adapter.addFooterView(footbean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void endloadmore() {
      adapter.clearFooterView();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void finishrefresh() {
        ptr.refreshComplete();
    }


    //获得下一页数据后的回调
    @Override
    public void getmoredatasuccess(WorkParkitembean workParkitembean) {
       if(workParkitembean.getData().getAllactivity()==null|workParkitembean.getData().getAllactivity().size()<=0){
           HintUtil.showtoast(getActivity(),"已经是最后一页");
       }
      else  {
            int i = list.size();

            Log.i("tmdloadmore", workParkitembean.getData().getAllactivity().size() + "");
            list.addAll(workParkitembean.getData().getAllactivity());
            time =workParkitembean.getData().getCreated_at();
            adapter.notifyDataSetChanged();
            //recyclerView.scrollToPosition(i);
        }
    }

    @Override
    public void getimagessuccee(Headbean headbean) {
        list2.clear();
        list2.addAll(headbean.getData().getSilder());
        inithead();
    }
}
