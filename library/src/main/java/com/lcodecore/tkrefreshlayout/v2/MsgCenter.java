package com.lcodecore.tkrefreshlayout.v2;


import android.view.View;

import com.lcodecore.tkrefreshlayout.utils.ScrollingUtil;

public class MsgCenter {
    private boolean enableOverScroll; //是否允许越界
    private boolean isPureOverScrollModeOn; //是否开启纯净的越界模式
    private boolean overScrollAutoLoadmore;
    private boolean enableRefresh;
    private boolean enableLoadmore;
    private boolean enableScrollTop;
    private boolean enableScrollBottom;


    private View mChildView;

    public MsgCenter(){
        //根据初始化参数判断需要打开什么系统
    }


}