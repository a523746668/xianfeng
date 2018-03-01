package com.qingyii.hxt.fragment;

//import com.ab.fragment.AbFragment;
//import com.ab.http.AbHttpListener;
//import com.ab.http.AbRequestParams;
//import com.ab.util.AbToastUtil;


//public class ResearchFragment extends Fragment {
//    private TextView tv_title, tv_type, tv_ok;
//    private ListView lv_choice;
//    private View view;
//    private huodongInfo huodong;
//    private Activity mActivity;
//    private choiceOfResearchAdapter mAdapter;
//    private List<researchInfo> list;
//    private List<choiceOfResearchInfo> choiceList;
//    private Handler handler;
//    //当前的题目的题号
//    private static int currentPage = 1;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_question, null);
//        mActivity = getActivity();
//        huodong = (huodongInfo) mActivity.getIntent().getSerializableExtra("huodongdetail");
//        list = new ArrayList<researchInfo>();
//        choiceList = new ArrayList<choiceOfResearchInfo>();
//
////	    handler = new Handler(new Callback() {
////
////			@Override
////			public boolean handleMessage(Message msg) {
////				switch (msg.what) {
////				case 101:
////
////					break;
////                case 102:
////					mAdapter.notifyDataSetChanged();
////					break;
////				default:
////					break;
////				}
////				return false;
////			}
////		});
//        tv_title = (TextView) view.findViewById(R.id.title_question);
//        tv_type = (TextView) view.findViewById(R.id.type_question_item);
//        tv_ok = (TextView) view.findViewById(R.id.ok_btn_question);
//
//        setDataAndRegister();
//        return view;
//    }
//
//    private void setDataAndRegister() {
////		getQuestionList();
////		getChoiceList(list.get(0).getResearchId());
//
//        list.addAll(huodong.getReseachList());
//        choiceList.addAll(huodong.getReseachList().get(0).getChoiceList());
//        lv_choice = (ListView) view.findViewById(R.id.lv_question);
//        mAdapter = new choiceOfResearchAdapter(mActivity, choiceList);
//        lv_choice.setAdapter(mAdapter);
//        tv_title.setText(list.get(0).getReserchTitle());
//        tv_type.setText(list.get(0).getResearchType());
//        tv_ok.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
////				while(currentPage == 0){
////					tv_title.setText(list.get(currentPage+1).getReserchTitle());
////					tv_type.setText(list.get(currentPage+1).getResearchType());
////					//保存选择的选项
////					holdResult();
////
////					getChoiceList(list.get(currentPage+1).getResearchId());
////					mAdapter = new choiceOfResearchAdapter(mActivity, choiceList);
////					lv_choice.setAdapter(mAdapter);
////					mAdapter.notifyDataSetChanged();
////					if(currentPage == list.size()){
////						mActivity.finish();
////					}
////				}
////				currentPage++;
//            }
//        });
//    }
//
//    protected void holdResult() {
//        // TODO Auto-generated method stub
//
//    }
//
////	@Override
////	public void setResource() {
////		super.setResource();
////		//设置加载的资源
////		this.setLoadDrawable(R.drawable.ic_load);
////		this.setLoadMessage("正在查询,请稍候");
////
////		this.setRefreshDrawable(R.drawable.ic_refresh);
////		this.setRefreshMessage("请求出错，请重试");
////	}
//
//    /**
//     * 请求问题实体类的数据
//     */
//    private void getQuestionList() {
//        // TODO Auto-generated method stub
//        // 绑定参数
//        AbRequestParams params = new AbRequestParams();
//        params.put("huodongId", huodong.getId());
//        // 下载网络数据
//        NetworkWeb web = NetworkWeb.newInstance(mActivity);
//        web.findLogList(params, new AbHttpListener() {
//
//            @Override
//            public void onSuccess(List<?> newList) {
//                list.clear();
//                if (newList != null && newList.size() > 0) {
//                    list.addAll((List<researchInfo>) newList);
//                    newList.clear();
//                    handler.sendEmptyMessage(101);
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
//                    choiceList.addAll((List<choiceOfResearchInfo>) newList);
//                    newList.clear();
//                    handler.sendEmptyMessage(102);
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
