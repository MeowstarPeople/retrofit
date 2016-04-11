package com.yong.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.yong.bean.BeanBody;
import com.yong.commondemo.R;
import com.yong.fragment.EmptyFragment;
import com.yong.fragment.IndexFragment;
import com.yong.fragment.MineFragment;
import com.yong.fragment.ProductFragment;
import com.yong.fragment.SocialFragment;
import com.yong.rest.Dto.PostParams;
import com.yong.rest.RestBean;

import java.util.Stack;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeActivity extends BaseActivity {

    static final int TAB_COUNT = 5;
    static final Class[] clazzes = new Class[]{
            IndexFragment.class,
            ProductFragment.class,
            EmptyFragment.class,
            SocialFragment.class,
            MineFragment.class
    };
    static final int[] drawableIds = new int[]{
            R.drawable.main_page_tab1,
            R.drawable.main_page_tab2,
            0,
            R.drawable.main_page_tab3,
            R.drawable.main_page_tab4
    };
    static final int[] tabStrings = {
            R.string.tab_string1,
            R.string.tab_string2,
            R.string.calculate,
            R.string.social,
            R.string.tab_string4
    };

    private Stack<Integer> mFragmentIdStack = new Stack<>();
    private FragmentTabHost mTabHost;
    private Bundle[] mBundles;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        mBundles = new Bundle[TAB_COUNT];
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        initFragments();
        mTabHost.setCurrentTab(0);
        mFragmentIdStack.push(0);


//        getBaidu();
//        getRetrofit();
    }

    private void getRetrofit() {

        showLoading();
        PostParams params = new PostParams();
        params.put("page", "1");
        Call<RestBean<BeanBody>> mCall = mGsonClient.getJoke("ffac023cd51e5d0430d6ceaecf623c2e", params);
        mCall.enqueue(new Callback<RestBean<BeanBody>>() {

            @Override
            public void onResponse(Response<RestBean<BeanBody>> response, Retrofit retrofit) {

                RestBean<BeanBody> body = response.body();
                dismissLoading();
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.getMessage();
            }
        });
    }


    private void getBaidu() {
        Call<String> mCall = mStringClient.getbaidu("http://www.chinaislands.gov.cn/contents/20198/20064.html");
        mCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                String mString = response.body().toString();
                Log.d("TAG",mString);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void initFragments() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);
        mTabHost. getTabWidget().setDividerDrawable(null);//消除分割线
        for (int i = 0; i < TAB_COUNT; i++) {
            String str = getResources().getString(tabStrings[i]);
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(str);

            if (i == 2) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setImageResource(R.drawable.index_add);
                tabSpec.setIndicator(imageView);

            } else {
                View view = getLayoutInflater().inflate(R.layout.layout_main_page_tab, null);
                ((TextView) view.findViewById(R.id.text)).setText(str);
                ((ImageView) view.findViewById(R.id.image)).setImageResource(drawableIds[i]);
                tabSpec.setIndicator(view);
            }

            mBundles[i] = new Bundle();
            mTabHost.addTab(tabSpec, clazzes[i], mBundles[i]);
        }

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mFragmentIdStack.remove((Integer) mTabHost.getCurrentTab());
                mFragmentIdStack.push(mTabHost.getCurrentTab());
            }
        });
    }

}
