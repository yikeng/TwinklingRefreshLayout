package com.lcodecore.twinklingrefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lcodecore.tkrefreshlayout.v2.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.v2.TwinklingRefreshLayout;
import com.lcodecore.twinklingrefreshlayout.adapter.SimpleAdapter;

/**
 * Created by lcodecore on 2016/10/1.
 */

public class ListViewFragment extends Fragment {

    private View rootView;
    private SimpleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_listview, container, false);
            setupListView((ListView) rootView.findViewById(R.id.listView));
        }
        return rootView;
    }

    private void setupListView(ListView listView) {
        TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) rootView.findViewById(R.id.refresh);
        refreshLayout.setEnableOverlayRefreshView(false);
        adapter = new SimpleAdapter();
        listView.setAdapter(adapter);
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
                }, 3000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.loadMoreCard();
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
    }




}
