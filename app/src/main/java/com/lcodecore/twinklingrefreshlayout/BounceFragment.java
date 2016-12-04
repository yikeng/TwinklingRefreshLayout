package com.lcodecore.twinklingrefreshlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lcodecore.tkrefreshlayout.v2.TwinklingRefreshLayout;
import com.lcodecore.twinklingrefreshlayout.adapter.SimpleAdapter;

/**
 * Created by lcodecore on 2016/12/4.
 */

public class BounceFragment extends Fragment {

    private View rootView;
    private SimpleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_bounce, container, false);
            setupListView((ListView) rootView.findViewById(R.id.listView));
            setupListView((ListView) rootView.findViewById(R.id.listView2));
        }
        return rootView;
    }

    private void setupListView(ListView listView) {
        adapter = new SimpleAdapter();
        listView.setAdapter(adapter);
        adapter.refreshCard();
    }
}
