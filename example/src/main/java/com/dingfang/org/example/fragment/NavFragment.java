package com.dingfang.org.example.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.dingfang.org.easylib.base.BaseFragment;
import com.dingfang.org.easylib.utils.LogUtilss;
import com.dingfang.org.easylib.view.NavigationButton;
import com.dingfang.org.example.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 底部的整条 tab导航条 碎片
 * Created by zuoqing on 2017/9/30.
 */

public class NavFragment extends BaseFragment {

    @BindView(R.id.nav_item_news)
    NavigationButton mNavNews;
    @BindView(R.id.nav_item_tweet)
    NavigationButton mNavTweet;
    @BindView(R.id.nav_item_explore)
    NavigationButton mNavExplore;
    @BindView(R.id.nav_item_me)
    NavigationButton mNavMe;


    View root;

    private Context mContext;
    private int mContainerId;
    private FragmentManager mFragmentManager;
    private NavigationButton mCurrentNavButton;

    private OnNavigationReselectListener mOnNavigationReselectListener;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void initViewsAndEvents() {
        initData();
    }

    /**
     * 初始化视图数据
     */
    private void initData() {

        mNavNews.init(R.drawable.tab_icon_new, "综合", Fragment01.class);

        mNavTweet.init(R.drawable.tab_icon_tweet, "动态", Fragment02.class);

        mNavExplore.init(R.drawable.tab_icon_explore, "发现", Fragment03.class);

        mNavMe.init(R.drawable.tab_icon_me, "我的", Fragment04.class);
    }

    /**
     * 初始化
     * @param context
     * @param fragmentManager
     * @param contentId
     * @param listener
     */
    public void setup(Context context, FragmentManager fragmentManager, int contentId, OnNavigationReselectListener listener) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mContainerId = contentId;
        mOnNavigationReselectListener = listener;

        // do clear
        clearOldFragment();
        // do select first
        doSelect(mNavNews);
    }

    /**
     * 以防万一，清除一遍fragment
     */
    @SuppressWarnings("RestrictedApi")
    private void clearOldFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (transaction == null || fragments == null || fragments.size() == 0)
            return;
        boolean doCommit = false;
        for (Fragment fragment : fragments) {
            if (fragment != this && fragment != null) {
                transaction.remove(fragment);
                doCommit = true;
            }
        }
        if (doCommit)
            transaction.commitNow();
    }

    /**
     * tab 切换
     * @param newNavButton
     */
    private void doSelect(NavigationButton newNavButton) {
        // If the new navigation is me info fragment, we intercept it
        /*
        if (newNavButton == mNavMe) {
            if (interceptMessageSkip())
                return;
        }
        */

        NavigationButton oldNavButton = null;
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton;
            if (oldNavButton == newNavButton) {
                onReselect(oldNavButton);
                return;
            }
            oldNavButton.setSelected(false);
        }
        newNavButton.setSelected(true);
        doTabChanged(oldNavButton, newNavButton);
        mCurrentNavButton = newNavButton;
    }

    /**
     * fragment切换
     * 该方法，保证了，使用只有一个fragment与activity绑定
     * 在fragment不存在的时候，就创建，存在的时候，就绑定
     * @param oldNavButton
     * @param newNavButton
     */
    private void doTabChanged(NavigationButton oldNavButton, NavigationButton newNavButton) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavButton != null) {
            if (oldNavButton.getFragment() != null) {
                ft.detach(oldNavButton.getFragment());//Fragment实例还在，只是解除了与activity的绑定
            }
        }
        if (newNavButton != null) {
            if (newNavButton.getFragment() == null) {
                //切换的时候，如果fragment存在，就切换，不存在，就创建
                Fragment fragment = Fragment.instantiate(mContext,
                        newNavButton.getClx().getName(), null);
                ft.add(mContainerId, fragment, newNavButton.getTag());
                newNavButton.setFragment(fragment);
            } else {
                ft.attach(newNavButton.getFragment());
            }
        }
        ft.commit();
    }

    @OnClick({R.id.nav_item_me,R.id.nav_item_explore,R.id.nav_item_tweet,R.id.nav_item_news})
    public  void  onClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_item_explore:
                LogUtilss.i("nav_item_explore");
                break;
            case R.id.nav_item_news:
                LogUtilss.i("nav_item_news");
                break;
            case R.id.nav_item_me:
                LogUtilss.i("nav_item_me");
                break;
            case R.id.nav_item_tweet:
                LogUtilss.i("nav_item_tweet");
                break;
        }
        if (view instanceof NavigationButton) {
            NavigationButton nav = (NavigationButton) view;
            doSelect(nav);
//            Toast.makeText(mContext,"",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 重复点击 tab item 回调
     */
    public interface OnNavigationReselectListener {
        void onReselect(NavigationButton navigationButton);
    }

    private void onReselect(NavigationButton navigationButton) {
        if (mOnNavigationReselectListener != null) {
            mOnNavigationReselectListener.onReselect(navigationButton);
        }
    }
}
