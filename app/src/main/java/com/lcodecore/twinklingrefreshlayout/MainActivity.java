package com.lcodecore.twinklingrefreshlayout;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    public int setInflateId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecyclerFragment());
        fragments.add(new ListViewFragment());
        fragments.add(new GridViewFragment());
        fragments.add(new ScrollViewFragment());
        fragments.add(new WebViewFragment());
        fragments.add(new BounceFragment());

        String tabTitles[] = new String[]{"RecyclerView", "ListView", "GridView","ScrollView","WebView","Bounce"};
        List<String> titles = Arrays.asList(tabTitles);

        TKFragmentPagerAdapter pagerAdapter = new TKFragmentPagerAdapter(getSupportFragmentManager(),fragments,titles);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);
    }

    class TKFragmentPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments;
        List<String> titles;

        public TKFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles) {
            super(fm);
            this.fragments =fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
