package com.lanjiaai.kzhihu.ui.view.swipebacklayout.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.lanjiaai.kzhihu.ui.view.swipebacklayout.SwipeBackLayout;
import com.lanjiaai.kzhihu.ui.view.swipebacklayout.Utils;

/**
 * Created by 文杰 on 2015/11/5.
 */
public class SwipeBackActivity extends FragmentActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}