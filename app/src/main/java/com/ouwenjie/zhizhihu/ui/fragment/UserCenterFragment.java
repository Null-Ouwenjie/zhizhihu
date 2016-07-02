package com.ouwenjie.zhizhihu.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ouwenjie.zhizhihu.common.ThemeManager;
import com.ouwenjie.zhizhihu.model.api.ZhiHu;
import com.ouwenjie.zhizhihu.model.entity.UserDetail;
import com.ouwenjie.zhizhihu.presenter.UserCenterPresenter;
import com.ouwenjie.zhizhihu.ui.activity.AboutUsActivity;
import com.ouwenjie.zhizhihu.ui.activity.FeedbackActivity;
import com.ouwenjie.zhizhihu.ui.activity.HomeActivity;
import com.ouwenjie.zhizhihu.ui.activity.MyFavoriteActivity;
import com.ouwenjie.zhizhihu.ui.activity.RandomLookActivity;
import com.ouwenjie.zhizhihu.ui.activity.SettingsActivity;
import com.ouwenjie.zhizhihu.ui.activity.UserSearchActivity;
import com.ouwenjie.zhizhihu.ui.activity.WebBrowserActivity;
import com.ouwenjie.zhizhihu.ui.viewInterface.UserCenterViewInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 精选  -->  推荐
 * Created by 文杰 on 2015/10/19.
 */
