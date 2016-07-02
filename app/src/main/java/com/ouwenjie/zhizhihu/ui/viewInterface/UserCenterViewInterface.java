package com.ouwenjie.zhizhihu.ui.viewInterface;

import com.ouwenjie.zhizhihu.model.entity.UserDetail;

/**
 * Created by Administrator on 2016/4/9 0009.
 */
public interface UserCenterViewInterface extends MVPView {

    void initData(UserDetail userDetailInfo);

    void showDefaultInfo();

}
