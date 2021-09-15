package com.example.demo.ui.login;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public interface OnNetworkCallbackListener {
    public void onResponse(int Id, Retrofit retrofit);
    public void onBodyError(ResponseBody responseBodyError);
    public void onBodyErrorIsNull();
    public void onFailure(Throwable t);
}
