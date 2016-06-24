package com.lanjiaai.kzhihu.ui.activity;

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

import com.lanjiaai.kzhihu.R;
import com.lanjiaai.kzhihu.common.LLog;
import com.lanjiaai.kzhihu.model.api.ZhiHu;
import com.lanjiaai.kzhihu.model.entity.Answer;
import com.lanjiaai.kzhihu.model.entity.Post;
import com.lanjiaai.kzhihu.presenter.PostAnswerPresenter;
import com.lanjiaai.kzhihu.ui.activity.base.SwipeBackActivity;
import com.lanjiaai.kzhihu.ui.adapter.PostAnswerAdapter;
import com.lanjiaai.kzhihu.ui.viewInterface.PostAnswerViewInterface;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * 某一推荐下的答案列表
 */
public class PostAnswersActivity extends SwipeBackActivity implements PostAnswerViewInterface {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.progress)
    ProgressBar progress;

    private static Post post;

    private PostAnswerPresenter presenter;

    private Realm mRealm;
    private PostAnswerAdapter postAnswerAdapter;

    public static void startActivity(Activity activity, Post p) {
        post = p;
        activity.startActivity(new Intent(activity, PostAnswersActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_answers);
        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();

        initWidget();

        presenter = new PostAnswerPresenter(this);
        presenter.initData(post.getDate().replace("-", ""), post.getName());
        progress.setVisibility(View.VISIBLE);
    }

    private void initWidget() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String title = null;
        switch (post.getName()) {
            case "archive":
                title = post.getDate() + " " + "历史精华";
                break;
            case "recent":
                title = post.getDate() + " " + "近日热门";
                break;
            case "yesterday":
                title = post.getDate() + " " + "昨日更新";
                break;
            default:
                break;
        }
        setTitle(title);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();
    }

    @Override
    public void initList(final List<Answer> answers) {
        postAnswerAdapter = new PostAnswerAdapter(this, answers);
        postAnswerAdapter.setOnItemClickListener(getOnItemClickListener(answers));
        recyclerView.setAdapter(postAnswerAdapter);
        progress.setVisibility(View.GONE);
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
                    intent.putExtra("url", url);
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
                    postAnswerAdapter.notifyDataSetChanged();
                    toast("取消收藏");
                } else {
                    // save
                    mRealm.beginTransaction();
                    mRealm.copyToRealm(answer);
                    mRealm.commitTransaction();
                    postAnswerAdapter.notifyDataSetChanged();
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
