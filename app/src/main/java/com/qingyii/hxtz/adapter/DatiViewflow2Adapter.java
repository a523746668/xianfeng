package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.ExamDetails;
import com.qingyii.hxtz.pojo.ExaminationPapers;
import com.qingyii.hxtz.util.EmptyUtil;

import java.util.List;

public class DatiViewflow2Adapter extends BaseAdapter {
    private Context context;
    private ExamDetails.DataBean edDataBean;
    private List<ExaminationPapers.Question> errQuestonList;

    public DatiViewflow2Adapter(Context context, ExamDetails.DataBean edDataBean, List<ExaminationPapers.Question> errQuestonList) {
        this.context = context;
        this.edDataBean = edDataBean;
        this.errQuestonList = errQuestonList;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return errQuestonList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return errQuestonList.get(position);
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
        final Diti2Adapter adapter;

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.viewflow_item, null);
            viewHolder.viewflow_item_title_l = LayoutInflater.from(context).inflate(R.layout.viewflow_item_title, null);
            viewHolder.viewflow_item_title = (TextView) viewHolder.viewflow_item_title_l.findViewById(R.id.viewflow_item_title);
            viewHolder.viewflow_item_list = (ListView) convertView.findViewById(R.id.viewflow_item_list);
            viewHolder.viewflow_item_list_footer = View.inflate(context, R.layout.viewflow_item_list_footer, null);
            viewHolder.viewflow_item_list_footer_xztv = (TextView) viewHolder.viewflow_item_list_footer.findViewById(R.id.viewflow_item_list_footer_xztv);
            if (edDataBean.getShowanswer() == 1)
                viewHolder.viewflow_item_list.addFooterView(viewHolder.viewflow_item_list_footer);
            viewHolder.viewflow_item_list.addHeaderView(viewHolder.viewflow_item_title_l);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (EmptyUtil.IsNotEmpty(errQuestonList.get(position).getQtype()))
            if ("multi".equals(errQuestonList.get(position).getQtype()))
                viewHolder.viewflow_item_title.setText("(多选题)" + errQuestonList.get(position).getQuestion());
            else if ("single".equals(errQuestonList.get(position).getQtype()))
                viewHolder.viewflow_item_title.setText("(单选题)" + errQuestonList.get(position).getQuestion());
            else if ("judge".equals(errQuestonList.get(position).getQtype()))
                viewHolder.viewflow_item_title.setText("(判断题)" + errQuestonList.get(position).getQuestion());

        //实例化适配器
        ExaminationPapers.Question question = errQuestonList.get(position);

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

            if (question.getOptions().size() > 0)  //如果有两个选项了 就不往里面增加选项了
                question.getOptions().clear();

            question.getOptions().add(option_wrong);
            question.getOptions().add(option_right);
        }

        String aItemValue = "";

        Log.e("选项  长度", question.getOptions().size() + "");

        for (int i = 0; i < question.getOptions().size(); i++) {
            //判断是不是正确答案
            if (question.getOptions().get(i).getIsanswer() == 1) {

                if (("single".equals(question.getQtype())) || ("multi".equals(question.getQtype()))) {
                    switch (i) {
                        case 0:
                            aItemValue += "A ";
                            break;
                        case 1:
                            aItemValue += "B ";
                            break;
                        case 2:
                            aItemValue += "C ";
                            break;
                        case 3:
                            aItemValue += "D ";
                            break;
                        case 4:
                            aItemValue += "E ";
                            break;
                        case 5:
                            aItemValue += "F ";
                            break;
                        case 6:
                            aItemValue += "G ";
                            break;
                        case 7:
                            aItemValue += "H ";
                            break;
                    }
                } else if ("judge".equals(question.getQtype())) {
                    if (i == 0) {
                        if ("正确".equals(question.getOptions().get(i).getOption()))
                            aItemValue += "正确 ";
                        else if ("错误".equals(question.getOptions().get(i).getOption()))
                            aItemValue += "错误 ";
                    } else if (i == 1) {
                        if ("正确".equals(question.getOptions().get(i).getOption()))
                            aItemValue += "正确 ";
                        else if ("错误".equals(question.getOptions().get(i).getOption()))
                            aItemValue += "错误 ";
                    }
                }
            }
        }

        viewHolder.viewflow_item_list_footer_xztv.setText(aItemValue + "");

        adapter = new Diti2Adapter(context, question);
        viewHolder.viewflow_item_list.setAdapter(adapter);
//        final String a = errQuestonList.get(position).getQtype();
//
//        //listview点击事件
//        viewHolder.viewflow_item_list.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, NotifyView view,
//                                    int myposition, long id) {
//                // TODO Auto-generated method stub
//                String itemID = errQuestonList.get(position).getOptions().get(myposition).getId();
//                // get Option Id  //备份之前是获取id 接口不确定是获取id 还是位置
//                //String itemID= String.valueOf(myposition);
//                ExaminationPapers.Question question = errQuestonList.get(position);
//                if (("single".equals(a)) || ("judge".equals(a))) {
//                    if (question.getSelection().contains(itemID)) {
//                        question.getSelection().remove(itemID);
//                    } else {
//                        question.getSelection().clear();
//                        question.getSelection().add(itemID);
//                    }
//                } else if ("multi".equals(a)) {
//                    if (question.getSelection().contains(itemID)) {
//                        question.getSelection().remove(itemID);
//                    } else {
//                        question.getSelection().add(itemID);
//                    }
//                }
//                adapter.F5();
//            }
//
//        });

        return convertView;
    }

    static class ViewHolder {
        View viewflow_item_title_l;
        TextView viewflow_item_title;
        ListView viewflow_item_list;
        View viewflow_item_list_footer;
        TextView viewflow_item_list_footer_xztv;
    }
}
