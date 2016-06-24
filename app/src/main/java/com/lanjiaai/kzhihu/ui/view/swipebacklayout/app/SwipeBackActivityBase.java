package com.lanjiaai.kzhihu.ui.view.swipebacklayout.app;

import com.lanjiaai.kzhihu.ui.view.swipebacklayout.SwipeBackLayout;

/**
 * Created by 文杰 on 2015/11/5.
 */
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();

}
