package com.lanjiaai.kzhihu.ui.fragment;

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
import android.text.TextUtils;
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
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lanjiaai.kzhihu.R;
import com.lanjiaai.kzhihu.common.Constant;
import com.lanjiaai.kzhihu.common.LLog;
import com.lanjiaai.kzhihu.common.ThemeManager;
import com.lanjiaai.kzhihu.model.PreferencesUtil;
import com.lanjiaai.kzhihu.model.api.ZhiHu;
import com.lanjiaai.kzhihu.model.entity.SearchUser;
import com.lanjiaai.kzhihu.model.entity.UserDetail;
import com.lanjiaai.kzhihu.model.mImp.ApiImp;
import com.lanjiaai.kzhihu.ui.activity.AboutUsActivity;
import com.lanjiaai.kzhihu.ui.activity.FeedbackActivity;
import com.lanjiaai.kzhihu.ui.activity.HomeActivity;
import com.lanjiaai.kzhihu.ui.activity.MyFavoriteActivity;
import com.lanjiaai.kzhihu.ui.activity.RandomLookActivity;
import com.lanjiaai.kzhihu.ui.activity.SettingsActivity;
import com.lanjiaai.kzhihu.ui.activity.UserSearchActivity;
import com.lanjiaai.kzhihu.ui.activity.WebBrowserActivity;
import com.lanjiaai.kzhihu.ui.viewInterface.UserCenterViewInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

/**
 * 精选  -->  推荐
 * Created by 文杰 on 2015/10/19.
 */
public class UserCenterFragment extends BaseFragment implements UserCenterViewInterface, View.OnClickListener {

    HomeActivity activity;

    @Bind(R.id.my_trends_list_view)
    XRecyclerView myTrendsListView;

    CircleImageView userAvatarImg;
    TextView userDescriptionTxt;
    TextView userSignatureTxt;
    TextView agreeCountTxt;
    TextView followerCountTxt;
    TextView favCountTxt;
    TextView askCountTxt;
    TextView answerCountTxt;
    TextView postCountTxt;


    TextView randomLookTxt;
    TextView trendsTxt;
    TextView myFavoriteTxt;
    TextView changedThemeTxt;

    TextView feedbackTxt;
    TextView settingsTxt;
    TextView aboutUsTxt;
    TextView goodGradeTxt;


    private LineChart trendsChart;
    private LineChart agreeChart;
    private LineChart followerChart;

    private LinearLayoutManager layoutManager;
    private Adapter adapter;

