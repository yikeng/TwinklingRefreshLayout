package com.lcodecore.tkrefreshlayout.v2;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Handler;
import android.view.animation.DecelerateInterpolator;

import com.lcodecore.tkrefreshlayout.v2.TwinklingRefreshLayout.CoProcessor;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by lcodecore on 2016/11/26.
 */

public class AnimProcessor {
    //AnimTasks

    private CoProcessor cp;
    private ValueAnimator va;
    private static final float animFraction = 1.5f;
    //动画的变化率
    private DecelerateInterpolator decelerateInterpolator;

    public AnimProcessor(CoProcessor coProcessor) {
        this.cp = coProcessor;

        decelerateInterpolator = new DecelerateInterpolator(10);

        va = new ValueAnimator();
        va.setInterpolator(new DecelerateInterpolator());
        va.addUpdateListener(animUpListener);
    }


//    private boolean isRefreshing, isLoadingmore, isOverScrollTop, isOverScrollBottom;
//    private boolean isPureScrollModeOn, isOverlayRefreshShow, floatRefresh, enableOverScrollTop, enableOverScrollBottom;
//
//    private int PULLING_TOP_DOWN = 0;
//    private int PULLING_BOTTOM_UP = 1;
//    private int state = PULLING_TOP_DOWN;


    public void init() {
//        mChildView.animate().setInterpolator(new DecelerateInterpolator());

        //在动画开始的地方快然后慢;

    }


    public void scrollHeadByMove(float moveY) {
        float offsetY = decelerateInterpolator.getInterpolation(moveY / cp.getMaxHeadHeight() / 2) * moveY / 2;

        if (cp.getHeader().getVisibility() != VISIBLE) cp.getHeader().setVisibility(VISIBLE);

//        cp.getHeader().getLayoutParams().height = (int) offsetY;
//        cp.getHeader().requestLayout();
        cp.getHeader().setTranslationY(-cp.getHeadHeight() + offsetY);

        if (!cp.isOpenFloatRefresh()) cp.getContent().setTranslationY(offsetY);

        cp.onPullingDown(offsetY);
    }

    public void scrollBottomByMove(float moveY) {
        float offsetY = decelerateInterpolator.getInterpolation(moveY / cp.getBottomHeight() / 2) * moveY / 2;

        if (cp.getFooter().getVisibility() != VISIBLE) cp.getFooter().setVisibility(VISIBLE);

//        cp.getFooter().getLayoutParams().height = (int) -offsetY;
//        cp.getFooter().requestLayout();
        cp.getFooter().setTranslationY(cp.getBottomHeight() - offsetY);

        cp.getContent().setTranslationY(-offsetY);

        cp.onPullingUp(-offsetY);
    }

    public void dealPullDownRelease() {
        if (!cp.isPureScrollModeOn() && getVisibleHeadHeight() >= cp.getHeadHeight() - cp.getTouchSlop()) {
            animHeadToRefresh();
            cp.setRefreshing(true);

            cp.onRefresh();
        } else {
            animHeadBack();
        }
    }

    public void dealPullUpRelease() {
        if (!cp.isPureScrollModeOn() && getVisibleFootHeight() >= cp.getBottomHeight() - cp.getTouchSlop()) {
            cp.setLoadingMore(true);
            animBottomToLoad();

            cp.onLoadMore();
        } else {
            animBottomBack();
        }
    }

    private int getVisibleHeadHeight() {
        return (int) (cp.getHeadHeight() + cp.getHeader().getTranslationY());
    }

    private int getVisibleFootHeight() {
        return (int) (cp.getBottomHeight() - cp.getFooter().getTranslationY());
    }

    private void animHeadToRefresh() {
        //if (!floatRefresh) animChildView(mHeadHeight);
        animLayoutByTime(getVisibleHeadHeight(), (int) cp.getHeadHeight());
    }

    private void animHeadBack() {
//        if (!floatRefresh) animChildView(0f);
//        else animFloatRefresh(0f);
        animLayoutByTime(getVisibleHeadHeight(), 0);
    }

    private void animBottomToLoad() {
        animLayoutByTime(getVisibleFootHeight(), (int) cp.getBottomHeight());
    }

