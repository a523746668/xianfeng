package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.pojo.DitiChuangg;
import com.qingyii.hxtz.pojo.DitiItem;
import com.qingyii.hxtz.util.EmptyUtil;

import java.util.ArrayList;
import java.util.HashSet;

public class DaCiAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DitiItem> list;
    private DitiChuangg dc = null;


    public DaCiAdapter(Context context, DitiChuangg dc) {
        this.context = context;
        this.list = dc.getQuestionOptionList();
        this.dc = dc;
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

        convertView = LayoutInflater.from(context).inflate(R.layout.datichuangg_item, null);

        ImageView datichuangg_item_xziv = (ImageView) convertView.findViewById(R.id.datichuangg_item_xziv);
//		LinearLayout datichuangg_item_ll=(LinearLayout) convertView.findViewById(R.id.datichuangg_item_ll);
        TextView datichuangg_item_xztv = (TextView) convertView.findViewById(R.id.datichuangg_item_xztv);


        if (EmptyUtil.IsNotEmpty(list.get(position).getOptiondesc())) {
            datichuangg_item_xztv.setText(list.get(position).getOptiondesc());
        }

        if (("1".equals(dc.getRedios())) || ("2".equals(dc.getRedios()))) {
            if (position == 0) {
                datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_aa);

            } else if (position == 1) {
                datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_bb);

            } else if (position == 2) {
                datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_cc);
//			datichuangg_item_xziv.setBackground(context.getResources().getDrawable(R.drawable.select_cc));

            } else if (position == 3) {
                datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_dd);

            } else if (position == 4) {
                datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_ee);

            } else if (position == 5) {
                datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_ff);

            } else if (position == 6) {
                datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_gg);

            } else if (position == 7) {
                datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_hh);

            }

        } else if ("3".equals(dc.getRedios())) {
            if (position == 0) {
                if ("正确".equals(list.get(position).getOptiondesc())) {
                    datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_yes);
                } else if ("错误".equals(list.get(position).getOptiondesc())) {
                    datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_no);
                }
            } else if (position == 1) {
                if ("正确".equals(list.get(position).getOptiondesc())) {
                    datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_yes);
                } else if ("错误".equals(list.get(position).getOptiondesc())) {
                    datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_no);
                }
            }

        }


        if (dc != null) {
            HashSet<String> hs = dc.getXxid();

            if (hs.contains(list.get(position).getOptionid())) {

                if (("1".equals(dc.getRedios())) || ("2".equals(dc.getRedios()))) {
                    if (position == 0) {
                        datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_ah);
                        datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));

                    } else if (position == 1) {
                        datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_bh);
                        datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));

                    } else if (position == 2) {
                        datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_ch);
                        datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));

                    } else if (position == 3) {
                        datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_dh);
                        datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));

                    } else if (position == 4) {
                        datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_eh);
                        datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));

                    } else if (position == 5) {
                        datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_fh);
                        datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));

                    } else if (position == 6) {
                        datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_gh);
                        datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));

                    } else if (position == 7) {
                        datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_hhh);
                        datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));

                    }

                } else if ("3".equals(dc.getRedios())) {
                    if (position == 0) {
                        if ("正确".equals(list.get(position).getOptiondesc())) {
                            datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_yesh);
                            datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));
                        } else if ("错误".equals(list.get(position).getOptiondesc())) {
                            datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_noh);
                            datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));
                        }
                    } else if (position == 1) {
                        if ("正确".equals(list.get(position).getOptiondesc())) {
                            datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_yesh);
                            datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));
                        } else if ("错误".equals(list.get(position).getOptiondesc())) {
                            datichuangg_item_xziv.setBackgroundResource(R.mipmap.select_noh);
                            datichuangg_item_xztv.setTextColor(context.getResources().getColor(R.color.red));
                        }
                    }


                }

            }
        }


        return convertView;
    }


}
