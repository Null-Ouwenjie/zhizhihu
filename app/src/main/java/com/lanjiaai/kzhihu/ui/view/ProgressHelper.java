package com.lanjiaai.kzhihu.ui.view;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Jack on 2016/1/19.
 */
public class ProgressHelper {

    private Context context;

    protected ProgressDialog progressDialog;

    public ProgressHelper(Context context) {
        this.context = context;
    }

    public void showProgress(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
        }
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void showProgress() {
        showProgress("正在加载...");
    }

    public void hideProgress() {
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }
}
