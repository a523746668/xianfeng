package com.qingyii.hxt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qingyii.hxt.R;
import com.qingyii.hxt.pojo.DitiChuangg;
import com.qingyii.hxt.pojo.Examination;
import com.qingyii.hxt.pojo.ExaminationPapers;
import com.qingyii.hxt.util.EmptyUtil;
import com.zhf.android_viewflow.ViewFlow;

import java.util.ArrayList;
import java.util.List;

public class DatiViewflowAdapter extends BaseAdapter {
    private Context context;
//	private ArrayList<Question> list;
//	private ArrayList<ExaminationPapers> lists;
    /**
     * 当前考卷索引
     */
    private int flag;
    private ViewFlow viewflow;
    private Examination examination;

    private List<ExaminationPapers.Question> list;

    private int mType = 0;//1:练习模式 2：考试模式
//	private int positionId;
//	private ArrayList<DitiItem> list2;


//	public DatiViewflowAdapter(Context context,
//			ArrayList<Question> list, ViewFlow viewflow,
//			ArrayList<ExaminationPapers> lists, int flag,
//			Examination examination){
//		
//		this.context = context;
//		this.list = list;
//		this.viewflow = viewflow;
//		this.lists = lists;
//		this.flag = flag;
//		this.examination = examination;
//		
//	}

    public DatiViewflowAdapter(Context context, List<ExaminationPapers.Question> list) {
        this.context = context;
        this.list = list;
    }

    public DatiViewflowAdapter(Context context, List<ExaminationPapers.Question> list, int type) {
        this.context = context;
        this.list = list;
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
//		positionId=position;
//		return positionId;
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final DitiAdapter adapter;

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.viewflow_item, null);
            viewHolder.viewflow_item_title_l = LayoutInflater.from(context).inflate(R.layout.viewflow_item_title, null);
            viewHolder.viewflow_item_title = (TextView) viewHolder.viewflow_item_title_l.findViewById(R.id.viewflow_item_title);
            viewHolder.viewflow_item_list = (ListView) convertView.findViewById(R.id.viewflow_item_list);

            viewHolder.viewflow_item_list.addHeaderView(viewHolder.viewflow_item_title_l);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

           int timu=position+1;
        if (EmptyUtil.IsNotEmpty(list.get(position).getQtype())) {
            if ("multi".equals(list.get(position).getQtype())) {
                viewHolder.viewflow_item_title.setText(timu+"/"+list.size()+"(多选题)"
                        + list.get(position).getQuestion());
            } else if ("single".equals(list.get(position).getQtype())) {
                viewHolder.viewflow_item_title.setText(timu+"/"+list.size()+"(单选题)"
                        + list.get(position).getQuestion());
            } else if ("judge".equals(list.get(position).getQtype())) {
                viewHolder.viewflow_item_title.setText(timu+"/"+list.size()+"(判断题)"
                        + list.get(position).getQuestion());
            }
        }

        //实例化适配器
        ExaminationPapers.Question question = list.get(position);

        if (question != null && question.getQtype().equals("judge")) {  //初始化选项

            ExaminationPapers.Option option_wrong = new ExaminationPapers.Option("0", "错误");
            ExaminationPapers.Option option_right = new ExaminationPapers.Option("1", "正确");

            if (question.getAnswers().equals("0")) {  //方便后面计算答案是否正确  DatiChuangguanActivity.panduan()
                option_wrong.setIsanswer(1);
                option_right.setIsanswer(0);
            } else if (question.getAnswers().equals("1")) {
                option_wrong.setIsanswer(0);
                option_right.setIsanswer(1);
            }


            if (question.getOptions().size() > 0) { //如果有两个选项了 就不往里面增加选项了
                question.getOptions().clear();
            }

            question.getOptions().add(option_wrong);
            question.getOptions().add(option_right);


        }

        adapter = new DitiAdapter(context, question, mType);

        viewHolder.viewflow_item_list.setAdapter(adapter);
        final String a = list.get(position).getQtype();

//		list2=list.get(position).getList();

        //关联题目

        //listview点击事件
        viewHolder.viewflow_item_list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int myposition, long id) {
                // TODO Auto-generated method stub

                String itemID = list.get(position).getOptions().get(myposition - 1).getId();
                // get Option Id  //备份之前是获取id 接口不确定是获取id 还是位置

                //String itemID= String.valueOf(myposition);

                ExaminationPapers.Question question = list.get(position);


                //System.out.println("qtype----"+ a);

                if (("single".equals(a)) || ("judge".equals(a))) {
                    if (question.getSelection().contains(itemID)) {
                        question.getSelection().remove(itemID);
                    } else {
                        question.getSelection().clear();
                        question.getSelection().add(itemID);

                    }
                } else if ("multi".equals(a)) {
                    if (question.getSelection().contains(itemID)) {
                        question.getSelection().remove(itemID);
                    } else {
                        question.getSelection().add(itemID);
                    }

                }

//				for (String xx:question.getSelection()  //打出当前选择的选项
//						) {
//
//					System.out.println("select----"+xx);
//
//				}

                adapter.F5();

            }

        });


        return convertView;
    }


    static class ViewHolder {
        View viewflow_item_title_l;
        TextView viewflow_item_title;
        ListView viewflow_item_list;
    }


}
