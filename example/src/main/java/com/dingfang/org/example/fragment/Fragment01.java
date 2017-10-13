package com.dingfang.org.example.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dingfang.org.easylib.base.BaseFragment;
import com.dingfang.org.example.R;
import com.dingfang.org.example.constant.ApiInterface;
import com.dingfang.org.example.entity.TestHttpBean;
import com.dingfang.org.example.okhttp.callback.DialogCallback;
import com.dingfang.org.example.okhttp.model.LzyResponse;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 普通的网络请求使用
 * Created by zuoqing on 2017/9/30.
 */

public class Fragment01 extends BaseFragment {
    private boolean isInitCache = false;

    @BindView(R.id.show_text)
    TextView show_text;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment01;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @OnClick({R.id.show_dialog, R.id.hide_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_dialog:
                OkGo.<LzyResponse<TestHttpBean>>get(ApiInterface.URL + ApiInterface.TEST_INTERFACE)//
                        .tag(this)//主要用来取消请求的标识
                        .cacheTime(1000 * 60 * 60 * 24 * 30)
                        .cacheKey("test_interface") //key 必须唯一
                        .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                        .params("page", "1")//
                        .params("rows", "10")//
                        .execute(new DialogCallback<LzyResponse<TestHttpBean>>(getActivity()) {

                            @Override
                            public void onSuccess(Response<LzyResponse<TestHttpBean>> response) {
                                if (response.isSuccessful()) {
                                    show_text.setText(response.body().result.getData().get(0).getBibName());
                                    Log.e("error", response.body().result.getData().get(0).getBibName());
                                }
                            }

                            @Override
                            public void onCacheSuccess(Response<LzyResponse<TestHttpBean>> response) {
                                if (!isInitCache) {
                                    //一般来说,缓存回调成功和网络回调成功做的事情是一样的,所以这里直接回调onSuccess
                                    onSuccess(response);
                                    isInitCache = true;
                                }
                            }

                            @Override
                            public void onError(Response<LzyResponse<TestHttpBean>> response) {
                                Log.e("error", "返回码： " + response.code() + " --- 返回信息： " + response.message() + " \n--- 错误消息：" + response.getException().getMessage());
//                                Log.e("error", response.body().result.getData().toString());
                            }

                        });

                break;
            case R.id.hide_dialog:
                show_text.setText("");
                break;

        }
    }
}
