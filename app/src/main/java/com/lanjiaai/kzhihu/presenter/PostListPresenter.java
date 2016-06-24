package com.lanjiaai.kzhihu.presenter;

import com.lanjiaai.kzhihu.model.entity.Post;
import com.lanjiaai.kzhihu.model.mImp.ApiImp;
import com.lanjiaai.kzhihu.model.mInterface.ApiInterface;
import com.lanjiaai.kzhihu.ui.viewInterface.PostListViewInterface;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by 文杰 on 2015/10/27.
 */
public class PostListPresenter extends MVPPresenter<PostListViewInterface> {

    private ApiInterface apiInterface;

    private List<Post> posts = new ArrayList<>();
    private String lastPublishTime;

    private boolean isLoadingNextPage = false;

    public PostListPresenter(PostListViewInterface postListViewInterface) {
        super(postListViewInterface);
        apiInterface = new ApiImp();
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {

    }

    /**
     * 初始化,加载第一页
     */
    public void initData() {
        viewInterface.setSwipeRefreshing(true);
        apiInterface.getPosts()
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Post> list) {
                        posts = list;
                        viewInterface.initList(posts);
                        lastPublishTime = posts.get(posts.size() - 1).getPublishtime();
                        viewInterface.setSwipeRefreshing(false);
                    }
                });
    }

    /**
     * 加载下一页
     */
    public void loadNextPage() {
        viewInterface.setSwipeRefreshing(true);
        isLoadingNextPage = true;
        apiInterface.getPosts(lastPublishTime)
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Post> list) {
                        posts.addAll(list);
                        viewInterface.refreshList();
                        lastPublishTime = posts.get(posts.size() - 1).getPublishtime();
                        viewInterface.setSwipeRefreshing(false);
                        isLoadingNextPage = false;
                    }
                });
    }

    public boolean hasData() {
        return !posts.isEmpty();
    }

    public boolean isLoadingNextPage() {
        return isLoadingNextPage;
    }
}
