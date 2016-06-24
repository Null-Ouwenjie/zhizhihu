package com.lanjiaai.kzhihu.model.entity;

import java.util.List;

/**
 * Created by Jack on 2016/2/3.
 */
public class SearchUserModel extends BaseModel {

    int count;
    List<SearchUser> users;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SearchUser> getUsers() {
        return users;
    }

    public void setUsers(List<SearchUser> users) {
        this.users = users;
    }
}
