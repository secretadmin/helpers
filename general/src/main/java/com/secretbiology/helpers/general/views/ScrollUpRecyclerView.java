package com.secretbiology.helpers.general.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * NCBSinfo © 2016, Secret Biology
 * https://github.com/NCBSinfo/NCBSinfo
 * Created by Rohit Suratekar on 15-11-16.
 */

public class ScrollUpRecyclerView extends RecyclerView {
    private boolean mScrollable;
    private int duration;
    private int translationY;
    private int initialY;
    private int startDelay;
    private float initialAlpha;

    public ScrollUpRecyclerView(Context context) {
        this(context, null);
        setDefaults();
    }

    public ScrollUpRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setDefaults();
    }

    public ScrollUpRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScrollable = false;
        setDefaults();
    }

    private void setDefaults() {
        this.duration = 500; //Default
        this.translationY = 0;
        this.startDelay = 100;
        this.initialAlpha = 0;
        this.initialY = 100;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return !mScrollable || super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            animate(getChildAt(i), i);

            if (i == getChildCount() - 1) {
                getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScrollable = true;
                    }
                }, i * 100);
            }
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean ismScrollable() {
        return mScrollable;
    }

    public void setmScrollable(boolean mScrollable) {
        this.mScrollable = mScrollable;
    }


    public void setTranslationY(int translationY) {
        this.translationY = translationY;
    }

    public int getInitialY() {
        return initialY;
    }

    public void setInitialY(int initialY) {
        this.initialY = initialY;
    }

    public int getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(int startDelay) {
        this.startDelay = startDelay;
    }

    public float getInitialAlpha() {
        return initialAlpha;
    }

    public void setInitialAlpha(float initialAlpha) {
        this.initialAlpha = initialAlpha;
    }

    private void animate(View view, final int pos) {
        view.animate().cancel();
        view.setTranslationY(initialY);
        view.setAlpha(initialAlpha);
        view.animate()
                .alpha(1.0f)
                .translationY(translationY)
                .setDuration(duration)
                .setStartDelay(pos * startDelay);
    }

}
