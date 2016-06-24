package com.ouwenjie.zhizhihu.model.entity;

import java.util.List;

/**
 * Created by 文杰 on 2015/10/19.
 */
public class PostsModel extends BaseModel {

    int count;
    List<Post> posts;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
