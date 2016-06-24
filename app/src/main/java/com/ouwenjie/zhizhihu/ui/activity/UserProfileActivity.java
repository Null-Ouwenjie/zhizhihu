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
    Toolbar toolbar;
    @Bind(R.id.top_answer_list_view)
    XRecyclerView topAnswerListView;
    @Bind(R.id.progress)
    ProgressBar progress;

    CircleImageView userAvatarImg;
    TextView userDescriptionTxt;
    TextView userSignatureTxt;
    TextView agreeCountTxt;
    TextView followerCountTxt;
    TextView favCountTxt;
    TextView askCountTxt;
    TextView answerCountTxt;
    TextView postCountTxt;

    private LineChart trendsChart;
    private LineChart agreeChart;
    private LineChart followerChart;

    private String userHash;
    private LinearLayoutManager layoutManager;
    private Adapter adapter;


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
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserProfileActivity.this.onBackPressed();
                    }
                }
        );
        userHash = getIntent().getStringExtra(KEY_USER_HASH);
        new ApiImp().getUserDetail(userHash)
                .subscribe(new Subscriber<UserDetail>() {
                    @Override
                    public void onCompleted() {
                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progress.setVisibility(View.GONE);
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
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topAnswerListView.setLayoutManager(layoutManager);
        adapter = new Adapter(this, objects);
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
        topAnswerListView.setAdapter(adapter);

        initHeaderView(detail);

    }

    private void initHeaderView(UserDetail detail) {

        View headerView = LayoutInflater.from(this)
                .inflate(R.layout.layout_user_profile_header, topAnswerListView, false);
        userAvatarImg = ButterKnife.findById(headerView, R.id.user_avatar_img);
        userDescriptionTxt = ButterKnife.findById(headerView, R.id.user_description_txt);
        userSignatureTxt = ButterKnife.findById(headerView, R.id.user_signature_txt);
        agreeCountTxt = ButterKnife.findById(headerView, R.id.agree_count_txt);
        followerCountTxt = ButterKnife.findById(headerView, R.id.follower_count_txt);
        favCountTxt = ButterKnife.findById(headerView, R.id.fav_count_txt);
        askCountTxt = ButterKnife.findById(headerView, R.id.ask_count_txt);
        answerCountTxt = ButterKnife.findById(headerView, R.id.answer_count_txt);
        postCountTxt = ButterKnife.findById(headerView, R.id.post_count_txt);


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

        trendsChart = ButterKnife.findById(headerView, R.id.trends_chart);
        agreeChart = ButterKnife.findById(headerView, R.id.agree_chart);
        followerChart = ButterKnife.findById(headerView, R.id.follower_chart);

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
                .into(userAvatarImg);

        userDescriptionTxt.setText(detail.getDescription());
        userSignatureTxt.setText(detail.getSignature());

        agreeCountTxt.setText(detail.getDetail().getAgree());
        followerCountTxt.setText(detail.getDetail().getFollower());
        favCountTxt.setText(detail.getDetail().getFav());
        askCountTxt.setText(detail.getDetail().getAsk());
        answerCountTxt.setText(detail.getDetail().getAnswer());
        postCountTxt.setText(detail.getDetail().getPost());

        topAnswerListView.addHeaderView(headerView);

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
