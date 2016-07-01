package com.ouwenjie.zhizhihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.common.LLog;
import com.ouwenjie.zhizhihu.model.api.ZhiHu;
import com.ouwenjie.zhizhihu.model.entity.UserDetail;
import com.ouwenjie.zhizhihu.model.mImp.ApiImp;
import com.ouwenjie.zhizhihu.ui.activity.base.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

public class UserProfileActivity extends SwipeBackActivity {

    public static final String KEY_USER_HASH = "user_profile_user_hash";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.top_answer_list_view)
    XRecyclerView mTopAnswerListView;
    @Bind(R.id.progress)
    ProgressBar mProgressBar;

    CircleImageView mUserAvatarImg;
    TextView mUserDescriptionTxt;
    TextView mUserSignatureTxt;
    TextView mAgreeCountTxt;
    TextView mFollowerCountTxt;
    TextView mFavCountTxt;
    TextView mAskCountTxt;
    TextView mAnswerCountTxt;
    TextView mPostCountTxt;

    private String mUserHash;

    public static Intent getStartIntent(Context context, String userHash) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra(KEY_USER_HASH, userHash);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserProfileActivity.this.onBackPressed();
                    }
                }
        );
        mUserHash = getIntent().getStringExtra(KEY_USER_HASH);
        new ApiImp().getUserDetail(mUserHash)
                .subscribe(new Subscriber<UserDetail>() {
                    @Override
                    public void onCompleted() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(UserDetail detail) {
                        createView(detail);
                    }
                });
    }

    public void createView(final UserDetail detail) {
        setTitle(detail.getName());

        List<UserDetail.TopAnswer> objects = detail.getTopanswers();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTopAnswerListView.setLayoutManager(layoutManager);
        Adapter adapter = new Adapter(this, objects);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UserDetail.TopAnswer answer = detail.getTopanswers().get(position);
                String url = "http://www.zhihu.com" + answer.getLink();
                LLog.e("get url = ", url);
                int openType = Integer.parseInt(getSharedPreferences("com.ouwenjie.kzhihu_preferences", MODE_PRIVATE)
                        .getString(SettingsActivity.PREF_KEY_OPEN_TYPE, "1"));
                LLog.e("get open type = ", openType);
                if (openType == 1) {
                    Intent intent = new Intent(getContext(), WebBrowserActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                } else if (openType == 0) {
                    boolean hasZhiHuClient = isAvilible(UserProfileActivity.this, ZhiHu.PACKAGE_NAME);
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
        });
        mTopAnswerListView.setAdapter(adapter);

        initHeaderView(detail);

    }

    private void initHeaderView(UserDetail detail) {

        View headerView = LayoutInflater.from(this)
                .inflate(R.layout.layout_user_profile_header, mTopAnswerListView, false);
        mUserAvatarImg = ButterKnife.findById(headerView, R.id.user_avatar_img);
        mUserDescriptionTxt = ButterKnife.findById(headerView, R.id.user_description_txt);
        mUserSignatureTxt = ButterKnife.findById(headerView, R.id.user_signature_txt);
        mAgreeCountTxt = ButterKnife.findById(headerView, R.id.agree_count_txt);
        mFollowerCountTxt = ButterKnife.findById(headerView, R.id.follower_count_txt);
        mFavCountTxt = ButterKnife.findById(headerView, R.id.fav_count_txt);
        mAskCountTxt = ButterKnife.findById(headerView, R.id.ask_count_txt);
        mAnswerCountTxt = ButterKnife.findById(headerView, R.id.answer_count_txt);
        mPostCountTxt = ButterKnife.findById(headerView, R.id.post_count_txt);


        List<String> xData = new ArrayList<>();
        List<Entry> answerDatas = new ArrayList<>();
        List<Entry> agreeDatas = new ArrayList<>();
        List<Entry> followerDatas = new ArrayList<>();

        for (int i = 0; i < detail.getTrend().size(); i++) {
            UserDetail.Trend trend = detail.getTrend().get(i);

            xData.add(trend.getDate());
            answerDatas.add(new Entry(Float.parseFloat(trend.getAnswer()), i));
            agreeDatas.add(new Entry(Float.parseFloat(trend.getAgree()), i));
            followerDatas.add(new Entry(Float.parseFloat(trend.getFollower()), i));
        }
        LineDataSet answerDataSet = new LineDataSet(answerDatas, "回答数");
        LineDataSet agreeDataSet = new LineDataSet(agreeDatas, "赞同数");
        LineDataSet followerDataSet = new LineDataSet(followerDatas, "粉丝数");

        LineChart trendsChart = ButterKnife.findById(headerView, R.id.trends_chart);
        LineChart agreeChart = ButterKnife.findById(headerView, R.id.agree_chart);
        LineChart followerChart = ButterKnife.findById(headerView, R.id.follower_chart);

        XAxis xAxis = trendsChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        LineData data = new LineData(xData, answerDataSet);
        trendsChart.setData(data);
        trendsChart.invalidate();

        XAxis agreeX = agreeChart.getXAxis();
        agreeX.setPosition(XAxis.XAxisPosition.BOTTOM);
        LineData agreeData = new LineData(xData, agreeDataSet);
        agreeChart.setData(agreeData);
        agreeChart.invalidate();

        XAxis followerX = followerChart.getXAxis();
        followerX.setPosition(XAxis.XAxisPosition.BOTTOM);
        LineData followerData = new LineData(xData, followerDataSet);
        followerChart.setData(followerData);
        followerChart.invalidate();


        Glide.with(this)
                .load(detail.getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mUserAvatarImg);

        mUserDescriptionTxt.setText(detail.getDescription());
        mUserSignatureTxt.setText(detail.getSignature());

        mAgreeCountTxt.setText(detail.getDetail().getAgree());
        mFollowerCountTxt.setText(detail.getDetail().getFollower());
        mFavCountTxt.setText(detail.getDetail().getFav());
        mAskCountTxt.setText(detail.getDetail().getAsk());
        mAnswerCountTxt.setText(detail.getDetail().getAnswer());
        mPostCountTxt.setText(detail.getDetail().getPost());

        mTopAnswerListView.addHeaderView(headerView);

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


    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private Context context;
        private List<UserDetail.TopAnswer> objectList;
        private OnItemClickListener onItemClickListener;

        public Adapter(Context context, List<UserDetail.TopAnswer> objectList) {
            this.context = context;
            this.objectList = objectList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_my_top_answer_list_item, parent, false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, (Integer) v.getTag());
                    }
                }
            });
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.itemView.setTag(position);
            UserDetail.TopAnswer answer = objectList.get(position);
            holder.questionTitleTxt.setText(answer.getTitle());
            holder.likeCountTxt.setText(answer.getAgree());
            holder.createTimeTxt.setText(answer.getDate());
        }

        @Override
        public int getItemCount() {
            return objectList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.question_title_txt)
            TextView questionTitleTxt;
            @Bind(R.id.like_count_txt)
            TextView likeCountTxt;
            @Bind(R.id.create_time_txt)
            TextView createTimeTxt;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
