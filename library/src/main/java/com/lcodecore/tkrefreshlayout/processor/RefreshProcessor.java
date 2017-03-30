package com.lcodecore.tkrefreshlayout.processor;

import android.view.MotionEvent;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.utils.ScrollingUtil;

/**
 * Created by lcodecore on 2017/3/1.
 */

public class RefreshProcessor implements IDecorator {

    protected TwinklingRefreshLayout.CoContext cp;

    public RefreshProcessor(TwinklingRefreshLayout.CoContext processor) {
        if (processor == null) throw new NullPointerException("The coprocessor can not be null.");
        cp = processor;
    }

    private float mTouchX, mTouchY;
    private boolean intercepted = false;
    private boolean willAnimHead = false;
    private boolean willAnimBottom = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = ev.getX();
                mTouchY = ev.getY();
                cp.dispatchTouchEventSuper(ev);
                return true;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getX() - mTouchX;
                float dy = ev.getY() - mTouchY;
                if (!intercepted && Math.abs(dx) <= Math.abs(dy) && Math.abs(dy) > cp.getTouchSlop()) {//滑动允许最大角度为45度
                    if (dy > 0 && ScrollingUtil.isViewToTop(cp.getTargetView(), cp.getTouchSlop()) && cp.allowPullDown()) {
                        cp.setStatePTD();
                        mTouchX = ev.getX();
                        mTouchY = ev.getY();
                        intercepted = true;
                        return true;
                    } else if (dy < 0 && ScrollingUtil.isViewToBottom(cp.getTargetView(), cp.getTouchSlop()) && cp.allowPullUp()) {
                        cp.setStatePBU();
                        mTouchX = ev.getX();
                        mTouchY = ev.getY();
                        intercepted = true;
                        return true;
                    }
                }
                if (intercepted) {
                    if (cp.isRefreshVisible() || cp.isLoadingVisible()) {
                        return cp.dispatchTouchEventSuper(ev);
                    }
                    if (!cp.isPrepareFinishRefresh() && cp.isStatePTD()) {
                        if (dy < -cp.getTouchSlop() || !ScrollingUtil.isViewToTop(cp.getTargetView(), cp.getTouchSlop())) {
                            cp.dispatchTouchEventSuper(ev);
                        }
                        dy = Math.min(cp.getMaxHeadHeight() * 2, dy);
                        dy = Math.max(0, dy);
                        cp.getAnimProcessor().scrollHeadByMove(dy);
                    } else if (!cp.isPrepareFinishLoadMore() && cp.isStatePBU()) {
                        //加载更多的动作
                        if (dy > cp.getTouchSlop() || !ScrollingUtil.isViewToBottom(cp.getTargetView(), cp.getTouchSlop())) {
                            cp.dispatchTouchEventSuper(ev);
                        }
                        dy = Math.max(-cp.getMaxBottomHeight() * 2, dy);
                        dy = Math.min(0, dy);
                        cp.getAnimProcessor().scrollBottomByMove(Math.abs(dy));
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                cp.setPrepareFinishRefresh(false);
                cp.setPrepareFinishLoadMore(false);
                if (intercepted) {
                    if (cp.isStatePTD()) {
                        willAnimHead = true;
                    } else if (cp.isStatePBU()) {
                        willAnimBottom = true;
                    }
                    intercepted = false;
                    return true;
                }
                break;
        }
        return cp.dispatchTouchEventSuper(ev);
    }

    @Override
    public boolean interceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mTouchX = ev.getX();
//                mTouchY = ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float dx = ev.getX() - mTouchX;
//                float dy = ev.getY() - mTouchY;
//                if (Math.abs(dx) <= Math.abs(dy) && Math.abs(dy) > cp.getTouchSlop()) {//滑动允许最大角度为45度
//                    if (dy > 0 && ScrollingUtil.isViewToTop(cp.getTargetView(), cp.getTouchSlop()) && cp.allowPullDown()) {
//                        cp.setStatePTD();
//                        return true;
//                    } else if (dy < 0 && ScrollingUtil.isViewToBottom(cp.getTargetView(), cp.getTouchSlop()) && cp.allowPullUp()) {
//                        cp.setStatePBU();
//                        return true;
//                    }
//                }
//                break;
//        }
        return false;
    }

    @Override
    public boolean dealTouchEvent(MotionEvent e) {
//        if (cp.isRefreshVisible() || cp.isLoadingVisible()) return false;
//
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                float dy = e.getY() - mTouchY;
//                if (cp.isStatePTD()) {
//                    dy = Math.min(cp.getMaxHeadHeight() * 2, dy);
//                    dy = Math.max(0, dy);
//                    cp.getAnimProcessor().scrollHeadByMove(dy);
//                } else if (cp.isStatePBU()) {
//                    //加载更多的动作
//                    dy = Math.min(cp.getMaxBottomHeight() * 2, Math.abs(dy));
//                    dy = Math.max(0, dy);
//                    cp.getAnimProcessor().scrollBottomByMove(dy);
//                }
//                return true;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                if (cp.isStatePTD()) {
//                    cp.getAnimProcessor().dealPullDownRelease();
//                } else if (cp.isStatePBU()) {
//                    cp.getAnimProcessor().dealPullUpRelease();
//                }
//                return true;
//        }
        return false;
    }

    @Override
    public void onFingerDown(MotionEvent ev) {
    }

    @Override
    public void onFingerUp(MotionEvent ev, boolean isFling) {
        if (!isFling && willAnimHead) {
            cp.getAnimProcessor().dealPullDownRelease();
        }
        if (!isFling && willAnimBottom) {
            cp.getAnimProcessor().dealPullUpRelease();
        }
        willAnimHead = false;
        willAnimBottom = false;
    }

    @Override
    public void onFingerScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY, float velocityX, float velocityY) {
        //手指在屏幕上滚动，如果此时正处在刷新状态，可隐藏
        int mTouchSlop = cp.getTouchSlop();
        if (cp.isRefreshVisible() && distanceY >= mTouchSlop && !cp.isOpenFloatRefresh()) {
            cp.getAnimProcessor().animHeadHideByVy((int) velocityY);
        }
        if (cp.isLoadingVisible() && distanceY <= -mTouchSlop) {
            cp.getAnimProcessor().animBottomHideByVy((int) velocityY);
        }
    }

    @Override
    public void onFingerFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    }
}
