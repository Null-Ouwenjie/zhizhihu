package com.lanjiaai.kzhihu.ui.viewInterface;

import com.lanjiaai.kzhihu.model.entity.TopUser;

import java.util.List;

/**
 * Created by Jack on 2016/3/23.
 */
public interface TopUserListViewInterface extends MVPView {

    void createUserListView(List<TopUser> topUsers);

}
