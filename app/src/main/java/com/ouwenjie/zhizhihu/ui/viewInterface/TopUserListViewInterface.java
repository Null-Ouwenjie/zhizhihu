package com.ouwenjie.zhizhihu.ui.viewInterface;

import com.ouwenjie.zhizhihu.model.entity.TopUser;

import java.util.List;

/**
 * Created by Jack on 2016/3/23.
 */
public interface TopUserListViewInterface extends MVPView {

    void createUserListView(List<TopUser> topUsers);

}
