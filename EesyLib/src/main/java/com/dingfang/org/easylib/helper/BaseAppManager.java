package com.dingfang.org.easylib.helper;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Activity 管理工具
 * create by pec
 * 2017/09/29
 */
public class BaseAppManager {

    private static BaseAppManager instance;

    private List<Activity> mActivities=new LinkedList<>();

    public BaseAppManager() {
    }

    public static BaseAppManager getInstance(){
        if(null==instance){
            synchronized (BaseAppManager.class){
                instance=new BaseAppManager();
            }
        }
        return instance;
    }

    public int size (){
        return mActivities.size();
    }

    public synchronized  void addActivity(Activity activity){
        mActivities.add(activity);
    }

    /**
     * 删除一个Activity
     * @param activity
     */
    public synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    /**
     * 清除所有的Activity
     */
    public synchronized void clear() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size();
        }
    }

    /**
     * 清除Activity 到首页
     */
    public synchronized void clearToTop() {
        for (int i = mActivities.size() - 2; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size() - 1;
        }
    }
}
