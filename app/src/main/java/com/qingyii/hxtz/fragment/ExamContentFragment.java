package com.qingyii.hxtz.fragment;

//import com.ab.fragment.AbFragment;
//import com.ab.http.AbHttpListener;
//import com.ab.http.AbRequestParams;
//import com.ab.util.AbToastUtil;


/**
 * 考试内容
 *
 * @author Administrator
 */
//public class ExamContentFragment extends Fragment {
//    private TextView tv_title, tv_type, tv_ok;
//    private ListView lv_choice;
//    private NotifyView view;
//    private kaoshiInfo kaoshiInfo;
//    private Activity mActivity;
//    private choiceOfQuestionAdapter mAdapter;
//    private List<questionInfo> list;
//    private List<choiceOfQuestionInfo> choiceList;
//    private Handler handler;
//    //当前的题目的题号
//    private static int currentPage = 0;
//
//    @Override
//    public NotifyView onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_question, null);
//        mActivity = getActivity();
//        kaoshiInfo = (kaoshiInfo) mActivity.getIntent().getSerializableExtra("kaoshidetail");
//        list = new ArrayList<questionInfo>();
//        choiceList = new ArrayList<choiceOfQuestionInfo>();
//        handler = new Handler(new Callback() {
//
//            @Override
//            public boolean handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 101:
//
//                        break;
//                    case 102:
//                        mAdapter.notifyDataSetChanged();
//                        break;
//                    case 105:
////                	while(currentPage == 0){
////    					tv_title.setText(list.get(currentPage+1).getTitle());
////    					tv_type.setText(list.get(currentPage+1).getQuestionType());
////    					//保存选择的选项
////    					holdResult();
////
//////    					getChoiceList(list.get(currentPage+1).getQuestionId());
////    					mAdapter = new choiceOfQuestionAdapter(mActivity, choiceList);
////    					lv_choice.setAdapter(mAdapter);
////    					if(currentPage == list.size()){
////    						showCommitDialog();
////    					}
////    				}
////    				currentPage++;
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
//        tv_title = (TextView) view.findViewById(R.id.title_question);
//        tv_type = (TextView) view.findViewById(R.id.type_question_item);
//        tv_ok = (TextView) view.findViewById(R.id.ok_btn_question);
//        lv_choice = (ListView) view.findViewById(R.id.lv_question);
//
//        setDataAndRegister();
//
//        return view;
//    }
//
//    private void setDataAndRegister() {
////		getQuestionList();
//        //静态数据
//        list.addAll(kaoshiInfo.getQuestions());
//        choiceList.addAll(kaoshiInfo.getQuestions().get(0).getChoice());
//        mAdapter = new choiceOfQuestionAdapter(mActivity, choiceList);
//        lv_choice.setAdapter(mAdapter);
//        tv_title.setText(list.get(0).getTitle());
//        System.out.println("标题：" + list.get(0).getTitle() + "类型 :" + list.get(0).getQuestionType());
//        tv_type.setText(list.get(0).getQuestionType());
//        tv_ok.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyView arg0) {
//                // TODO Auto-generated method stub
////				handler.sendEmptyMessage(105);
//            }
//        });
//    }
//
//
//    protected void showCommitDialog() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
//        dialog.setTitle("提交")
//                .setMessage("确认提交吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(mActivity, "提交完成，稍后将公布考试结果。", Toast.LENGTH_SHORT).show();
//                mActivity.finish();
//            }
//        }).setPositiveButton("取消", null).show();
//    }
//
//    protected void holdResult() {
//        // TODO Auto-generated method stub
//
//    }
//
//    /**
//     * 请求问题实体类的数据
//     */
//    private void getQuestionList() {
//        // TODO Auto-generated method stub
//        // 绑定参数
//        AbRequestParams params = new AbRequestParams();
//        params.put("kaoshiId", kaoshiInfo.getId());
//        // 下载网络数据
//        NetworkWeb web = NetworkWeb.newInstance(mActivity);
//        web.findLogList(params, new AbHttpListener() {
//
//            @Override
//            public void onSuccess(List<?> newList) {
//                list.clear();
//                if (newList != null && newList.size() > 0) {
//                    list.addAll((List<questionInfo>) newList);
//                    newList.clear();
////			                handler.sendEmptyMessage(101);
//                }
//
//            }
//
//            @Override
//            public void onFailure(String content) {
//                AbToastUtil.showToast(mActivity, content);
//                //显示重试的框
////						showRefreshView();
//            }
//
//        });
//    }
//
//    /**
//     * 请求选项实体类的数据
//     *
//     * @param questionId
//     */
//    private void getChoiceList(String questionId) {
//        // 绑定参数
//        AbRequestParams params = new AbRequestParams();
//        params.put("questionId", questionId);
//        // 下载网络数据
//        NetworkWeb web = NetworkWeb.newInstance(mActivity);
//        web.findLogList(params, new AbHttpListener() {
//
//            @Override
//            public void onSuccess(List<?> newList) {
//                choiceList.clear();
//                if (newList != null && newList.size() > 0) {
//                    choiceList.addAll((List<choiceOfQuestionInfo>) newList);
//                    newList.clear();
////	                handler.sendEmptyMessage(102);
//                }
//
//            }
//
//            @Override
//            public void onFailure(String content) {
//                AbToastUtil.showToast(mActivity, content);
//                //显示重试的框
////				showRefreshView();
//            }
//
//        });
//    }
//
//}
