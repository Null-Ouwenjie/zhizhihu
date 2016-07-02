package com.ouwenjie.zhizhihu.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.common.LLog;
import com.ouwenjie.zhizhihu.model.api.ZhiHu;
import com.ouwenjie.zhizhihu.model.entity.Answer;
import com.ouwenjie.zhizhihu.model.entity.Post;
import com.ouwenjie.zhizhihu.presenter.PostAnswerPresenter;
import com.ouwenjie.zhizhihu.ui.activity.base.SwipeBackActivity;
import com.ouwenjie.zhizhihu.ui.adapter.PostAnswerAdapter;
import com.ouwenjie.zhizhihu.ui.viewInterface.PostAnswerViewInterface;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * 某一推荐下的答案列表
 */
public class PostAnswersActivity extends SwipeBackActivity implements PostAnswerViewInterface {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recycler_view)
    RecyclerView mAnswerListView;
    @Bind(R.id.progress)
    ProgressBar mProgressBar;

    private static Post sPost;

    private PostAnswerPresenter mPresenter;

    private Realm mRealm;
    private PostAnswerAdapter mPostAnswerAdapter;

    public static void startActivity(Context context, Post p) {
        sPost = p;
        context.startActivity(new Intent(context, PostAnswersActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_answers);
        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();

        initWidget();

        mPresenter = new PostAnswerPresenter(this);
        mPresenter.create();
        mPresenter.initData(sPost.getDate().replace("-", ""), sPost.getName());
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    private void initWidget() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String title = null;
        switch (sPost.getName()) {
            case "archive":
                title = sPost.getDate() + " " + "历史精华";
                break;
            case "recent":
                title = sPost.getDate() + " " + "近日热门";
                break;
            case "yesterday":
                title = sPost.getDate() + " " + "昨日更新";
                break;
            default:
                break;
        }
        setTitle(title);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAnswerListView.setLayoutManager(linearLayoutManager);
        mAnswerListView.hasFixedSize();
    }

    @Override
    public void initList(final List<Answer> answers) {
        mPostAnswerAdapter = new PostAnswerAdapter(this, answers);
        mPostAnswerAdapter.setOnItemClickListener(getOnItemClickListener(answers));
        mAnswerListView.setAdapter(mPostAnswerAdapter);
        mProgressBar.setVisibility(View.GONE);
    }

    private PostAnswerAdapter.OnItemClickListener getOnItemClickListener(final List<Answer> answers) {
        return new PostAnswerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Answer answer = answers.get(position);
                String qId = answer.getQuestionid();
                String aId = answer.getAnswerid();
                String url = String.format(ZhiHu.QUESTION_BASE_URL + "%s/answer/%s", qId, aId);

                int openType = Integer.parseInt(getSharedPreferences("com.ouwenjie.kzhihu_preferences", MODE_PRIVATE)
                        .getString(SettingsActivity.PREF_KEY_OPEN_TYPE, "1"));
                LLog.e("get open type = ", openType);
                if (openType == 1) {
                    Intent intent = new Intent(getContext(), WebBrowserActivity.class);
                    intent.putExtra(WebBrowserActivity.URL, url);
                    startActivity(intent);
                } else if (openType == 0) {
                    boolean hasZhiHuClient = isAvilible(PostAnswersActivity.this, ZhiHu.PACKAGE_NAME);
                    if (hasZhiHuClient) {
                        LLog.e("onItemClick=>", "hasZhiHuClient");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.setPackage(ZhiHu.PACKAGE_NAME);
                        startActivity(intent);
                    } else {
                        toast("请您安装知乎客户端");
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            }

            @Override
            public void onFavoriteClick(View view, int position) {
                Answer answer = answers.get(position);
                Answer favoriteAnswer = Realm.getDefaultInstance()
                        .where(Answer.class)
                        .equalTo("answerid", answer.getAnswerid())
                        .findFirst();
                if (favoriteAnswer != null) {
                    // remove
                    mRealm.beginTransaction();
                    favoriteAnswer.removeFromRealm();
                    mRealm.commitTransaction();
                    mPostAnswerAdapter.notifyDataSetChanged();
                    toast("取消收藏");
                } else {
                    // save
                    mRealm.beginTransaction();
                    mRealm.copyToRealm(answer);
                    mRealm.commitTransaction();
                    mPostAnswerAdapter.notifyDataSetChanged();
                    toast("已收藏");
                }
            }
        };
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

}
