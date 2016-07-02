package com.ouwenjie.zhizhihu.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ouwenjie.zhizhihu.common.Constant;
import com.ouwenjie.zhizhihu.model.entity.SearchUser;
import com.ouwenjie.zhizhihu.model.entity.UserDetail;
import com.ouwenjie.zhizhihu.model.mImp.ApiImp;
import com.ouwenjie.zhizhihu.model.mInterface.ApiInterface;
import com.ouwenjie.zhizhihu.ui.viewInterface.UserCenterViewInterface;
import com.ouwenjie.zhizhihu.utils.PreferencesUtil;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ouwenjie on 2016/7/2.
 */
public class UserCenterPresenter extends MVPPresenter<UserCenterViewInterface> {

    private ApiInterface mApiInterface;
    private boolean mHasBindUser = false;
    private UserDetail mUserDetailInfo;

    public UserCenterPresenter(UserCenterViewInterface viewInterface) {
        super(viewInterface);
        mApiInterface = new ApiImp();
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {
        mCompositeSubscription.clear();
    }

    public void loadData() {
        String userString = PreferencesUtil.getString(mContext, Constant.KEY_BIND_USER);
        if (!TextUtils.isEmpty(userString)) {
            mHasBindUser = true;
            final SearchUser user = new Gson().fromJson(userString, SearchUser.class);
            Subscription sub = mApiInterface.getUserDetail(user.getHash())
                    .subscribe(new Action1<UserDetail>() {
                        @Override
                        public void call(UserDetail userDetail) {
                            mUserDetailInfo = userDetail;
                            mViewInterface.initData(mUserDetailInfo);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable e) {
                            doRxError(e);
                        }
                    });
            mCompositeSubscription.add(sub);
        } else {
            mHasBindUser = false;
            mViewInterface.showDefaultInfo();

        }
    }

    public boolean hasBindUser() {
        return mHasBindUser;
    }

}
