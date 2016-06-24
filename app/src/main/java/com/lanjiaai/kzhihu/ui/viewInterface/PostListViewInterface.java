package com.lanjiaai.kzhihu.ui.viewInterface;

import com.lanjiaai.kzhihu.model.entity.Post;

import java.util.List;

/**
 * Created by 文杰 on 2015/10/27.
 */
public interface PostListViewInterface extends MVPView {

    void initList(List<Post> posts);

    void refreshList();

    void setSwipeRefreshing(boolean refreshing);

}
