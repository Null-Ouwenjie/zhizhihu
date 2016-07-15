package com.ouwenjie.zhizhihu.presenter;

import com.ouwenjie.zhizhihu.model.entity.Post;
import com.ouwenjie.zhizhihu.model.mImp.ApiImp;
import com.ouwenjie.zhizhihu.model.mInterface.ApiInterface;
import com.ouwenjie.zhizhihu.ui.viewInterface.PostListViewInterface;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by 文杰 on 2015/10/27.
 */
public class PostListPresenter extends MVPPresenter<PostListViewInterface> {

    private ApiInterface mApiInterface;

    private List<Post> mPosts = new ArrayList<>();
    private String mLastPublishTime;

    private boolean mIsLoadingNextPage = false;

    public PostListPresenter(PostListViewInterface postListViewInterface) {
        super(postListViewInterface);
        mApiInterface = new ApiImp();
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {
        mCompositeSubscription.clear();
    }

    /**
     * 初始化,加载第一页
     */
    public void initData() {
        mViewInterface.setSwipeRefreshing(true);
        Subscription sub = mApiInterface.getPosts()
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        doRxError(e);
                    }

                    @Override
                    public void onNext(List<Post> list) {
                        mPosts = list;
                        mViewInterface.initList(mPosts);
                        mLastPublishTime = mPosts.get(mPosts.size() - 1).getPublishtime();
                        mViewInterface.setSwipeRefreshing(false);
                    }
                });
        mCompositeSubscription.add(sub);
    }

    /**
     * 加载下一页
     */
    public void loadNextPage() {
        mViewInterface.setSwipeRefreshing(true);
        mIsLoadingNextPage = true;
        Subscription sub = mApiInterface.getPosts(mLastPublishTime)
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        doRxError(e);
                    }

                    @Override
                    public void onNext(List<Post> list) {
                        mPosts.addAll(list);
                        mViewInterface.refreshList();
                        mLastPublishTime = mPosts.get(mPosts.size() - 1).getPublishtime();
                        mViewInterface.setSwipeRefreshing(false);
                        mIsLoadingNextPage = false;
                    }
                });
        mCompositeSubscription.add(sub);
    }

    public boolean hasData() {
        return !mPosts.isEmpty();
    }

    public boolean isLoadingNextPage() {
        return mIsLoadingNextPage;
    }
}
