package com.ouwenjie.zhizhihu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.model.api.ZhiHu;
import com.ouwenjie.zhizhihu.model.entity.Answer;
import com.ouwenjie.zhizhihu.ui.activity.base.SwipeBackActivity;
import com.ouwenjie.zhizhihu.ui.adapter.PostAnswerAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MyFavoriteActivity extends SwipeBackActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.progress)
    ProgressBar mProgressBar;
    @Bind(R.id.my_favorite_answer_list_view)
    RecyclerView mMyFavoriteAnswerListView;

    private Realm mRealm;
    private PostAnswerAdapter mPostAnswerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("我的收藏");

        mRealm = Realm.getDefaultInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMyFavoriteAnswerListView.setLayoutManager(linearLayoutManager);
        mMyFavoriteAnswerListView.hasFixedSize();

    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Answer> answers = mRealm.allObjects(Answer.class);
        initList(answers);
    }


    public void initList(final List<Answer> answers) {
        mPostAnswerAdapter = new PostAnswerAdapter(this, answers);
        mPostAnswerAdapter.setOnItemClickListener(getOnItemClickListener(answers));
        mMyFavoriteAnswerListView.setAdapter(mPostAnswerAdapter);
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
                Intent intent = new Intent(getContext(), WebBrowserActivity.class);
                intent.putExtra(WebBrowserActivity.URL, url);
                startActivity(intent);

//                boolean hasZhiHuClient = isAvilible(PostAnswersActivity.this, ZhiHu.PACKAGE_NAME);
//                if (hasZhiHuClient) {
//                    Log.e("onItemClick=>", "hasZhiHuClient");
//                    String url = String.format(ZhiHu.QUESTION_BASE_URL + "%s/answer/%s", qId, aId);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    intent.setPackage(ZhiHu.PACKAGE_NAME);
//                    startActivity(intent);
//                } else {
//                    toast("请您安装知乎客户端");
//                }
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
}