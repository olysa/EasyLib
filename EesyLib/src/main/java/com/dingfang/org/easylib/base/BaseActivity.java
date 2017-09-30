package com.dingfang.org.easylib.base;

import android.view.View;

import com.dingfang.org.easylib.helper.EventCenter;

/**
 * 子类继承该 基类
 * Created by zuoqing on 2017/9/29.
 */
public abstract class BaseActivity extends BaseAppCompatActivity implements BaseView{

    /**
     * 以下
     * 是默认初始化，如果要改，可以在子类中重写指定方法
     */
    @Override
    protected void onEventComming(EventCenter eventCenter) {
        //默认 不处理Event广播
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        //默认不需要 启动的动画
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionType() {
        //默认不返回启动动画类型
        return null;
    }

    @Override
    protected boolean isBindEventBus() {
        //默认不绑定 ，即不注册EventBus广播
        return false;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        //默认不将导航栏透明化
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
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

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
    public void hideLoading() {
        toggleShowLoading(false, null);
    }
}
