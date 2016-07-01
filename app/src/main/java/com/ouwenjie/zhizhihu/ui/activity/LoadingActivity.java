package com.ouwenjie.zhizhihu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.ui.activity.base.BaseActivity;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoadingActivity extends BaseActivity {

    public static final int MSG_PASS = 1; // 进入主页
    public final long MIN_WAITTING_MSEC = 1000; // 最少等待的毫秒数
    private Handler mHandler = new WeakHandler(this);

    @Bind(R.id.loading_img)
    ImageView mLoadingImg;
    @Bind(R.id.logo_img)
    ImageView mLogoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 实现窗口全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置无标题样式
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 3 秒后进入主界面
        mHandler.removeMessages(MSG_PASS);
        mHandler.sendEmptyMessageDelayed(MSG_PASS, MIN_WAITTING_MSEC * 3);
        Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha_0_1);
        mLogoImg.startAnimation(alpha);
    }

    /**
     * 进入主页
     */
    private void pass() {
        Intent intent;
        intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }


    static class WeakHandler extends Handler {
        WeakReference<LoadingActivity> mActivity;

        public WeakHandler(LoadingActivity activity) {
            mActivity = new WeakReference<LoadingActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingActivity activity = mActivity.get();
            if (null != activity) {
                switch (msg.what) {
                    case MSG_PASS: // 跳转
                        activity.pass();
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
