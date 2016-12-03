package com.lcodecore.twinklingrefreshlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lcodecore.tkrefreshlayout.v2.TwinklingRefreshLayout;

/**
 * Created by lcodecore on 2016/10/2.
 */

public class WebViewFragment extends Fragment {

    private View rootView;

    private WebView mWebView;
    private boolean mIsWebViewAvailable;

    public WebViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_webview, container, false);
            mWebView = (WebView) rootView.findViewById(R.id.webView);
            TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) rootView.findViewById(R.id.refreshLayout);
            refreshLayout.setPureScrollModeOn(true);
            //refreshLayout.setEnableLoadmore(false);
            //refreshLayout.setEnableOverlayRefreshView(false);
        }
        mWebView.loadUrl("http://lcodecorex.github.io");
        mIsWebViewAvailable = true;
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        mIsWebViewAvailable = false;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    public WebView getWebView() {
        return mIsWebViewAvailable ? mWebView : null;
    }
}
