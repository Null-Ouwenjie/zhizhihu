package com.ouwenjie.zhizhihu.ui.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ouwenjie.zhizhihu.App;
import com.ouwenjie.zhizhihu.common.ThemeManager;
import com.ouwenjie.zhizhihu.ui.view.ProgressHelper;

public class BaseActivity extends AppCompatActivity {

    protected int mTheme = ThemeManager.DEFAULT_THEME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mTheme = ThemeManager.getCurTheme(this);
        } else {
            mTheme = savedInstanceState.getInt("theme");
        }
        setTheme(mTheme);
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mTheme != ThemeManager.getCurTheme(this)) {
            reload();
        }
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


//    protected void openWebView(String url) {
//        Intent intent = new Intent(this, WebBrowserActivity.class);
//        intent.putExtra("url", url);
//        startActivity(intent);
//    }

    public Context getContext() {
        return getApplicationContext();
    }

    public void toast(String msg) {
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private ProgressHelper progressHelper = new ProgressHelper(this);

    public void showProgress() {
        showProgress(null);
    }

    public void showProgress(String msg) {
        progressHelper.showProgress(msg);
    }

    public void hideProgress() {
        progressHelper.hideProgress();
    }


}
