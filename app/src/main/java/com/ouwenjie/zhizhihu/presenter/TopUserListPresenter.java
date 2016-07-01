package com.ouwenjie.zhizhihu.presenter;

import com.ouwenjie.zhizhihu.model.entity.TopUser;
import com.ouwenjie.zhizhihu.model.mImp.ApiImp;
import com.ouwenjie.zhizhihu.model.mInterface.ApiInterface;
import com.ouwenjie.zhizhihu.ui.viewInterface.TopUserListViewInterface;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Jack on 2016/3/23.
 */
public class TopUserListPresenter extends MVPPresenter<TopUserListViewInterface> {

    private int mPage = 1;
    private static final int sPageCount = 20;

    private ApiInterface mApiInterface;
    private String mTopType;
    private List<TopUser> mTopUsers;

    public TopUserListPresenter(TopUserListViewInterface viewInterface) {
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


    public void loadTopUserData(String topType) {
        mTopType = topType;
        Subscription sub = mApiInterface.getTopUser(topType, mPage, sPageCount)
                .subscribe(new Subscriber<List<TopUser>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        doRxError(e);
                    }

                    @Override
                    public void onNext(List<TopUser> topUsers) {
                        mTopUsers = topUsers;
                        mViewInterface.createUserListView(mTopUsers);
                    }
                });
        mCompositeSubscription.add(sub);
    }

}
