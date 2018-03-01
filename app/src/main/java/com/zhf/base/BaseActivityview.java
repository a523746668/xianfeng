package com.zhf.base;

import java.util.ArrayList;

/**
 * Created by zhf on 2017/9/24.
 */

public interface BaseActivityview<T> {
     void showerrow(Exception e);
     void getdatasuccess(ArrayList<T> list);
     void getdatasuccess(T t);
}
