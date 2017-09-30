package com.dingfang.org.easylib.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dingfang.org.easylib.R;
import com.dingfang.org.easylib.helper.BaseAppManager;
import com.dingfang.org.easylib.helper.EventCenter;
import com.dingfang.org.easylib.helper.view.VaryViewHelperController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 通用activity 必用 抽象层
 * Created by zuoqing on 2017/9/29.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    protected Context mContext = null;

    /**
     * 当前的view的控制界面显示---->网络异常,网络过慢,请求空数据显示
     */
    private VaryViewHelperController mVaryViewHelperController = null;

    private Unbinder unbinder;

    /**
     * activity 切换动画
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //去掉系统默认标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //启动动画
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionType()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);

        //是否注册eventbus,用于广播
        if(isBindEventBus()){
            EventBus.getDefault().register(this);
        }
        //是否透明状态栏
        setTranslucentStatus(isApplyStatusBarTranslucency());

        mContext=this;
        //activity统一管理
        BaseAppManager.getInstance().addActivity(this);

        if(getContentViewLayoutID()!=0){
            setContentView(getContentViewLayoutID());
        }else{
            throw new IllegalArgumentException("You must return a  contentView layout resource Id");
        }

        //初始化，供子类调用
        initViewsAndEvents();
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //使用ButterKnife
        unbinder = ButterKnife.bind(this);
        if (null != getLoadingTargetView()) {
            //展示不同情况下的界面结构
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除 ButterKnife 绑定
        unbinder.unbind();
        //注销广播
        if (isBindEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        //activity管理 移除
        BaseAppManager.getInstance().removeActivity(this);
    }


    @Override
    public void finish() {
        super.finish();
        //关闭动画
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionType()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
    }



    /**
     * activity的跳转
     * */
    protected void readyGo(Class<?> clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

    protected void readyGoWithData(Class<?> clazz,Bundle bundle){
        Intent intent=new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoThenKill(Class<?> clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
        finish();
    }

    protected void readyGoWithDataThenKill(Class<?> clazz,Bundle bundle){
        Intent intent=new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    protected void readyGoWaitResult(Class<?> clazz,int requestCode){
        Intent intent=new Intent(this,clazz);
        startActivityForResult(intent,requestCode);
    }

    protected void readyGoWithDataWaitResult(Class<?> clazz,Bundle bundle,int requestCode){
        Intent intent=new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }


    /**
     * 显示正在加载中
     *基于getLoadingTargetView()不为空的情况
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }


    /**
     * set status bar translucency
     *透明状态栏--->顶部电池栏
     * a |= b;==>a = a|b
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    @Subscribe
    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            onEventComming(eventCenter);
        }
    }


    /**
     * 广播接受数据继承类实现相关操作
     *
     * @param eventCenter
     */
    protected abstract void onEventComming(EventCenter eventCenter);


    /**
     * activity进入退出是否需要动画设置
     * */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * activity动画选择器
     * */
    protected abstract TransitionMode getOverridePendingTransitionType();

    /**
     * 界面是否注册监听器
     * */
    protected abstract boolean isBindEventBus();

    /**
     * 是否将导航栏透明化
     * */
    protected abstract boolean isApplyStatusBarTranslucency();

    /**
     *设置当前布局
     * */
    protected abstract int getContentViewLayoutID();

    /**
     * 实现数据赋值
     * */
    protected abstract void initViewsAndEvents();

    /**
     * 是否有其他不同的view，
     */
    protected abstract View getLoadingTargetView();
}
