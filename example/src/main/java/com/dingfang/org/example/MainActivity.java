package com.dingfang.org.example;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;

import com.dingfang.org.easylib.base.BaseActivity;
import com.dingfang.org.easylib.view.NavigationButton;
import com.dingfang.org.example.fragment.NavFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavFragment.OnNavigationReselectListener{

    @BindView(R.id.container)
    LinearLayout mContainer;

    private NavFragment mNavBar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        init();
//        showNetError();
    }

    /**
     * 全局初始化
     */
    private void init() {
        FragmentManager manager = getSupportFragmentManager();
        mNavBar = ((NavFragment) manager.findFragmentById(R.id.nav_bar));
        mNavBar.setup(this, manager, R.id.main_container, this);
    }

    @Override
    public void onReselect(NavigationButton navigationButton) {
        Fragment fragment = navigationButton.getFragment();
        if (fragment != null) {
            //do your business
        }
    }

//    @Override
//    protected View getLoadingTargetView() {
//        return mContainer;
//    }
}
