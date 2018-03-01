package com.zhf.zfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.zhf.Util.HintUtil;
import com.zhf.adapter.ResultsonAdapter;
import com.zhf.bean.Resultbean;
import com.zhf.present.Implpresent.Resultsonpresenter;
import com.zhf.present.Implview.Resultsonview;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/9/22.
 */
//结果清单viewpager里面的Fragment
public class ResultsonFragment extends Fragment implements Resultsonview{
    Resultbean resultbean;
    TextView title,getscore,score,postion1,allpostion1,postion2,allpostion2;
    TextView name1,name2;
    Button back;
    RecyclerView recyclerView;
    private Resultsonpresenter presenter;

    ArrayList<Resultbean.DataBean.BrothindustryBean> list=new ArrayList<>();
    private ResultsonAdapter adapter;

    int  librarySystem;
    int industryid=-999;

    public void setIndustryid(int industryid) {
        this.industryid = industryid;
    }

    public void setLibrarySystem(int librarySystem) {
        this.librarySystem = librarySystem;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.resultsonfragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview(view);
        presenter=new Resultsonpresenter(getActivity(),this);

        if(industryid==-999){
            presenter.getdata(librarySystem );}
        else {
            presenter.getdata(librarySystem,industryid);
        }

    }



    private void initview(View view) {
        name2= (TextView) view.findViewById(R.id.postion2name);
        name1= (TextView) view.findViewById(R.id.postion1name);
      title= (TextView) view.findViewById(R.id.resultsonname);
      getscore= (TextView) view.findViewById(R.id.getscore);
        postion1= (TextView) view.findViewById(R.id.postion);
        postion2= (TextView) view.findViewById(R.id.postion2);
        score= (TextView) view.findViewById(R.id.score);
        allpostion1=(TextView) view.findViewById(R.id.allpostion);
        allpostion2=(TextView) view.findViewById(R.id.allpostion2);
        recyclerView= (RecyclerView) view.findViewById(R.id.resultsonrecyc);
        adapter=new ResultsonAdapter(list,getActivity() );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void getdatasuccess(Resultbean resultbean) {
            title.setText(resultbean.getData().getMy().getName());
            getscore.setText(String.valueOf(resultbean.getData().getMy().getMyscore()));
        score.setText("/"+resultbean.getData().getMy().getScore());
      /*  name1.setText(resultbean.getData().getParentindustry().getName());
        postion1.setText(String.valueOf(resultbean.getData().getParentindustry().getOrder()));
        allpostion1.setText("/"+resultbean.getData().getParentindustry().getTotle());
      */
       name1.setText("在"+resultbean.getData().getTopIndustry().getName()+"的排名");
        postion1.setText(String.valueOf(resultbean.getData().getTopIndustry().getOrder()));
         allpostion1.setText("/"+resultbean.getData().getTopIndustry().getTotle());
           list.clear();
           list.addAll(resultbean.getData().getBrothindustry());
            adapter.notifyDataSetChanged();
        HintUtil.stopdialog();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unbind();
        presenter=null;
    }
}
