package com.dingfang.org.example.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dingfang.org.easylib.base.BaseFragment;
import com.dingfang.org.example.R;
import com.dingfang.org.example.adapter.BaseRecyclerAdapter;
import com.dingfang.org.example.adapter.SmartViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by zuoqing on 2017/9/30.
 */

public class Fragment03 extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private BaseRecyclerAdapter<Void> mAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment03;
    }

    @Override
    protected void initViewsAndEvents() {
        init();
    }

    private void init() {
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.addItemDecoration(new DividerItemDecoration(getContext(), android.support.v7.widget.DividerItemDecoration.VERTICAL));
        recyclerview.setAdapter(mAdapter = new BaseRecyclerAdapter<Void>(initData(),android.R.layout.simple_list_item_2,this) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Void model, int position) {
                holder.text(android.R.id.text1, String.format(Locale.CHINA, "第%02d条数据", position));
                holder.text(android.R.id.text2, String.format(Locale.CHINA, "这是测试的第%02d条数据", position));
            }
        });


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        mAdapter.refresh();
                        refreshlayout.finishRefresh();
                        refreshlayout.setLoadmoreFinished(false);
                    }
                }, 2000);
            }
        });

        //不让上拉加载
//        refreshLayout.setEnableLoadmore(false);

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.loadmore(initData());
                        refreshlayout.finishLoadmore();
                        if (mAdapter.getItemCount() > 60) {
                            Toast.makeText(getActivity().getApplication(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                            refreshlayout.setLoadmoreFinished(true);//将不会再次触发加载更多事件
                        }
                    }
                }, 2000);
            }
        });

    }

    /**
     * RecyclerView item 点击回调到这里
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getActivity(), "" + Item.values()[i].name, Toast.LENGTH_SHORT).show();
    }

    private Collection<Void> initData() {
        return Arrays.asList(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
    }

    private enum Item {
        Basic("基本的使用"),
        SpecifyStyle("使用指定的Header和Footer"),
        EmptyLayout("整合空页面"),
        NestedLayout("嵌套Layout作为内容"),
        NestedScroll("嵌套滚动使用"),
        PureScroll("纯滚动模式"),
        Listener("多功能监听器"),
        Custom("自定义Header"),
        SnapHelper("结合 SnapHelper 使用"),;
        public String name;

        Item(String name) {
            this.name = name;
        }
    }
}
