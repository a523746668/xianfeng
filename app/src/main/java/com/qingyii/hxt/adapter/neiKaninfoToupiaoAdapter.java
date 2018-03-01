package com.qingyii.hxt.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.util.EmptyUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

//import com.ab.util.dct.IFDCT;
//import com.ab.view.progress.AbHorizontalProgressBar;

public class neiKaninfoToupiaoAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<neiKanInfoInfo> list;
	HashMap<String,Boolean> states=new HashMap<String,Boolean>();
	private int selectedIndex;
	private int maxSelect=1;
	private ArrayList<Integer> listSelect;
	/**
	 * voteCount投票总数变量
	 */
	public  int voteCount=0;
	public int getSelectedIndex() {
		return selectedIndex;
	}
	public void addSelectIndex(int position) {
		this.selectedIndex = position;
		if(maxSelect>1){
			Integer select = Integer.valueOf(position);
			if (listSelect.contains(select)) {
				listSelect.remove(select);
			}else {
				if (listSelect.size()>=maxSelect) {
					return;
				}
				listSelect.add(select);
			};
		}
		notifyDataSetChanged();
	}
	
	public ArrayList<Integer> getSelectList() {
		if(maxSelect==1) {
			ArrayList<Integer> templist = new ArrayList<Integer>();
			templist.add(selectedIndex);
			return templist;
		}
		return listSelect;
	}
	
	public void setMaxSelect(int max) {
		maxSelect = (max<=0)?1:max;
	}
	
	public neiKaninfoToupiaoAdapter(Context context,
			ArrayList<neiKanInfoInfo> list) {
		super();
		this.context = context;
		this.list = list;
		listSelect = new ArrayList<Integer>();
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		neiKanInfoInfo info=list.get(position);
		
		convertView = LayoutInflater.from(context).inflate(R.layout.neikaninfo_toupiao_item, null);
//		voteinfoList=list.get(position).getToupiaoList();
		// ProgressBar进度控制
	    ProgressBar mAbProgressBar=(ProgressBar) convertView.findViewById(R.id.horizontalProgressBar);
	    final RadioButton rb_cbinfo_toupiao = (RadioButton) convertView.findViewById(R.id.rb_cbinfo_toupiao);
	    if(maxSelect>1) {
	    	rb_cbinfo_toupiao.setButtonDrawable(R.drawable.checkbox_selector);
	    }
		TextView tv_neikaninfo_toupiao = (TextView) convertView.findViewById(R.id.tv_neikaninfo_toupiao);
		TextView tv_toupiaoshu=(TextView) convertView.findViewById(R.id.tv_toupiaoshu);
		TextView tv_baifenbi=(TextView) convertView.findViewById(R.id.tv_baifenbi);
		RelativeLayout rl_toupiao=(RelativeLayout) convertView.findViewById(R.id.rl_toupiao);
		// cb_cbinfo_toupiao.setText(list.get(position).getNeiKanInfoInfo_toupiaoContent());
	
		tv_neikaninfo_toupiao.setText(list.get(position).getInfodesc());
		float votecountforone=0;
		if (EmptyUtil.IsNotEmpty(list.get(position).getVotecount())) {
			tv_toupiaoshu.setText(list.get(position).getVotecount());
			votecountforone=Float.parseFloat(list.get(position).getVotecount());
		}else {
			tv_toupiaoshu.setText("0");
		}
		float price=votecountforone/voteCount *100;
		if (price==0) {
			tv_baifenbi.setText("(0%)");
		}else {
			DecimalFormat decimalFormat=new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
			String strPrice=decimalFormat.format(price);//format 返回的是字符串
			if (voteCount!=0) {
				tv_baifenbi.setText("("+strPrice+"%)");
			}
		}
		
		if (EmptyUtil.IsNotEmpty(tv_toupiaoshu.getText().toString())) {
			mAbProgressBar.setProgress(Integer.parseInt(tv_toupiaoshu.getText().toString()));
		}
		
        if (info.getFlag()==1) {
        	
			rb_cbinfo_toupiao.setVisibility(View.GONE);
			rl_toupiao.setVisibility(View.VISIBLE);
		}
        if ("1".equals(list.get(position).getIsvoted())) {
			rb_cbinfo_toupiao.setVisibility(View.GONE);
			rl_toupiao.setVisibility(View.VISIBLE);
			
		}
		if ((maxSelect>1&&listSelect.contains(Integer.valueOf(position))) ||
				maxSelect==1&&selectedIndex==position) {
			rb_cbinfo_toupiao.setChecked(true);
		} else {
			rb_cbinfo_toupiao.setChecked(false);
		}
		rb_cbinfo_toupiao.setFocusable(false);
//        rb_cbinfo_toupiao.setOnClickListener(new NotifyView.OnClickListener() {
//            
//            public void onClick(NotifyView v) {
//                
//                //重置，确保最多只有一项被选中  
//                for(String key:states.keySet()){  
//                    states.put(key, false);  
//                                      }                 states.put(String.valueOf(position), rb_cbinfo_toupiao.isChecked());  
//                neiKaninfoToupiaoAdapter.this.notifyDataSetChanged();  
//            }  
//        });  
//       
//        boolean res=false;  
//        if(states.get(String.valueOf(position)) == null || states.get(String.valueOf(position))== false){  
//         res=false;  
//            states.put(String.valueOf(position), false);  
//        }  
//      else  
//           res = true;  
//         
//        rb_cbinfo_toupiao.setChecked(res);  
		return convertView;
	}


}