public class UserCenterFragment extends BaseFragment
        implements UserCenterViewInterface, View.OnClickListener {

    private HomeActivity mHomeActivity;

    @Bind(R.id.my_trends_list_view)
    XRecyclerView mMyTrendsListView;

    CircleImageView mUserAvatarImg;
    TextView mUserDescriptionTxt;
    TextView mUserSignatureTxt;
    TextView mAgreeCountTxt;
    TextView mFollowerCountTxt;
    TextView mFavCountTxt;
    TextView mAskCountTxt;
    TextView mAnswerCountTxt;
    TextView mPostCountTxt;

    TextView mRandomLookTxt;
    TextView mTrendsTxt;
    TextView mMyFavoriteTxt;
    TextView mChangedThemeTxt;

    TextView mFeedbackTxt;
    TextView mSettingsTxt;
    TextView mAboutUsTxt;
    TextView mGoodGradeTxt;

    private LineChart mTrendsChart;
    private LineChart mAgreeChart;
    private LineChart mFollowerChart;

    private Adapter mAdapter;

    private List<UserDetail.TopAnswer> mTopAnswers;

    private UserCenterPresenter mPresenter;

    public UserCenterFragment() {
    }

    public static UserCenterFragment newInstance(@Nullable Bundle args) {
        UserCenterFragment fragment = new UserCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mHomeActivity = (HomeActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_center, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        mPresenter = new UserCenterPresenter(this);
        mPresenter.create();

    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (mTopAnswers == null) {
            mTopAnswers = new ArrayList<>();
        }
        mAdapter = new Adapter(getContext(), mTopAnswers);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UserDetail.TopAnswer answer = mTopAnswers.get(position);
                String url = "http://www.zhihu.com" + answer.getLink();
                LLog.e("get url = ", url);
                int openType = Integer.parseInt(getContext().getSharedPreferences("com.ouwenjie.kzhihu_preferences", Activity.MODE_PRIVATE)
                        .getString(SettingsActivity.PREF_KEY_OPEN_TYPE, "1"));
                LLog.e("get open type = ", openType);
                if (openType == 1) {
                    Intent intent = new Intent(getContext(), WebBrowserActivity.class);
                    intent.putExtra(WebBrowserActivity.URL, url);
                    startActivity(intent);
                } else if (openType == 0) {
                    boolean hasZhiHuClient = isAvilible(getContext(), ZhiHu.PACKAGE_NAME);
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
        mMyTrendsListView.setLayoutManager(layoutManager);
        mMyTrendsListView.setAdapter(mAdapter);

        View headerView = LayoutInflater.from(getContext())
                .inflate(R.layout.layout_my_profile_header, mMyTrendsListView, false);

        mUserAvatarImg = ButterKnife.findById(headerView, R.id.user_avatar_img);
        mUserDescriptionTxt = ButterKnife.findById(headerView, R.id.user_description_txt);
        mUserSignatureTxt = ButterKnife.findById(headerView, R.id.user_signature_txt);
        mAgreeCountTxt = ButterKnife.findById(headerView, R.id.agree_count_txt);
        mFollowerCountTxt = ButterKnife.findById(headerView, R.id.follower_count_txt);
        mFavCountTxt = ButterKnife.findById(headerView, R.id.fav_count_txt);
        mAskCountTxt = ButterKnife.findById(headerView, R.id.ask_count_txt);
        mAnswerCountTxt = ButterKnife.findById(headerView, R.id.answer_count_txt);
        mPostCountTxt = ButterKnife.findById(headerView, R.id.post_count_txt);

        mUserAvatarImg.setOnClickListener(this);
        mUserDescriptionTxt.setOnClickListener(this);
        mUserSignatureTxt.setOnClickListener(this);
        mAgreeCountTxt.setOnClickListener(this);
        mFollowerCountTxt.setOnClickListener(this);
        mFavCountTxt.setOnClickListener(this);
        mAskCountTxt.setOnClickListener(this);
        mAnswerCountTxt.setOnClickListener(this);
        mPostCountTxt.setOnClickListener(this);

        mRandomLookTxt = ButterKnife.findById(headerView, R.id.random_look_txt);
        mTrendsTxt = ButterKnife.findById(headerView, R.id.trends_txt);
        mMyFavoriteTxt = ButterKnife.findById(headerView, R.id.my_favorite_txt);
        mChangedThemeTxt = ButterKnife.findById(headerView, R.id.changed_theme_txt);
        mFeedbackTxt = ButterKnife.findById(headerView, R.id.my_feedback_txt);
        mSettingsTxt = ButterKnife.findById(headerView, R.id.settings_txt);
        mAboutUsTxt = ButterKnife.findById(headerView, R.id.about_us_txt);
        mGoodGradeTxt = ButterKnife.findById(headerView, R.id.good_grade_txt);

        mRandomLookTxt.setOnClickListener(this);
        mTrendsTxt.setOnClickListener(this);
        mMyFavoriteTxt.setOnClickListener(this);
        mChangedThemeTxt.setOnClickListener(this);
        mFeedbackTxt.setOnClickListener(this);
        mSettingsTxt.setOnClickListener(this);
        mAboutUsTxt.setOnClickListener(this);
        mGoodGradeTxt.setOnClickListener(this);

        mTrendsChart = ButterKnife.findById(headerView, R.id.trends_chart);
        mAgreeChart = ButterKnife.findById(headerView, R.id.agree_chart);
        mFollowerChart = ButterKnife.findById(headerView, R.id.follower_chart);

        mMyTrendsListView.addHeaderView(headerView);
    }

    @Override
    public void initData(UserDetail userDetailInfo) {
        mTopAnswers.addAll(userDetailInfo.getTopanswers());
        mAdapter.notifyDataSetChanged();

        Glide.with(this)
                .load(userDetailInfo.getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mUserAvatarImg);

        mUserDescriptionTxt.setText(userDetailInfo.getDescription());
        mUserSignatureTxt.setText(userDetailInfo.getSignature());

        mAgreeCountTxt.setText(userDetailInfo.getDetail().getAgree());
        mFollowerCountTxt.setText(userDetailInfo.getDetail().getFollower());
        mFavCountTxt.setText(userDetailInfo.getDetail().getFav());
        mAskCountTxt.setText(userDetailInfo.getDetail().getAsk());
        mAnswerCountTxt.setText(userDetailInfo.getDetail().getAnswer());
        mPostCountTxt.setText(userDetailInfo.getDetail().getPost());

        List<String> xData = new ArrayList<>();
        List<Entry> answerDatas = new ArrayList<>();
        List<Entry> agreeDatas = new ArrayList<>();
        List<Entry> followerDatas = new ArrayList<>();

        for (int i = 0; i < userDetailInfo.getTrend().size(); i++) {
            UserDetail.Trend trend = userDetailInfo.getTrend().get(i);
            xData.add(trend.getDate());
            answerDatas.add(new Entry(Float.parseFloat(trend.getAnswer()), i));
            agreeDatas.add(new Entry(Float.parseFloat(trend.getAgree()), i));
            followerDatas.add(new Entry(Float.parseFloat(trend.getFollower()), i));
        }
        LineDataSet answerDataSet = new LineDataSet(answerDatas, "回答数");
        LineDataSet agreeDataSet = new LineDataSet(agreeDatas, "赞同数");
        LineDataSet followerDataSet = new LineDataSet(followerDatas, "粉丝数");

        XAxis xAxis = mTrendsChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        LineData data = new LineData(xData, answerDataSet);
        mTrendsChart.setData(data);
        mTrendsChart.invalidate();

        XAxis agreeX = mAgreeChart.getXAxis();
        agreeX.setPosition(XAxis.XAxisPosition.BOTTOM);
        LineData agreeData = new LineData(xData, agreeDataSet);
        mAgreeChart.setData(agreeData);
        mAgreeChart.invalidate();

        XAxis followerX = mFollowerChart.getXAxis();
        followerX.setPosition(XAxis.XAxisPosition.BOTTOM);
        LineData followerData = new LineData(xData, followerDataSet);
        mFollowerChart.setData(followerData);
        mFollowerChart.invalidate();


    }

    @Override
    public void showDefaultInfo() {
        Glide.with(getContext())
                .load(R.drawable.ic_default_avatar)
                .into(mUserAvatarImg);
        mUserDescriptionTxt.setText("请点击绑定自己的知乎账号");
    }

    public boolean isIntentAvailable(Intent intent) {
        List<ResolveInfo> list = getContext().getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
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


    public void goMarket() {
        String strUri = "market://details?id=" + getContext().getPackageName();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUri));
        if (isIntentAvailable(intent)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_avatar_img:
            case R.id.user_description_txt:
                if (mPresenter.hasBindUser()) {

                } else {
                    startActivity(new Intent(getContext(), UserSearchActivity.class));
                }

                break;

            case R.id.agree_count_txt:
                break;
            case R.id.follower_count_txt:
                break;
            case R.id.fav_count_txt:
                break;
            case R.id.ask_count_txt:
                break;
            case R.id.answer_count_txt:
                break;
            case R.id.post_count_txt:
                break;

            case R.id.random_look_txt:
                startActivity(new Intent(getContext(), RandomLookActivity.class));
                break;
            case R.id.trends_txt:
                break;
            case R.id.my_favorite_txt:
                startActivity(new Intent(getContext(), MyFavoriteActivity.class));
                break;
            case R.id.changed_theme_txt:
                ThemeManager.changeTheme(getContext());
                mHomeActivity.reload();
                break;

            case R.id.my_feedback_txt:
                startActivity(new Intent(getContext(), FeedbackActivity.class));
                break;
            case R.id.settings_txt:
                startActivity(new Intent(getContext(), SettingsActivity.class));
                break;
            case R.id.about_us_txt:
                startActivity(new Intent(getContext(), AboutUsActivity.class));
                break;
            case R.id.good_grade_txt:
                goMarket();
                break;

        }
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
