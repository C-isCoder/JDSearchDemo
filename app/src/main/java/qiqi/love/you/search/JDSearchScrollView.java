package qiqi.love.you.search;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * Created by iscod on 2016/6/4.
 */
public class JDSearchScrollView extends ScrollView {
    private final String TAG = "JDSearchScrollView";
    /**
     * 头部高度
     */
    private int mHeight;
    /**
     * 左边的TextView
     */
    private TextView mLeft;
    /**
     * 右边的TextView
     */
    private TextView mRight;
    /**
     * 中间的TextView
     */
    private TextView mCenter;
    /**
     * 搜索框布局
     */
    private View mView;
    /**
     * 初始默认颜色
     * 默认透明
     */
    private int mDefaultBackgroundColor = Color.TRANSPARENT;
    /**
     * 初始字体颜色
     * 默认白色
     */
    private int mDefaultTextColor = Color.WHITE;
    /**
     * 改变的背景颜色
     * 默认白色
     */
    private int mChangeBackgroundColor = Color.WHITE;
    /**
     * 改变的背景颜色RGB
     * 默认白色
     */
    private int r = 255, g = 255, b = 255;
    /**
     * 启用RGB颜色
     */
    private boolean onRGB = false;
    /**
     * 改变的字体颜色
     * 默认黑色
     */
    private int mChangeTextColor = Color.BLACK;
    /**
     * 外部实现，把滚动的坐标Y传递出去
     */
    private onScrolledY mOnScrolledY;
    /**
     * 是否外部调用
     * 默认不是
     */
    private boolean isOut = false;

    public JDSearchScrollView(Context context) {
        super(context);
    }

    public JDSearchScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JDSearchScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //ScrollView有且只有一个子布局LinearLayout
        LinearLayout linearLayout = (LinearLayout) getChildAt(0);
        if (linearLayout.getChildCount() != 0 && !isOut) {
            View view = linearLayout.getChildAt(0);
            linearLayout.getChildCount();
            mHeight = view.getMeasuredHeight() + linearLayout.getPaddingTop()
                    + linearLayout.getPaddingBottom();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0 && !isOut) {
            mView.setBackgroundColor(mDefaultBackgroundColor);
        }
        if (scrollY <= (mHeight / 2) && !isOut) {
            mLeft.setTextColor(mDefaultTextColor);
            mRight.setTextColor(mDefaultTextColor);
            mCenter.setTextColor(mDefaultTextColor);
        }
        if (scrollY >= (mHeight / 2) && scrollY <= mHeight && !isOut) {
            float transition = scrollY * (1f / mHeight);
            int alpha = (int) (transition * 255);
            if (onRGB) {
                int color = Color.argb(alpha, r, g, b);
                mView.setBackgroundColor(color);
                mLeft.setTextColor(mChangeTextColor);
                mRight.setTextColor(mChangeTextColor);
                mCenter.setTextColor(mChangeTextColor);
            } else {
                //mView.setAlpha(alpha);
                //mView.setBackgroundColor(mChangeBackgroundColor);
                int color = Color.argb(alpha, r, g, b);
                mView.setBackgroundColor(color);
                mLeft.setTextColor(mChangeTextColor);
                mRight.setTextColor(mChangeTextColor);
                mCenter.setTextColor(mChangeTextColor);
            }
        }
        if (mOnScrolledY == null && isOut) {
            throw new NullPointerException("mOnScrolledY cannot null, please setOnScrolledYet. ");
        }
        if (isOut) {
            //回调接口
            mOnScrolledY.onScrolled(scrollY);
        }
    }

    public void setOnScrolledY(onScrolledY l) {
        if (l != null)
            mOnScrolledY = l;
    }

    public interface onScrolledY {
        void onScrolled(int scrollY);
    }

    public void setLeft(TextView leftView) {
        if (leftView != null)
            mLeft = leftView;
    }

    public void setRight(TextView rightView) {
        if (rightView != null)
            mRight = rightView;
    }

    public void setCenter(TextView centerView) {
        if (centerView != null)
            mCenter = centerView;
    }

    public void setView(View view) {
        if (view != null)
            mView = view;
    }

    public void setDefaultBackgroundColor(@ColorInt int color) {
        mDefaultBackgroundColor = color;
    }

    public void setChangeBackgroundColor(@ColorInt int color) {
        mChangeBackgroundColor = color;
    }

    public void setChangeTextColor(@ColorInt int color) {
        mChangeTextColor = color;
    }

    public void setBackgroundRBG(int r, int g, int b, boolean isRGB) {
        this.r = r;
        this.g = g;
        this.b = b;
        onRGB = isRGB;
    }

    public void setIsOut(boolean isOut) {
        this.isOut = isOut;
    }
}
