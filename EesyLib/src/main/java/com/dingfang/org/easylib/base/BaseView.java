package com.dingfang.org.easylib.base;

/**
 * 错误页面、无数据页面、等非正常状态下页面提示信息接口
 * 对应当前数据填充的界面的状态操控
 */
public interface BaseView {
    /**
     * show loading message
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * hide loading
     */
    void hideLoading();

    /**
     * show error message
     */
    void showError(String msg);

    /**
     * show exception message
     */
    void showException(String msg);

    /**
     * show net error
     */
    void showNetError();
}
