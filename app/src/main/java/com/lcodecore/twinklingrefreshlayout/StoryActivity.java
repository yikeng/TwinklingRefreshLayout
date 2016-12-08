package com.lcodecore.twinklingrefreshlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lcodecore.tkrefreshlayout.v3.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

public class StoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

//        TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh);
//        ProgressLayout header = new ProgressLayout(this);
//        refreshLayout.setHeaderView(header);
//        refreshLayout.setFloatRefresh(true);
//        refreshLayout.setEnableOverlayRefreshView(false);
    }
}
