package com.qingyii.hxt;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxt.adapter.DatiViewflowAdapter;
import com.qingyii.hxt.pojo.DitiChuangg;
import com.qingyii.hxt.pojo.DitiItem;
import com.qingyii.hxt.pojo.Examination;
import com.qingyii.hxt.pojo.ExaminationPapers;
import com.zhf.android_viewflow.ViewFlow;
import com.zhf.android_viewflow.ViewFlow.ViewSwitchListener;

import java.util.ArrayList;
import java.util.List;

//import com.ab.fragment.AbDialogFragment.AbDialogOnLoadListener;
//import com.ab.util.AbDialogUtil;

public class KaoshiCheckErrorActivity extends AbBaseActivity {

    private ImageView iv_back, iv_rank;
    private TextView tv_title;
    private TextView mTextRightAnswer;
    /**
     * 当前考卷
     */
    private ExaminationPapers examinationPapers;
    private Examination examination;
    //	private ExamContentFragment fragment;
    public ViewFlow kaoshi_viewflow;

    private List<ExaminationPapers.Question> errQuestonList; // = new ArrayList<DitiChuangg>();

    private DatiViewflowAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kaoshi_checkerror);

//		type = getIntent().getStringExtra("type");
        errQuestonList = (List<ExaminationPapers.Question>) getIntent().getSerializableExtra("errQuestionList");

        examination = (Examination) getIntent().getSerializableExtra("examination");

        initUI();
//		  //注册广播
//        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(
//                Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    private void initUI() {

        kaoshi_viewflow = (ViewFlow) findViewById(R.id.kaoshi_viewflow);

        adapter = new DatiViewflowAdapter(this, errQuestonList);
        kaoshi_viewflow.setAdapter(adapter, 0);

//		myAdapter=new DatichuanggAdapter(this, list,kaoshi_viewflow, lists, flag,examination);
//		
//		kaoshi_viewflow.setAdapter(myAdapter, 0);

        //viewflow滑动监听
        kaoshi_viewflow.setOnViewSwitchListener(new ViewSwitchListener() {
            @Override
            public void onSwitched(View view, int position) {
                if (KaoshiCheckErrorActivity.this.examination.getShowanswer().equals("1")) {
                    KaoshiCheckErrorActivity.this.setRightAnswer(position);
                }
            }
        });

        tv_title = (TextView) findViewById(R.id.title_kaoshicontent);
        tv_title.setText("错题");
        mTextRightAnswer = (TextView) findViewById(R.id.text_rightanswer);
        //加粗中文字体
//		tv_title.getPaint().setFakeBoldText(true);
        iv_back = (ImageView) findViewById(R.id.iv_back_kaoshicontent);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        if (this.examination.getShowanswer().equals("1")) {
            setRightAnswer(0);
        }
    }

    private void setRightAnswer(int position) {
        if (position < KaoshiCheckErrorActivity.this.errQuestonList.size()) {
            ExaminationPapers.Question dc = KaoshiCheckErrorActivity.this.errQuestonList.get(position);
            List<ExaminationPapers.Option> items = dc.getOptions();
            String str = "";
            int size = items.size();
            for (int i = 0; i < size; i++) {
                ExaminationPapers.Option item = items.get(i);
                if (item.getIsanswer() == 1) {
                    if (("1".equals(dc.getQtype()))
                            || ("2".equals(dc.getQtype()))) {
                        str += getWordFromPosition(i) + ",";
                    } else {
                        str += item.getOption() + ",";
                    }
                }
            }
            if (str.length() > 0) {
                str = str.substring(0, str.length() - 1);
            }
            mTextRightAnswer.setText("正确答案：" + str);
        }

    }

    private String getWordFromPosition(int position) {
        switch (position) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "G";
            case 7:
                return "H";
        }
        return "";
    }

    public void onBackPressed() {
        KaoshiCheckErrorActivity.this.finish();
    }
}
