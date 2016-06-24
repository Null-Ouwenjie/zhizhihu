package com.ouwenjie.zhizhihu.model.entity;

import java.util.List;

/**
 * Created by 文杰 on 2015/10/23.
 */
public class TopUserModel extends BaseModel {

    int count;
    List<TopUser> topuser;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<TopUser> getTopuser() {
        return topuser;
    }

    public void setTopuser(List<TopUser> topuser) {
        this.topuser = topuser;
    }
}
