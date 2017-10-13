package com.dingfang.org.example.fragment;

import android.content.Intent;
import android.view.View;

import com.dingfang.org.easylib.base.BaseFragment;
import com.dingfang.org.example.R;
import com.dingfang.org.example.activity.DownloadActivity;
import com.dingfang.org.example.activity.FileUploadActivity;

import butterknife.OnClick;


/**
 * 文件下载，或者说是app下载使用
 * Created by zuoqing on 2017/9/30.
 */

public class Fragment02 extends BaseFragment {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment02;
    }

    @Override
    protected void initViewsAndEvents() {
        init();
    }

    /**
     * 全局初始化
     */
    private void init() {

    }

    @OnClick({R.id.fileDownload, R.id.fileUpload})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.fileDownload: {
                Intent intent = new Intent(getActivity(), DownloadActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.fileUpload: {
                Intent intent = new Intent(getActivity(), FileUploadActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}
