package com.lcodecore.twinklingrefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout;
import com.lcodecore.tkrefreshlayout.v2.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.v2.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.lcodecore.twinklingrefreshlayout.adapter.CardAdapter;
import com.lcodecore.twinklingrefreshlayout.adapter.ViewPagerHolder;
import com.lcodecore.twinklingrefreshlayout.beans.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcodecore on 2016/10/1.
 */

public class RecyclerFragment extends Fragment {

    private View rootView;
    private CardAdapter cardAdapter;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
            rv = (RecyclerView) rootView.findViewById(R.id.recyclerview);
            setupRecyclerView(rv);
        }
        return rootView;
    }

    private void setupRecyclerView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        cardAdapter = new CardAdapter();
        rv.setAdapter(cardAdapter);

        final TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) rootView.findViewById(R.id.refresh);
        ProgressLayout headerView = new ProgressLayout(getContext());
        //BezierLayout headerView = new BezierLayout(getContext());
        refreshLayout.setHeaderView(headerView);

        addHeader();
        refreshCard();


        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshCard();
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreCard();
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });

//        refreshLayout.startRefresh();
    }

    void addHeader() {
        ViewPagerHolder holder = new ViewPagerHolder(rv.getContext(), rv);
        cardAdapter.setHeadHolder(holder);
    }

    void refreshCard() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("God of Light", "点亮世界之光", R.drawable.card_cover1));
        cards.add(new Card("我的手机与众不同", "专题", R.drawable.card_cover2));
        cards.add(new Card("BlackLight", "做最纯粹的微博客户端", R.drawable.card_cover3));
        cards.add(new Card("BuzzFeed", "最好玩的新闻在这里", R.drawable.card_cover4));
        cards.add(new Card("Nester", "专治各种熊孩子", R.drawable.card_cover5));
        cardAdapter.setDataList(cards);
    }

    void loadMoreCard() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("二次元专题", "啊喂，别总想去四维空间啦", R.drawable.card_cover6));
        cards.add(new Card("Music Player", "闻其名，余音绕梁", R.drawable.card_cover7));
        cards.add(new Card("el", "剪纸人の唯美旅程", R.drawable.card_cover8));
        cards.add(new Card("God of Light", "点亮世界之光", R.drawable.card_cover1));
        cards.add(new Card("BlackLight", "做最纯粹的微博客户端", R.drawable.card_cover3));
        cardAdapter.addItems(cards);
    }
}
