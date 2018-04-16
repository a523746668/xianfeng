package com.qingyii.hxtz;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.qingyii.hxtz.adapter.DatiViewflow2Adapter;
import com.qingyii.hxtz.pojo.ExamDetails;
import com.qingyii.hxtz.pojo.ExaminationPapers;
import com.zhf.android_viewflow.ViewFlow;
import com.zhf.android_viewflow.ViewFlow.ViewSwitchListener;

import java.util.List;

//import com.ab.fragment.AbDialogFragment.AbDialogOnLoadListener;
//import com.ab.util.AbDialogUtil;

public class KaoshiCheckError2Activity extends AbBaseActivity {

    private ImageView iv_back, iv_rank;
    private TextView tv_title;
    private TextView mTextRightAnswer;
    /**
     * 当前考卷
     */
    //private ExaminationPapers examinationPapers;
    //private Examination examination;
    private ExamDetails.DataBean edDataBean;
    //	private ExamContentFragment fragment;
    public ViewFlow kaoshi_viewflow;

    //private ArrayList<ExaminationPapers.Question> errQuestionList;
    private List<ExaminationPapers.Question> errQuestonList; // = new ArrayList<DitiChuangg>();

    private DatiViewflow2Adapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kaoshi_checkerror);

        //type = getIntent().getStringExtra("type");
        errQuestonList = getIntent().getParcelableArrayListExtra("errQuestionList");
        edDataBean = getIntent().getParcelableExtra("edDataBean");

        initUI();
        //注册广播
        //registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    private void initUI() {

        kaoshi_viewflow = (ViewFlow) findViewById(R.id.kaoshi_viewflow);

        adapter = new DatiViewflow2Adapter(this, edDataBean, errQuestonList);
        kaoshi_viewflow.setAdapter(adapter, 0);

        //myAdapter=new DatichuanggAdapter(this, list,kaoshi_viewflow, lists, flag,examination);
        //kaoshi_viewflow.setAdapter(myAdapter, 0);

        //viewflow滑动监听
        kaoshi_viewflow.setOnViewSwitchListener(new ViewSwitchListener() {
            @Override
            public void onSwitched(View view, int position) {
//                if (KaoshiCheckError2Activity.this.examination.getShowanswer() == 1)
//                    KaoshiCheckError2Activity.this.setRightAnswer(position);
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
//        if (this.examination.getShowanswer() == 1) {
//            setRightAnswer(0);
//        }
    }

//    private void setRightAnswer(int position) {
//        if (position < KaoshiCheckError2Activity.this.errQuestonList.size()) {
//            ExaminationPapers.Question dc = KaoshiCheckError2Activity.this.errQuestonList.get(position);
//            List<ExaminationPapers.Option> items = dc.getOptions();
//            String str = "";
//            int size = items.size();
//            for (int i = 0; i < size; i++) {
//                ExaminationPapers.Option item = items.get(i);
//                if (item.getIsanswer() == 1) {
//                    if (("1".equals(dc.getQtype())) || ("2".equals(dc.getQtype()))) {
//                        str += getWordFromPosition(i) + ",";
//                    } else {
//                        str += item.getOption() + ",";
//                    }
//                }
//            }
//            if (str.length() > 0) {
//                str = str.substring(0, str.length() - 1);
//            }
//            mTextRightAnswer.setText("正确答案：" + str);
//        }
//    }

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
        KaoshiCheckError2Activity.this.finish();
    }
}
