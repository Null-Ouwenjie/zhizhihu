package com.ouwenjie.zhizhihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.model.api.ZhiHu;
import com.ouwenjie.zhizhihu.ui.activity.base.SwipeBackActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebBrowserActivity extends SwipeBackActivity {

    public static final String URL = "url";

    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra(URL);
        webView.getSettings().setJavaScriptEnabled(true);// 设置可以加载JavaScript的页面
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings()
                .setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.setVerticalScrollBarEnabled(true);
        webView.setScrollbarFadingEnabled(false);
        webView.setVerticalFadingEdgeEnabled(false);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient() {
            // 这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，
            // 进行预先定义的其他操作，这对一个程序是非常必要的。
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("UrlLoading=>", url);
                if (url.contains("intent:")) {
                    openZhiHuClient();
                } else if (!url.contains("tel:")) {//页面上有数字会导致连接电话
                    view.loadUrl(url);
                }
                return true;
            }

            // 接收到Http请求的事件
            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            // 这个事件就是开始载入页面调用的，通常我们可以在这设定一个loading的页面，告诉用户程序在等待网络响应。
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            // 同样道理，我们知道一个页面载入完成，于是我们可以关闭loading条，切换程序动作
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressBar.getVisibility() != View.GONE) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        if (17 <= Build.VERSION.SDK_INT) {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
        }
        Logger.d(url);
        webView.loadUrl(url);
//        webView.loadUrl("https://www.zhihu.com/topic/19550564/top-answers");
    }

    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (webView.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            webView.goBack();   //goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCoder, event);
    }

    public void openZhiHuClient() {
        boolean hasZhiHuClient = isAvilible(WebBrowserActivity.this, ZhiHu.PACKAGE_NAME);
        if (hasZhiHuClient) {
            Log.e("onItemClick=>", "hasZhiHuClient");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setPackage(ZhiHu.PACKAGE_NAME);
            startActivity(intent);
        } else {
            toast("请您安装知乎客户端");
        }
    }


    private boolean isAvilible(Context context, String packageName) {
        boolean hasInstalled = false;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(PackageManager.PERMISSION_GRANTED);
        for (PackageInfo p : list) {
            if (packageName != null && packageName.equals(p.packageName)) {
                hasInstalled = true;
                break;
            }
        }
        return hasInstalled;
    }


    /**
     * 提供给 js 的方法
     */

}
