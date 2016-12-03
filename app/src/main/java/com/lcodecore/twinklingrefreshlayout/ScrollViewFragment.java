package com.lcodecore.twinklingrefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.v2.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.v2.TwinklingRefreshLayout;

/**
 * Created by lcodecore on 2016/10/1.
 */

public class ScrollViewFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_scrollview, container, false);
            TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) rootView.findViewById(R.id.refreshLayout);
            TextHeaderView headerView = (TextHeaderView) View.inflate(getContext(), R.layout.header_tv, null);
            refreshLayout.setHeaderView(headerView);
            refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
                @Override
                public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishRefreshing();
                        }
                    }, 2000);
                }

                @Override
                public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishLoadmore();
                        }
                    }, 2000);
                }
            });
        }
        return rootView;
    }
}
