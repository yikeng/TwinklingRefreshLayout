package com.lcodecore.twinklingrefreshlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.v2.TwinklingRefreshLayout;

/**
 * Created by lcodecore on 2016/12/4.
 */

public class BounceFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_bounce, container, false);
//            TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) rootView.findViewById(R.id.refreshLayout);
//            TextHeaderView headerView = (TextHeaderView) View.inflate(getContext(),R.layout.header_tv,null);
//            refreshLayout.setHeaderView(headerView);
        }
        return rootView;
    }
}
