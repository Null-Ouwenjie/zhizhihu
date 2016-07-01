package com.ouwenjie.zhizhihu.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.ui.activity.base.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutUsActivity extends SwipeBackActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.version_name_txt)
    TextView mVersionNameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

        initTitleBar();
    }

    private void initTitleBar() {
        setSupportActionBar(mToolbar);
        setTitle("关于我们");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AboutUsActivity.this.onBackPressed();
                    }
                }
        );
    }


}
