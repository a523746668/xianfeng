package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.bean.choiceOfResearchInfo;
import com.qingyii.hxt.bean.researchInfo;

import java.util.ArrayList;

public class viewFlorAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<researchInfo> list;
    private choiceOfResearchAdapter adapter;
    
	public viewFlorAdapter(Context context, ArrayList<researchInfo> list) {
		super();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ArrayList<choiceOfResearchInfo> list2=new ArrayList<choiceOfResearchInfo>();
		convertView=LayoutInflater.from(context).inflate(R.layout.fragment_question, null);
		TextView title_question=(TextView) convertView.findViewById(R.id.title_question);
		ListView lv_question=(ListView) convertView.findViewById(R.id.lv_question);
		title_question.setText(list.get(position).getReserchTitle());
		//嵌套list
        choiceOfResearchInfo crInfo=new choiceOfResearchInfo();
		crInfo.setChoiceContent("A.刘备");
        choiceOfResearchInfo crInfo1=new choiceOfResearchInfo();
        crInfo1.setChoiceContent("B.曹操");
        choiceOfResearchInfo crInfo2=new choiceOfResearchInfo();
        crInfo2.setChoiceContent("C.关于");
        choiceOfResearchInfo crInfo3=new choiceOfResearchInfo();
        crInfo3.setChoiceContent("D.赵云");
        choiceOfResearchInfo crInfo4=new choiceOfResearchInfo();
		crInfo4.setChoiceContent("星期一");
        choiceOfResearchInfo crInfo5=new choiceOfResearchInfo();
        crInfo5.setChoiceContent("星期2");
        choiceOfResearchInfo crInfo6=new choiceOfResearchInfo();
        crInfo6.setChoiceContent("星期3");
        choiceOfResearchInfo crInfo7=new choiceOfResearchInfo();
        crInfo7.setChoiceContent("星期4");
		if (position==0) {
			list2.add(crInfo);
			list2.add(crInfo1);
			list2.add(crInfo2);
			list2.add(crInfo3);
		}else if (position==1) {
		    list2.add(crInfo4);
		    list2.add(crInfo5);
		    list2.add(crInfo6);
		    list2.add(crInfo7);
		}

		adapter=new choiceOfResearchAdapter(context, list2);
		lv_question.setAdapter(adapter);
		return convertView;
	}

}
