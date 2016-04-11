package com.yong.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.yong.rest.LoggingInterceptor;
import com.yong.rest.RestClient;
import com.yong.rest.ConverterFactory.StringConverterFactory;
import com.yong.utils.ThreadPoolManager;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class BaseActivity extends FragmentActivity implements ActivityInterface, View.OnClickListener {

    private static final int SHOW_PROGRESS = 1;
    private static final int DISMISS_PROGRESS = 2;
    private ProgressHandler mProgressHandler = new ProgressHandler(this);

    ThreadPoolManager mThreadPoolManager;
    protected RestClient mGsonClient = null;
    protected RestClient mStringClient = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mThreadPoolManager = ThreadPoolManager.getInstance();
        initGsonRetrofit();
        initStringGsonRetrofit();
    }

    /**
     * 初始化返回数据为JSON类型的Retrofit并配置拦截器
     */
    private void initGsonRetrofit() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apis.baidu.com/showapi_open_bus/showapi_joke/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)

                .build();

        mGsonClient = retrofit.create(RestClient.class);
    }

    /**
     * 初始化返回数据为String类型的Retrofit并配置拦截器
     */
    private void initStringGsonRetrofit() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com/")
                .addConverterFactory(StringConverterFactory.create())
                .client(client)
                .build();

        mStringClient = retrofit.create(RestClient.class);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (msg != null || msg.length() > 0)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param run
     */
    public void executeTask(Runnable run) {
        mThreadPoolManager.executeTask(run);
    }

    @Override
    public void showLoading() {
        mProgressHandler.sendEmptyMessage(SHOW_PROGRESS);
    }

    @Override
    public void dismissLoading() {
        mProgressHandler.sendEmptyMessage(DISMISS_PROGRESS);
    }

    public static class ProgressHandler extends Handler {
        private ProgressDialog mProgressDialog;
        private Activity mActivity;

        public ProgressHandler(Activity activity) {
            super(Looper.getMainLooper());
            mActivity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SHOW_PROGRESS:
                    if (null == mProgressDialog) {
                        mProgressDialog = new ProgressDialog(mActivity);
                    }
                    if (!mProgressDialog.isShowing()) {
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        mProgressDialog.setContentView(new ProgressBar(mActivity));
                        mProgressDialog.getWindow().setGravity(Gravity.CENTER);
                    }
                    break;
                case DISMISS_PROGRESS:
                    if (null != mProgressDialog && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}
