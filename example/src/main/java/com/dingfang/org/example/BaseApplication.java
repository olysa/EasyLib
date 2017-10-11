package com.dingfang.org.example;

import android.app.Application;

import com.dingfang.org.example.okhttp.OkHttpUtil;

/**
 * Created by zuoqing on 2017/10/11.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    /**
     * 全局初始化
     */
    private void init() {
        //okhttp全局配置
        OkHttpUtil.getInstance().init(this);
    }
}
