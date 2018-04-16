package com.qingyii.hxtz.httpway;

import android.util.Log;

import com.google.gson.Gson;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 通用参数转型
 */

public abstract class HandleParameterCallback extends Callback<HandleParameter> {

    @Override
    public HandleParameter parseNetworkResponse(Response response, int id) throws Exception {
        String result = response.body().string();
        Log.e("HandleParameter_String", result);
        HandleParameter handleParameter = new Gson().fromJson(result, HandleParameter.class);
        return handleParameter;
    }
}