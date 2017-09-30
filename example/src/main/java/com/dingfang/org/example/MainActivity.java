package com.dingfang.org.example;

import android.support.constraint.ConstraintLayout;

import com.dingfang.org.easylib.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.container)
    ConstraintLayout mConstraintLayout;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
//        showNetError();
    }

//    @Override
//    protected View getLoadingTargetView() {
//        return mConstraintLayout;
//    }
}
