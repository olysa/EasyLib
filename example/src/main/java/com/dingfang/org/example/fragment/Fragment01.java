package com.dingfang.org.example.fragment;

import android.view.View;

import com.dingfang.org.easylib.base.BaseFragment;
import com.dingfang.org.easylib.view.progress.HHProgressAlertDialog;
import com.dingfang.org.example.R;

import butterknife.OnClick;

/**
 * Created by zuoqing on 2017/9/30.
 */

public class Fragment01 extends BaseFragment {

    private HHProgressAlertDialog progressAlertDialog;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment01;
    }

    @Override
    protected void initViewsAndEvents() {

    }
    @OnClick({R.id.show_dialog,R.id.hide_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_dialog:
                progressAlertDialog = new HHProgressAlertDialog(getActivity());
                progressAlertDialog.setCancelable(true); //设置返回键可以取消请求
                progressAlertDialog.show();
                break;
            case R.id.hide_dialog:
                progressAlertDialog.dismiss();
                break;

        }
    }
}
