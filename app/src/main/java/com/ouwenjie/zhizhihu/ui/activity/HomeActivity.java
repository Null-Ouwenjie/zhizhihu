package com.ouwenjie.zhizhihu.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.FrameLayout;

import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.ui.activity.base.BaseActivity;
import com.ouwenjie.zhizhihu.ui.fragment.PostFragment;
import com.ouwenjie.zhizhihu.ui.fragment.TopUserFragment;
import com.ouwenjie.zhizhihu.ui.fragment.UserCenterFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @Bind(R.id.container)
    FrameLayout container;

    private BottomBar mBottomBar;

    private PostFragment postFragment;
    private TopUserFragment topUserFragment;
    private UserCenterFragment userCenterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        postFragment = PostFragment.newInstance();
        topUserFragment = TopUserFragment.newInstance();
        userCenterFragment = UserCenterFragment.newInstance(null);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, postFragment).commit();

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.menu_home, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                // The user selected item number one.
                if (menuItemId == R.id.posts) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, postFragment).commit();
                } else if (menuItemId == R.id.bigv) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, topUserFragment).commit();
                } else if (menuItemId == R.id.me) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, userCenterFragment).commit();
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                // The user reselected item number one, scroll your content to top.
                if (menuItemId == R.id.posts) {
                    // The user selected item number one.
                } else if (menuItemId == R.id.bigv) {

                } else if (menuItemId == R.id.me) {

                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

}
