package com.ouwenjie.zhizhihu.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.model.entity.Post;
import com.ouwenjie.zhizhihu.presenter.PostListPresenter;
import com.ouwenjie.zhizhihu.ui.activity.PostAnswersActivity;
import com.ouwenjie.zhizhihu.ui.adapter.PostListAdapter;
import com.ouwenjie.zhizhihu.ui.viewInterface.PostListViewInterface;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 精选  -->  推荐
 * Created by 文杰 on 2015/10/19.
 */
public class PostListFragment extends SwipeRefreshFragment implements PostListViewInterface {

    Activity activity;

    @Bind(R.id.posts_list)
    RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    private PostListAdapter postListAdapter;

    private boolean mIsFirstTimeTouchBottom = true;

    PostListPresenter postListPresenter;

    public PostListFragment() {
    }

    public static PostListFragment newInstance(@Nullable Bundle args) {
        PostListFragment fragment = new PostListFragment();
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
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postListPresenter = new PostListPresenter(this);
        initView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                postListPresenter.initData();
            }
        }, 399);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    private void initView() {
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(getScrollToBottomListener(linearLayoutManager));
    }

    @Override
    public void initList(final List<Post> postList) {
        postListAdapter = new PostListAdapter(this, postList);
        postListAdapter.setOnItemClickListener(new PostListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PostAnswersActivity.startActivity(activity, postList.get(position));
            }
        });
        recyclerView.setAdapter(postListAdapter);
    }

    /**
     * 重新刷新界面
     */
    @Override
    public void refreshList() {
        if (postListAdapter != null) {
            postListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 控制下拉刷新的控件
     *
     * @param refreshing
     */
    @Override
    public void setSwipeRefreshing(boolean refreshing) {
        setRefreshing(refreshing);
    }

    @Override
    public void onRefreshing() {
        // 界面有数据, 而且此刻没有正在加载下一页, 这个下拉就自己关掉吧
        if (postListPresenter.hasData() && !postListPresenter.isLoadingNextPage()) {
            super.onRefreshing();
        }
    }

    private RecyclerView.OnScrollListener getScrollToBottomListener(final LinearLayoutManager linearLayoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                int count = postListAdapter.getItemCount() - 1;
                boolean isBottom = (lastItem == count);
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
                        Log.e("onScrolled=>", "refresh.... ");
                        postListPresenter.loadNextPage();
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

}
