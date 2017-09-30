package com.dingfang.org.easylib.base;

import android.view.View;

import com.dingfang.org.easylib.helper.EventCenter;

/**
 * 子类继承该 基类
 * Created by zuoqing on 2017/9/29.
 */
public abstract class BaseFragment extends BaseAppFragment implements BaseView {

    /**
     * 以下是默认配置
     */
    @Override
    protected void onEventComming(EventCenter eventCenter) {
        //默认不实现
    }

    @Override
    protected boolean isBindEventBus() {
        //默认不监听
        return false;
    }

    @Override
    protected View getLoadingTargetView() {
        //默认 不需要异常处理，当这个方法子类没有重写时，下面的5个异常处理的方法，将不能正常使用
        return null;
    }

    /**
     * 以下是
     * 错误或异常处理
     */
    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }
}
