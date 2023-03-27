package com.lcodecore.twinklingrefreshlayout;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

public class StoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        final TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh);
//        ProgressLayout header = new ProgressLayout(this);
//        refreshLayout.setHeaderView(header);
//        refreshLayout.setFloatRefresh(true);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 4000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 4000);
            }
        });
    }
}
