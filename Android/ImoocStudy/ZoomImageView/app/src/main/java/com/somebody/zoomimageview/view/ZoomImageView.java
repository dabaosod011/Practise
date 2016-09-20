package com.somebody.zoomimageview.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Copyright (c) 2016 Wen-xintech Inc. All rights reserved.
 * <p/>
 * Created by Hai Xiao on 9/17/16.
 * hai.xiao@wen-xintech.com
 */

public class ZoomImageView extends ImageView
        implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    private static final String TAG = "ZoomImageView";

    private boolean mOnce = false;

    private float mInitScale;   //  初始化是缩放的值
    private float mMidScale;    //  双击放大时到达的值
    private float mMaxScale;    //  放大的最大值

    private Matrix mScaleMatrix;
    private ScaleGestureDetector mScaleGestureDetector;

    //自由移动
    private int mLastPointerCount;
    private float mLastX;
    private float mLastY;
    private float mTouchSlop;
    private boolean isDragable;
    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    //双击放大与缩小
    private GestureDetector mGestureDetector;
    private boolean isAutoScale;

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScaleMatrix = new Matrix();
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onDoubleTap(MotionEvent e) {
                Log.d(TAG, "onDoubleTap() called with: e = [" + e + "], isAutoScale = " + isAutoScale);
                if (isAutoScale) return true;

                float x = e.getX();
                float y = e.getY();

                float curScale = getScale();
                Log.d(TAG, "onDoubleTap: currentScale = " + curScale);
                if (curScale < mMidScale) {
                    /*mScaleMatrix.postScale(mMidScale / curScale, mMidScale / curScale, x, y);
                    setImageMatrix(mScaleMatrix);*/
                    postDelayed(new AutoScaleRunnable(mMidScale, x, y), 16);
                    isAutoScale = true;
                } else {
                   /* mScaleMatrix.postScale(mInitScale / curScale, mInitScale / curScale, x, y);
                    setImageMatrix(mScaleMatrix);*/
                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                    isAutoScale = true;
                }

                return true;
            }
        });
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context) {
        this(context, null);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * layout布局完成之后,获取ImageView加载完成的图片
     */
    @Override public void onGlobalLayout() {
        if (!mOnce) {
            //得到控件的宽和高
            int width = getWidth();
            int height = getHeight();

            //得到图片及其宽和高
            Drawable drawable = getDrawable();
            if (null == drawable) return;
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();

            float scale = 1.0f;
            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }

            if (dh > height && dw < width) {
                scale = height * 1.0f / dh;
            }

            if ((dw > width && dh > height) || (dw < width && dh < height)) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            mInitScale = scale;
            mMidScale = mInitScale * 2;
            mMaxScale = mInitScale * 4;

            // 将图片移动至控件的中心
            int dx = getWidth() / 2 - dw / 2;
            int dy = getHeight() / 2 - dh / 2;

            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }
    }

    @Override public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    //  缩放的区间是: mInitScale ~ mMaxScale
    @Override public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        if (null == getDrawable()) return true;

        float scale = getScale();
        float scaleFactor = scaleGestureDetector.getScaleFactor();

        Log.d(TAG, "onScale: scale = " + scale + ", scaleFactor = " + scaleFactor);

        //  缩放范围的控制
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }

            if (scale * scaleFactor > mMaxScale) {
                scale = mMaxScale / scale;
            }

            mScaleMatrix.postScale(scaleFactor,
                                   scaleFactor,
                                   mScaleGestureDetector.getFocusX(),
                                   mScaleGestureDetector.getFocusY()
            );

            checkBoardAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }


        return false;
    }

    @Override public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    @Override public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mGestureDetector.onTouchEvent(motionEvent)) {
            return true;
        }

        mScaleGestureDetector.onTouchEvent(motionEvent);

        //  多点触控的中心点
        float x = 0, y = 0;
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += motionEvent.getX(i);
            y += motionEvent.getY(i);
        }
        x /= pointerCount;
        y /= pointerCount;

        if (mLastPointerCount != pointerCount) {
            isDragable = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCount = pointerCount;

        Log.d(TAG, "onTouch: pointerCount = " + pointerCount + ", x = " + x + ", y = " + y);
        RectF matrixRectF = getMatrixRectF();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (matrixRectF.width() > getWidth() + 0.01f || matrixRectF.height() > getHeight() + 0.01f) {
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (matrixRectF.width() > getWidth() + 0.01f || matrixRectF.height() > getHeight() + 0.01f) {
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true);
                }

                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isDragable) {
                    isDragable = isMoveAction(dx, dy);
                }

                if (isDragable) {
                    if (null != getDrawable()) {
                        isCheckLeftAndRight = true;
                        isCheckTopAndBottom = true;
                        if (matrixRectF.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        if (matrixRectF.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkBoardWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }
                }

                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mLastPointerCount = 0;
                break;

            default:
                break;
        }

        return true;
    }

    //  获取当前图片的缩放值
    private float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);

        return values[Matrix.MSCALE_X];
    }

    // 获取图片放大缩小以后的宽和高,以及 l, r, t, b
    private RectF getMatrixRectF() {
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();

        if (null != drawable) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mScaleMatrix.mapRect(rectF);
        }

        return rectF;
    }

    //  当缩放图片是,进行边界和中心点检查
    private void checkBoardAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0, deltaY = 0;
        int width = getWidth();
        int height = getHeight();

        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = 0 - rectF.left;
            }

            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        } else {
            //如果图片的宽度小于控件的宽,使其居中
            deltaX = width / 2 - rectF.right + rectF.width() / 2;
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = 0 - rectF.top;
            }

            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        } else {
            //如果图片的高度小于控件的高,使其居中
            deltaY = height / 2 - rectF.bottom + rectF.height() / 2;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }

    //  当移动图片时,进行边界检查
    private void checkBoardWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0, deltaY = 0;
        int width = getWidth();
        int height = getHeight();

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = 0 - rectF.top;
        }

        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }

        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = 0 - rectF.left;
        }

        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    private class AutoScaleRunnable implements Runnable {
        private static final String TAG = "AutoScaleRunnable";

        //  缩放的中心点
        private float x;
        private float y;
        private float targetScale;
        private float tmpScale;

        private final float BIGGER = 1.07f;
        private final float SMALLER = 0.93f;

        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.targetScale = targetScale;
            this.x = x;
            this.y = y;

            if (getScale() < targetScale) {
                tmpScale = BIGGER;
            }
            if (getScale() > targetScale) {
                tmpScale = SMALLER;
            }
        }

        @Override public void run() {
            Log.d(TAG, "run: tmpScale = " + tmpScale + ", targetScale = " + targetScale);
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBoardAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            float curScale = getScale();
            if ((tmpScale > 1.0f && curScale < targetScale) || (tmpScale < 1.0f && curScale > targetScale)) {
                postDelayed(this, 16);
            } else {
                float scale = targetScale / curScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBoardAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);

                isAutoScale = false;
            }
        }
    }
}