    private void animBottomBack() {
        animLayoutByTime(getVisibleFootHeight(), 0);
    }

    public void animHeadByVy(int vy) {
        //finishRefreshing();
        animLayoutByVy((int) cp.getHeadHeight(), 0, vy);
    }

    public void animBottomByVy(int vy) {
        //finishLoadmore();
        animLayoutByVy((int) cp.getBottomHeight(), 0, vy);
    }

    //保证快速回到不可见状态
    public void animLayoutByVy(int start, int end, int vy) {
        if (vy == 0) vy = 5000;
        animLayoutByTime(start, end, 5 * Math.abs((start - end) * 1000 / vy));
    }

    public void animLayoutByTime(int start, int end) {
        va.setIntValues(start, end);
        va.setDuration((int) (Math.abs(start - end) * animFraction));
        va.start();
    }

    public void animLayoutByTime(int start, int end, long time) {
        va.setIntValues(start, end);
        va.setDuration(time);
        va.start();
    }

    public void animLayoutByTime(int start, int end, long time, AnimatorListener listener) {
        va.setIntValues(start, end);
        va.setDuration(time);
        va.addListener(listener);
        va.start();
    }

    public void animLayoutByTime(int start, int end, long time, AnimatorUpdateListener listener) {
        va.setIntValues(start, end);
        va.setDuration(time);
        va.addUpdateListener(listener);
        va.start();
    }



    private AnimatorUpdateListener animUpListener = new AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int height = (int) animation.getAnimatedValue();

            if (cp.isStatePTD()) {
                cp.getHeader().setTranslationY(-cp.getHeadHeight() + height);
//                cp.getHeader().getLayoutParams().height = height;
//                cp.getHeader().requestLayout();//重绘

                if (!cp.isOpenFloatRefresh()) cp.getContent().setTranslationY(height);

                if (cp.isOverScrollRefreshShow()) {
                    cp.getHeader().setVisibility(VISIBLE);
                    cp.getFooter().setVisibility(GONE);
                }

                cp.onPullDownReleasing(height);
            } else if (cp.isStatePBU()) {
                cp.getFooter().setTranslationY(cp.getBottomHeight() - height);
//                cp.getFooter().getLayoutParams().height = height;
//                cp.getFooter().requestLayout();

                cp.getContent().setTranslationY(-height);

                if (cp.isOverScrollRefreshShow()) {
                    cp.getHeader().setVisibility(GONE);
                    cp.getFooter().setVisibility(VISIBLE);
                }

                cp.onPullUpReleasing(height);
            }
        }
    };

    private AnimatorUpdateListener overScrollTopUpListener = new AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int height = (int) animation.getAnimatedValue();
            cp.getHeader().setTranslationY(-cp.getHeadHeight() + height);
            if (cp.isOverScrollRefreshShow()) cp.getContent().setTranslationY(height);
        }
    };

    /**
     * 执行顶部越界  To executive cross-border springback at the top.
     * 越界高度height ∝ vy/computeTimes，此处采用的模型是 height=A*(vy + B)/computeTimes
     *
     * @param vy           满足越界条件的手指滑动速度  the finger sliding speed on the screen.
     * @param computeTimes 从满足条件到滚动到顶部总共计算的次数 Calculation times from sliding to top.
     */
    public void animOverScrollTop(float vy, int computeTimes) {
//        if (vy == 0) vy = 5000;
        cp.setStatePTD();
        int oh = (int) Math.abs(vy / computeTimes / 2);
        final int overHeight = oh > 300 ? 300 : oh;
        final int time = overHeight <= 50 ? 105 : (int) (0.5 * overHeight + 80);
        animLayoutByTime(0, overHeight, time);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animLayoutByTime(overHeight, 0, time);
            }
        }, time);
    }

    public void animOverScrollBottom(float vy, int computeTimes) {
//        if (vy == 0) vy = 5000;
        cp.setStatePBU();
        int oh = (int) Math.abs(vy / computeTimes / 2);
        final int overHeight = oh > 300 ? 300 : oh;
        final int time = overHeight <= 50 ? 105 : (int) (0.5 * overHeight + 80);
        animLayoutByTime(0, overHeight, time);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animLayoutByTime(overHeight, 0, time);
            }
        }, time);
    }
}
