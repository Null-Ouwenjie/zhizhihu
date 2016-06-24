package com.lanjiaai.kzhihu.ui.viewInterface;

import android.content.Context;

/**
 * Created by Jack on 2015/11/18.
 */
public interface MVPView {

    Context getContext();

    void showProgress();
    void hideProgress();

    void toast(String msg);

}
