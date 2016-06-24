package com.lanjiaai.kzhihu.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lanjiaai.kzhihu.R;
import com.lanjiaai.kzhihu.ui.activity.base.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutUsActivity extends SwipeBackActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.version_name_txt)
    TextView versionNameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

        initTitleBar();
    }

    private void initTitleBar() {
        setSupportActionBar(toolbar);
        setTitle("关于我们");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AboutUsActivity.this.onBackPressed();
                    }
                }
        );
    }


}
