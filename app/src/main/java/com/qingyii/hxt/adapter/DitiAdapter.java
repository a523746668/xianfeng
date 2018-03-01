package com.qingyii.hxt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.pojo.ExaminationPapers;

import java.util.List;

public class DitiAdapter extends BaseAdapter{
	
	private Context context;
//	private ArrayList<Question> list;
//	private ArrayList<ExaminationPapers> lists;
	private List<ExaminationPapers.Option> list;
//	ArrayList<DitiChuangg> list2;
	private ExaminationPapers.Question question=null;
	private int mType = 0;
	
	
	public DitiAdapter(Context context,ExaminationPapers.Question question){
		this.context=context;
		this.list=question.getOptions();
		this.question=question;
	}
	
	public DitiAdapter(Context context,ExaminationPapers.Question question,int type){
		this.context=context;
		this.list=question.getOptions();
		this.question=question;
		this.mType = type;
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
		// TODO Auto-generated method stub
		
		convertView=LayoutInflater.from(context).inflate(R.layout.datichuangg_item, null);
		
		ImageView datichuangg_item_xziv=(ImageView) convertView.findViewById(R.id.datichuangg_item_xziv);
		TextView datichuangg_item_xztv=(TextView) convertView.findViewById(R.id.datichuangg_item_xztv);
		
		
		
		datichuangg_item_xztv.setText(list.get(position).getOption());
		
	if(("single".equals(question.getQtype()))||("multi".equals(question.getQtype()))){
		if(position==0){
			datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_aa);
//			datichuangg_item_xziv.setBackground(context.getResources().getDrawable(R.drawable.select_aa));
			
		}else if(position==1){
			datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_bb);
			
		}else if(position==2){
			datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_cc);
			
		}else if(position==3){
			datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_dd);
			
		}else if(position==4){
			datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_ee);
			
		}else if(position==5){
			datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_ff);
			
		}else if(position==6){
			datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_gg);
			
		}else if(position==7){
			datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_hh);
			
		}
		
	}else if("judge".equals(question.getQtype())){

		if(position==0){
			if("正确".equals(list.get(position).getOption())){
				datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_yes);
			}else if("错误".equals(list.get(position).getOption())){
				datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_no);
			}
		}else if(position==1){
			if("正确".equals(list.get(position).getOption())){
				datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_yes);
			}else if("错误".equals(list.get(position).getOption())){
				datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_no);
			}
		}
		
	}
		
		
		
		
		
		
		
		
		
		
		if(question!=null){

			//判断是否选中
			List<String> selection= question.getSelection();

			int red = ContextCompat.getColor(context,R.color.red);

			 if(selection.contains(list.get(position).getId())){
			
				 if(("single".equals(question.getQtype()))||("multi".equals(question.getQtype()))){

					 
					 if(position==0){
							datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_ah);
						 
							datichuangg_item_xztv.setTextColor(red);
							
						}else if(position==1){
							datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_bh);
							datichuangg_item_xztv.setTextColor(red);
							
						}else if(position==2){
							datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_ch);
							datichuangg_item_xztv.setTextColor(red);
							
						}else if(position==3){
							datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_dh);
							datichuangg_item_xztv.setTextColor(red);
							
						}else if(position==4){
							datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_eh);
							datichuangg_item_xztv.setTextColor(red);
							
						}else if(position==5){
							datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_fh);
							datichuangg_item_xztv.setTextColor(red);
							
						}else if(position==6){
							datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_gh);
							datichuangg_item_xztv.setTextColor(red);
							
						}else if(position==7){
							datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_hhh);
							datichuangg_item_xztv.setTextColor(red);
							
						}
					 
				 }else if("judge".equals(question.getQtype())){
					 if(position==0){
							if("正确".equals(list.get(position).getOption())){
								datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_yesh);
								datichuangg_item_xztv.setTextColor(red);
							}else if("错误".equals(list.get(position).getOption())){
								datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_noh);
								datichuangg_item_xztv.setTextColor(red);
							}
						}else if(position==1){
							if("正确".equals(list.get(position).getOption())){
								datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_yesh);
								datichuangg_item_xztv.setTextColor(red);
							}else if("错误".equals(list.get(position).getOption())){
								datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_noh);
								datichuangg_item_xztv.setTextColor(red);
							}
						}
						
					 
				 }
				 if (mType==1&&list.get(position).getIsanswer()==0) {//用户选择的选项提示错误
					 //datichuangg_item_xziv.setBackground(context.getResources().getDrawable(R.drawable.select_noh));
					 //datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.black));
				 }
			}
				 //不管用户有没有选择这个选项，提示正确答案
				 if (mType==1&&list.get(position).getIsanswer()==1) {
					 //datichuangg_item_xziv.setBackground(context.getResources().getDrawable(R.drawable.select_yesh));
					 //datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.black));
				 }
		}
		
		

		
		return convertView;
	}

	
	public void F5() {
		notifyDataSetChanged();
	}
	
	

	
	
}
