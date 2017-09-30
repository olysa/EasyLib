package com.dingfang.org.easylib.helper.view;

import android.content.Context;
import android.view.View;

/**
 * create by pec
 * 2017/09/29
 */
public interface IVaryViewHelper {

    View getCurrentLayout();

    void restoreView();

    void showLayout(View view);

    View inflate(int layoutId);

    Context getContext();

    View getView();
}
