package qiqi.love.you.search;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.jd_scroll_view)
    JDSearchScrollView mJdSearchScrollView;
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.relative_layout)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.tv_left)
    TextView mTextViewLeft;
    @BindView(R.id.tv_right)
    TextView mTextViewRight;
    @BindView(R.id.tv_center)
    TextView mTextViewCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView2();
    }

    private void initView2() {
        //是否外部调用
        //mJdSearchScrollView.setIsOut(true);
        mJdSearchScrollView.setBackgroundRGB(255, 200, 111, true);
        mJdSearchScrollView.setLeft(mTextViewLeft);
        mJdSearchScrollView.setRight(mTextViewRight);
        mJdSearchScrollView.setView(mRelativeLayout);
        mJdSearchScrollView.setCenter(mTextViewCenter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //写在这里，不然获取不到View的高度
            initView1();
        }
    }

    /**
     * 外部调用
     * 回调接口传出移动的Y轴的坐标
     */
    private void initView1() {
        final int head = imageView.getHeight();
        final int half = head / 2;
        mJdSearchScrollView.setOnScrolledY(new JDSearchScrollView.onScrolledY() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onScrolled(int scrollY) {
                Log.d("CID", "高度：" + head);
                Log.d("CID", "y点坐标" + scrollY);
                if (scrollY == 0) {
                    mTextViewRight.setTextColor(Color.WHITE);
                    mTextViewLeft.setTextColor(Color.WHITE);
                    mRelativeLayout.setBackgroundColor(Color.TRANSPARENT);
                }
                if (scrollY >= half && scrollY <= head) {
                    mTextViewRight.setTextColor(Color.BLACK);
                    mTextViewLeft.setTextColor(Color.BLACK);
                    float i = scrollY * (1f / head);
                    int alpha = (int) (i * 255);
                    int color = Color.argb(alpha, 255, 255, 255);
                    mRelativeLayout.setBackgroundColor(color);
                    Log.d("CID", "Alpha:" + alpha);
                }
            }
        });
    }
}
