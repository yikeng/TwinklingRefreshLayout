package com.lcodecore.twinklingrefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.lcodecore.tkrefreshlayout.Footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lcodecore.tkrefreshlayout.v2.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.v2.TwinklingRefreshLayout;
import com.lcodecore.twinklingrefreshlayout.adapter.SimpleAdapter;

/**
 * Created by lcodecore on 2016/10/1.
 */

public class GridViewFragment extends Fragment {

    private View rootView;
    private SimpleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_gridview, container, false);
            setupGridView((GridView) rootView.findViewById(R.id.gridView));
        }
        return rootView;
    }

    private void setupGridView(GridView gridView) {
        final TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) rootView.findViewById(R.id.refresh);
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        refreshLayout.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(getContext());
        refreshLayout.setBottomView(loadingView);

        adapter = new SimpleAdapter();
        gridView.setAdapter(adapter);
        adapter.refreshCard();

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refreshCard();
                        refreshLayout.finishRefreshing();
                    }
                },2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.loadMoreCard();
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }
        });
    }
}