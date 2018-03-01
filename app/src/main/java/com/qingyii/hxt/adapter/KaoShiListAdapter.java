package com.qingyii.hxt.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.kaoshiContentActivity;
import com.qingyii.hxt.pojo.Question;
import com.qingyii.hxt.util.EmptyUtil;
import com.zhf.android_viewflow.ViewFlow;

import java.util.ArrayList;

/**
 * 考试题目列表数据适配器
 * 
 * @author shelia
 * 
 */
public class KaoShiListAdapter extends BaseAdapter {
	private kaoshiContentActivity context;
	private ArrayList<Question> list;
	private ViewFlow viewflow;
	public KaoShiListAdapter(kaoshiContentActivity context, ArrayList<Question> list,ViewFlow viewflow) {
		this.context = context;
		this.list = list;
		this.viewflow=viewflow;
	}

	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View contentview, ViewGroup arg2) {
		final ViewHolder holder = new ViewHolder();
		// MyListView kaoshi_list;
//		if (contentview == null) {
			contentview = LayoutInflater.from(context).inflate(
					R.layout.kaoshi_list_item_02, null);
			holder.kaoshi_title = (TextView) contentview
					.findViewById(R.id.kaoshi_title);
			holder.q_a_context = (TextView) contentview
					.findViewById(R.id.q_a_context);
			holder.q_b_context = (TextView) contentview
					.findViewById(R.id.q_b_context);
			holder.q_c_context = (TextView) contentview
					.findViewById(R.id.q_c_context);
			holder.q_d_context = (TextView) contentview
					.findViewById(R.id.q_d_context);
			holder.a_ll = (LinearLayout) contentview.findViewById(R.id.a_ll);
			holder.b_ll = (LinearLayout) contentview.findViewById(R.id.b_ll);
			holder.c_ll = (LinearLayout) contentview.findViewById(R.id.c_ll);
			holder.d_ll = (LinearLayout) contentview.findViewById(R.id.d_ll);
			holder.item_ok=(Button) contentview.findViewById(R.id.item_ok);
			holder.q_a=(ImageView) contentview.findViewById(R.id.q_a);
			holder.q_b=(ImageView) contentview.findViewById(R.id.q_b);
			holder.q_c=(ImageView) contentview.findViewById(R.id.q_c);
			holder.q_d=(ImageView) contentview.findViewById(R.id.q_d);
			holder.quest_type_01=(LinearLayout) contentview.findViewById(R.id.quest_type_01);
			holder.quest_type_02=(LinearLayout) contentview.findViewById(R.id.quest_type_02);
			holder.q_yes_context=(TextView) contentview.findViewById(R.id.q_yes_context);
			holder.q_no_context=(TextView) contentview.findViewById(R.id.q_no_context);
			holder.yes_ll=(LinearLayout) contentview.findViewById(R.id.yes_ll);
			holder.no_ll=(LinearLayout) contentview.findViewById(R.id.no_ll);
//			contentview.setTag(holder);
//		}
//		 else{ holder = (ViewHolder) contentview.getTag(); }
		
		
		if("3".equals(list.get(position).getRedios())){
			//判断题目
			holder.quest_type_02.setVisibility(View.VISIBLE);
			holder.quest_type_01.setVisibility(View.GONE);
			holder.yes_ll.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					holder.q_yes_context.setTextColor(context.getResources()
							.getColor(R.color.red));
					holder.q_no_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.selectindext=1;
					list.get(position).setSelectindext(1);
				}
			});
			holder.no_ll.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					holder.q_no_context.setTextColor(context.getResources()
							.getColor(R.color.red));
					holder.q_yes_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.selectindext=2;
					list.get(position).setSelectindext(2);
				}
			});
		}else{
			//选择题：单选和多选
			holder.quest_type_01.setVisibility(View.VISIBLE);
			holder.quest_type_02.setVisibility(View.GONE);
			holder.q_a_context.setText(list.get(position).getAnswer1());
			holder.q_b_context.setText(list.get(position).getAnswer2());
			holder.q_c_context.setText(list.get(position).getAnswer3());
			holder.q_d_context.setText(list.get(position).getAnswer4());
			holder.a_ll.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					//单选
					if("1".equals(list.get(position).getRedios())){
						holder.selectindext=1;
						list.get(position).setSelectindext(1);
						holder.q_a_context.setTextColor(context.getResources()
								.getColor(R.color.red));
						holder.q_b_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_c_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_d_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_a.setImageResource(R.mipmap.select_a);
						holder.q_b.setImageResource(R.mipmap.select_bb);
						holder.q_c.setImageResource(R.mipmap.select_cc);
						holder.q_d.setImageResource(R.mipmap.select_dd);
					}else if("2".equals(list.get(position).getRedios())){
						//多选
						if(EmptyUtil.IsNotEmpty(holder.checkBoxSelectindext)){
							if(holder.checkBoxSelectindext.contains("1")){
								holder.q_a_context.setTextColor(context.getResources()
										.getColor(R.color.black));
								holder.q_a.setImageResource(R.mipmap.select_aa);
								if(holder.checkBoxSelectindext.length()>1){
									holder.checkBoxSelectindext=holder.checkBoxSelectindext.replace("1", "");
									list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
								}else{
									holder.checkBoxSelectindext="";
									list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
								}
							}else{
								holder.q_a_context.setTextColor(context.getResources()
										.getColor(R.color.red));
								holder.q_a.setImageResource(R.mipmap.select_a);
								holder.checkBoxSelectindext=holder.checkBoxSelectindext+",1";
								list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
							}
						}else{
							holder.q_a_context.setTextColor(context.getResources()
									.getColor(R.color.red));
							holder.q_a.setImageResource(R.mipmap.select_a);
							holder.checkBoxSelectindext="1";
							list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
						}
					}
				}
			});
			holder.b_ll.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					//单选
					if("1".equals(list.get(position).getRedios())){
						holder.selectindext=2;
						list.get(position).setSelectindext(2);
						holder.q_b_context.setTextColor(context.getResources()
								.getColor(R.color.red));
						holder.q_a_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_c_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_d_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_a.setImageResource(R.mipmap.select_aa);
						holder.q_b.setImageResource(R.mipmap.select_b);
						holder.q_c.setImageResource(R.mipmap.select_cc);
						holder.q_d.setImageResource(R.mipmap.select_dd);
					}else if("2".equals(list.get(position).getRedios())){
						//多选
						if(EmptyUtil.IsNotEmpty(holder.checkBoxSelectindext)){
							if(holder.checkBoxSelectindext.contains("2")){
								holder.q_b_context.setTextColor(context.getResources()
										.getColor(R.color.black));
								holder.q_b.setImageResource(R.mipmap.select_bb);
								if(holder.checkBoxSelectindext.length()>1){
									holder.checkBoxSelectindext=holder.checkBoxSelectindext.replace("2", "");
									list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
								}else{
									holder.checkBoxSelectindext="";
									list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
								}
							}else{
								holder.q_b_context.setTextColor(context.getResources()
										.getColor(R.color.red));
								holder.q_b.setImageResource(R.mipmap.select_b);
								holder.checkBoxSelectindext=holder.checkBoxSelectindext+",2";
								list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
							}
						}else{
							holder.q_b_context.setTextColor(context.getResources()
									.getColor(R.color.red));
							holder.q_b.setImageResource(R.mipmap.select_b);
							holder.checkBoxSelectindext="2";
							list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
						}
					}
				}
			});
			holder.c_ll.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					//单选
					if("1".equals(list.get(position).getRedios())){
						holder.selectindext=3;
						list.get(position).setSelectindext(3);
						holder.q_c_context.setTextColor(context.getResources()
								.getColor(R.color.red));
						holder.q_b_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_a_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_d_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_a.setImageResource(R.mipmap.select_aa);
						holder.q_b.setImageResource(R.mipmap.select_bb);
						holder.q_c.setImageResource(R.mipmap.select_c);
						holder.q_d.setImageResource(R.mipmap.select_dd);
					}else if("2".equals(list.get(position).getRedios())){
						//多选
						if(EmptyUtil.IsNotEmpty(holder.checkBoxSelectindext)){
							if(holder.checkBoxSelectindext.contains("3")){
								holder.q_c_context.setTextColor(context.getResources()
										.getColor(R.color.black));
								holder.q_d.setImageResource(R.mipmap.select_cc);
								if(holder.checkBoxSelectindext.length()>1){
									holder.checkBoxSelectindext=holder.checkBoxSelectindext.replace("3", "");
									list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
								}else{
									holder.checkBoxSelectindext="";
									list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
								}
							}else{
								holder.q_c_context.setTextColor(context.getResources()
										.getColor(R.color.red));
								holder.q_c.setImageResource(R.mipmap.select_c);
								holder.checkBoxSelectindext=holder.checkBoxSelectindext+",3";
								list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
							}
						}else{
							holder.q_c_context.setTextColor(context.getResources()
									.getColor(R.color.red));
							holder.q_c.setImageResource(R.mipmap.select_c);
							holder.checkBoxSelectindext="3";
							list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
						}
					}
				}
			});
			holder.d_ll.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					//单选
					if("1".equals(list.get(position).getRedios())){
						holder.selectindext=4;
						list.get(position).setSelectindext(4);
						holder.q_d_context.setTextColor(context.getResources()
								.getColor(R.color.red));
						holder.q_b_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_c_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_a_context.setTextColor(context.getResources()
								.getColor(R.color.black));
						holder.q_a.setImageResource(R.mipmap.select_aa);
						holder.q_b.setImageResource(R.mipmap.select_bb);
						holder.q_c.setImageResource(R.mipmap.select_cc);
						holder.q_d.setImageResource(R.mipmap.select_d);
					}else if("2".equals(list.get(position).getRedios())){
						//多选
						if(EmptyUtil.IsNotEmpty(holder.checkBoxSelectindext)){
							if(holder.checkBoxSelectindext.contains("4")){
								holder.q_d_context.setTextColor(context.getResources()
										.getColor(R.color.black));
								holder.q_d.setImageResource(R.mipmap.select_dd);
								if(holder.checkBoxSelectindext.length()>1){
									holder.checkBoxSelectindext=holder.checkBoxSelectindext.replace("4", "");
									list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
								}else{
									holder.checkBoxSelectindext="";
									list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
								}
							}else{
								holder.q_d_context.setTextColor(context.getResources()
										.getColor(R.color.red));
								holder.q_d.setImageResource(R.mipmap.select_d);
								holder.checkBoxSelectindext=holder.checkBoxSelectindext+",4";
								list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
							}
						}else{
							holder.q_d_context.setTextColor(context.getResources()
									.getColor(R.color.red));
							holder.q_d.setImageResource(R.mipmap.select_d);
							holder.checkBoxSelectindext="4";
							list.get(position).setCheckBoxSelectindext(holder.checkBoxSelectindext);
						}
					}
				}
			});
		}
		
		holder.item_ok.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("unused")
			@Override
			public void onClick(View arg0) {
				if("1".equals(list.get(position).getRedios())){
					//单选题
					checkAnsewer(holder.selectindext, position);
				}else if("2".equals(list.get(position).getRedios())){
				    //多选题
					if(EmptyUtil.IsNotEmpty(holder.checkBoxSelectindext)){
						checkAnswer2(holder.checkBoxSelectindext, position);
					}else{
						list.get(position).setAndserFalg(false);
					}
				}else if("3".equals(list.get(position).getRedios())){
					//判断题
					checkAnsewer(holder.selectindext, position);
				}
//				if(list.get(position).isAndserFalg()){
//					Toast.makeText(context, "恭喜你答对了", Toast.LENGTH_SHORT).show();
//				}else{
//					Toast.makeText(context, "对不起你答错了", Toast.LENGTH_SHORT).show();
//				}
				//点击确认
					
//					if(context.selectIndext<list.size()-1){
//						context.selectIndext+=1;
//						list.get(position).setShowOk(false);
//						holder.item_ok.setVisibility(NotifyView.GONE);
//						//ViewFlow切换到指定的屏
////						viewflow.snapToScreen(context.selectIndext);
//					}else if(context.selectIndext==list.size()-1){
//						list.get(position).setShowOk(false);
//						holder.item_ok.setVisibility(NotifyView.GONE);
//						Toast.makeText(context, "恭喜你答到最后一题！", Toast.LENGTH_SHORT).show();
//					}
			}
		});
		if("2".equals(list.get(position).getRedios())){
			holder.kaoshi_title.setText("(多选题)"+list.get(position).getQuestiondesc());
		}else if("1".equals(list.get(position).getRedios())){
			holder.kaoshi_title.setText("(单选题)"+list.get(position).getQuestiondesc());
		}else if("3".equals(list.get(position).getRedios())){
			holder.kaoshi_title.setText("(判断题)"+list.get(position).getQuestiondesc());
		}
		//赋值
		if(list.get(position).isShowOk()){
			holder.item_ok.setVisibility(View.VISIBLE);
		}else{
			holder.item_ok.setVisibility(View.GONE);
		}
		
		if("3".equals(list.get(position).getRedios())){
			//判断题
			if(list.get(position).getSelectindext()>0){
				if(list.get(position).getSelectindext()==1){
					holder.q_yes_context.setTextColor(context.getResources()
							.getColor(R.color.red));
					holder.q_no_context.setTextColor(context.getResources()
							.getColor(R.color.black));
				}
				if(list.get(position).getSelectindext()==2){
					holder.q_yes_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_no_context.setTextColor(context.getResources()
							.getColor(R.color.red));
				}
			}
		}else if("2".equals(list.get(position).getRedios())){
			//多选题
			if(EmptyUtil.IsNotEmpty(list.get(position).getCheckBoxSelectindext())){
				
					if(list.get(position).getCheckBoxSelectindext().contains("4")){
						holder.q_d_context.setTextColor(context.getResources()
								.getColor(R.color.red));
						holder.q_d.setImageResource(R.mipmap.select_d);
						
					}
					if(list.get(position).getCheckBoxSelectindext().contains("3")){
						holder.q_c_context.setTextColor(context.getResources()
								.getColor(R.color.red));
						holder.q_c.setImageResource(R.mipmap.select_c);
						
					}
					if(list.get(position).getCheckBoxSelectindext().contains("2")){
						holder.q_b_context.setTextColor(context.getResources()
								.getColor(R.color.red));
						holder.q_b.setImageResource(R.mipmap.select_b);
						
					}
					if(list.get(position).getCheckBoxSelectindext().contains("1")){
						holder.q_a_context.setTextColor(context.getResources()
								.getColor(R.color.red));
						holder.q_a.setImageResource(R.mipmap.select_a);
						
					}
					
				
			}
		}else if("1".equals(list.get(position).getRedios())){
			//单选题
			if(list.get(position).getSelectindext()>0){
				if(list.get(position).getSelectindext()==1){
					holder.q_a_context.setTextColor(context.getResources()
							.getColor(R.color.red));
					holder.q_b_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_c_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_d_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_a.setImageResource(R.mipmap.select_a);
					holder.q_b.setImageResource(R.mipmap.select_bb);
					holder.q_c.setImageResource(R.mipmap.select_cc);
					holder.q_d.setImageResource(R.mipmap.select_dd);
				}
				if(list.get(position).getSelectindext()==2){
					holder.q_a_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_b_context.setTextColor(context.getResources()
							.getColor(R.color.red));
					holder.q_c_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_d_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_a.setImageResource(R.mipmap.select_aa);
					holder.q_b.setImageResource(R.mipmap.select_b);
					holder.q_c.setImageResource(R.mipmap.select_cc);
					holder.q_d.setImageResource(R.mipmap.select_dd);
				}
				if(list.get(position).getSelectindext()==3){
					holder.q_a_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_b_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_c_context.setTextColor(context.getResources()
							.getColor(R.color.red));
					holder.q_d_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_a.setImageResource(R.mipmap.select_aa);
					holder.q_b.setImageResource(R.mipmap.select_bb);
					holder.q_c.setImageResource(R.mipmap.select_c);
					holder.q_d.setImageResource(R.mipmap.select_dd);
				}
				if(list.get(position).getSelectindext()==4){
					holder.q_a_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_b_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_c_context.setTextColor(context.getResources()
							.getColor(R.color.black));
					holder.q_d_context.setTextColor(context.getResources()
							.getColor(R.color.red));
					holder.q_a.setImageResource(R.mipmap.select_aa);
					holder.q_b.setImageResource(R.mipmap.select_bb);
					holder.q_c.setImageResource(R.mipmap.select_cc);
					holder.q_d.setImageResource(R.mipmap.select_d);
				}
			}
		}
		return contentview;
	}

	class ViewHolder {
		ImageView q_a,q_b,q_c,q_d;
		TextView kaoshi_title;
		TextView q_a_context;
		TextView q_b_context;
		TextView q_c_context;
		TextView q_d_context;
		LinearLayout a_ll, b_ll, c_ll, d_ll,yes_ll,no_ll;
		LinearLayout quest_type_02,quest_type_01;
		TextView q_yes_context,q_no_context;
		Button item_ok;
		/**
		 * 单选类型/判断题目选中索引
		 */
		int selectindext=0;
		/**
		 * 多选类型题目选择索引串,以逗号分开
		 */
		String checkBoxSelectindext;
	}
	/**
	 * 复选题判断选择是否正确
	 * @param checkboxStr
	 * @param postion
	 */
	private void checkAnswer2(String checkboxStr,int postion){
		boolean falg=true;
		if(EmptyUtil.IsNotEmpty(list.get(postion).getRightanswer1())){
			if(!checkboxStr.contains("1")){
				falg=false;
			}
		}
		if(EmptyUtil.IsNotEmpty(list.get(postion).getRightanswer2())){
			if(!checkboxStr.contains("2")){
				falg=false;
			}
		}
		if(EmptyUtil.IsNotEmpty(list.get(postion).getRightanswer3())){
			if(!checkboxStr.contains("3")){
				falg=false;
			}
		}
		if(EmptyUtil.IsNotEmpty(list.get(postion).getRightanswer4())){
			if(!checkboxStr.contains("4")){
				falg=false;
			}
		}
		//赋值
		list.get(postion).setAndserFalg(falg);
	}
	/**
	 * 单选题判断选择是否正确
	 * @param indext
	 */
	private void checkAnsewer(int indext,int position) {
		 switch (indext) {
		case 1:
			if(EmptyUtil.IsNotEmpty(list.get(position).getRightanswer1())){
				list.get(position).setAndserFalg(true);
			}
			break;
		case 2:
			if(EmptyUtil.IsNotEmpty(list.get(position).getRightanswer2())){
				list.get(position).setAndserFalg(true);
			}
			break;
		case 3:
			if(EmptyUtil.IsNotEmpty(list.get(position).getRightanswer3())){
				list.get(position).setAndserFalg(true);
			}
			break;
		case 4:
			if(EmptyUtil.IsNotEmpty(list.get(position).getRightanswer4())){
				list.get(position).setAndserFalg(true);
			}
			break;
		}
	}
}