    private boolean hasBindUser;
    private UserDetail userDetailInfo;
    private List<UserDetail.TopAnswer> answers;

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
        this.activity = (HomeActivity) activity;
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

    }

    @Override
    public void onResume() {
        super.onResume();

        String userString = PreferencesUtil.getString(getContext().getApplicationContext(), Constant.KEY_BIND_USER);
        if (!TextUtils.isEmpty(userString)) {
            hasBindUser = true;
            final SearchUser user = new Gson().fromJson(userString, SearchUser.class);
            new ApiImp().getUserDetail(user.getHash())
                    .subscribe(new Subscriber<UserDetail>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(UserDetail userDetail) {
                            userDetailInfo = userDetail;
                            initData();
                        }
                    });
        } else {
            hasBindUser = false;
            Glide.with(this)
                    .load(R.drawable.ic_default_avatar)
                    .into(userAvatarImg);
            userDescriptionTxt.setText("请点击绑定自己的知乎账号");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (answers == null) {
            answers = new ArrayList<>();
        }
        adapter = new Adapter(getContext(), answers);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UserDetail.TopAnswer answer = userDetailInfo.getTopanswers().get(position);
                String url = "http://www.zhihu.com" + answer.getLink();
                LLog.e("get url = ", url);
                int openType = Integer.parseInt(getContext().getSharedPreferences("com.ouwenjie.kzhihu_preferences", Activity.MODE_PRIVATE)
                        .getString(SettingsActivity.PREF_KEY_OPEN_TYPE, "1"));
                LLog.e("get open type = ", openType);
                if (openType == 1) {
                    Intent intent = new Intent(getContext(), WebBrowserActivity.class);
                    intent.putExtra("url", url);
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
        myTrendsListView.setLayoutManager(layoutManager);
        myTrendsListView.setAdapter(adapter);

        View headerView = LayoutInflater.from(getContext())
                .inflate(R.layout.layout_my_profile_header, myTrendsListView, false);

        userAvatarImg = ButterKnife.findById(headerView, R.id.user_avatar_img);
        userDescriptionTxt = ButterKnife.findById(headerView, R.id.user_description_txt);
        userSignatureTxt = ButterKnife.findById(headerView, R.id.user_signature_txt);
        agreeCountTxt = ButterKnife.findById(headerView, R.id.agree_count_txt);
        followerCountTxt = ButterKnife.findById(headerView, R.id.follower_count_txt);
        favCountTxt = ButterKnife.findById(headerView, R.id.fav_count_txt);
        askCountTxt = ButterKnife.findById(headerView, R.id.ask_count_txt);
        answerCountTxt = ButterKnife.findById(headerView, R.id.answer_count_txt);
        postCountTxt = ButterKnife.findById(headerView, R.id.post_count_txt);

        userAvatarImg.setOnClickListener(this);
        userDescriptionTxt.setOnClickListener(this);
        userSignatureTxt.setOnClickListener(this);
        agreeCountTxt.setOnClickListener(this);
        followerCountTxt.setOnClickListener(this);
        favCountTxt.setOnClickListener(this);
        askCountTxt.setOnClickListener(this);
        answerCountTxt.setOnClickListener(this);
        postCountTxt.setOnClickListener(this);

        randomLookTxt = ButterKnife.findById(headerView, R.id.random_look_txt);
        trendsTxt = ButterKnife.findById(headerView, R.id.trends_txt);
        myFavoriteTxt = ButterKnife.findById(headerView, R.id.my_favorite_txt);
        changedThemeTxt = ButterKnife.findById(headerView, R.id.changed_theme_txt);
        feedbackTxt = ButterKnife.findById(headerView, R.id.my_feedback_txt);
        settingsTxt = ButterKnife.findById(headerView, R.id.settings_txt);
        aboutUsTxt = ButterKnife.findById(headerView, R.id.about_us_txt);
        goodGradeTxt = ButterKnife.findById(headerView, R.id.good_grade_txt);

        randomLookTxt.setOnClickListener(this);
        trendsTxt.setOnClickListener(this);
        myFavoriteTxt.setOnClickListener(this);
        changedThemeTxt.setOnClickListener(this);
        feedbackTxt.setOnClickListener(this);
        settingsTxt.setOnClickListener(this);
        aboutUsTxt.setOnClickListener(this);
        goodGradeTxt.setOnClickListener(this);

        trendsChart = ButterKnife.findById(headerView, R.id.trends_chart);
        agreeChart = ButterKnife.findById(headerView, R.id.agree_chart);
        followerChart = ButterKnife.findById(headerView, R.id.follower_chart);

        myTrendsListView.addHeaderView(headerView);
    }

    private void initData() {
        answers.addAll(userDetailInfo.getTopanswers());
        adapter.notifyDataSetChanged();

        Glide.with(this)
                .load(userDetailInfo.getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userAvatarImg);

        userDescriptionTxt.setText(userDetailInfo.getDescription());
        userSignatureTxt.setText(userDetailInfo.getSignature());

        agreeCountTxt.setText(userDetailInfo.getDetail().getAgree());
        followerCountTxt.setText(userDetailInfo.getDetail().getFollower());
        favCountTxt.setText(userDetailInfo.getDetail().getFav());
        askCountTxt.setText(userDetailInfo.getDetail().getAsk());
        answerCountTxt.setText(userDetailInfo.getDetail().getAnswer());
        postCountTxt.setText(userDetailInfo.getDetail().getPost());

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
                if (hasBindUser) {

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
                activity.reload();
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
