package com.ouwenjie.zhizhihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.common.LLog;
import com.ouwenjie.zhizhihu.model.api.ZhiHu;
import com.ouwenjie.zhizhihu.model.entity.Answer;
import com.ouwenjie.zhizhihu.model.entity.Post;
import com.ouwenjie.zhizhihu.model.mImp.ApiImp;
import com.ouwenjie.zhizhihu.model.mInterface.ApiInterface;
import com.ouwenjie.zhizhihu.ui.activity.base.BaseActivity;
import com.ouwenjie.zhizhihu.utils.TimeUtil;

import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class RandomLookActivity extends BaseActivity {

    private static final long daySec = 60 * 60 * 24;

    private long mTargetTimestamp;
    private boolean mHasLoadData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_look);

        int randomDay = new Random().nextInt(30);
        long daySecs = daySec * randomDay;

        long curSec = TimeUtil.getCurrentUnixTimeStamp();
        mTargetTimestamp = curSec - daySecs;
        LLog.e("RandomLook ", "mTargetTimestamp=" + mTargetTimestamp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mHasLoadData) {
            loadRandom();
        } else {
            finish();
        }
    }

    private void loadRandom() {
        final ApiInterface apiInterface = new ApiImp();
        // 使用一个随机的时间戳去请求 posts
        apiInterface.getPosts(mTargetTimestamp + "")
                .flatMap(new Func1<List<Post>, Observable<List<Answer>>>() {
                    @Override
                    public Observable<List<Answer>> call(List<Post> posts) {
                        Post post = posts.get(0);

                        // 将得到的第一个 post ，去获取答案列表
                        return apiInterface.getAnswers(post.getDate().replace("-", ""), post.getName());
                    }
                })
                .subscribe(new Subscriber<List<Answer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Answer> answers) {
                        int randomIndex = new Random().nextInt(answers.size() - 1);
                        LLog.e("RandomLook ", "randomIndex=" + randomIndex);

                        Answer answer = answers.get(randomIndex);
                        String qId = answer.getQuestionid();
                        String aId = answer.getAnswerid();
                        String url = String.format(ZhiHu.QUESTION_BASE_URL + "%s/answer/%s", qId, aId);

                        int openType = Integer.parseInt(getSharedPreferences("com.ouwenjie.kzhihu_preferences", MODE_PRIVATE)
                                .getString(SettingsActivity.PREF_KEY_OPEN_TYPE, "1"));
                        LLog.e("get open type = ", openType);
                        if (openType == 1) {
                            Intent intent = new Intent(getApplicationContext(), WebBrowserActivity.class);
                            intent.putExtra("url", url);
                            startActivity(intent);
                        } else if (openType == 0) {
                            boolean hasZhiHuClient = isValid(RandomLookActivity.this, ZhiHu.PACKAGE_NAME);
                            if (hasZhiHuClient) {
                                LLog.e("onItemClick=>", "hasZhiHuClient");
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                intent.setPackage(ZhiHu.PACKAGE_NAME);
                                startActivity(intent);
                            } else {
                                toast("请您安装知乎客户端");
                                finish();
                            }
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                        mHasLoadData = true;
                    }
                });

    }

    private boolean isValid(Context context, String packageName) {
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
