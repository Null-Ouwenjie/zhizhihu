package com.ouwenjie.zhizhihu.model.mInterface;

import com.ouwenjie.zhizhihu.model.entity.Answer;
import com.ouwenjie.zhizhihu.model.entity.Post;
import com.ouwenjie.zhizhihu.model.entity.SearchUser;
import com.ouwenjie.zhizhihu.model.entity.TopUser;
import com.ouwenjie.zhizhihu.model.entity.UserDetail;

import java.util.List;

import rx.Observable;

/**
 * Created by Jack on 2016/3/23.
 */
public interface ApiInterface {

    Observable<List<Post>> getPosts();

    Observable<List<Post>> getPosts(String publishTime);

    Observable<List<Answer>> getAnswers(String date, String name);

    Observable<List<TopUser>> getTopUser(String type, int page, int item);

    Observable<UserDetail> getUserDetail(String userHash);

    Observable<List<SearchUser>> searchUser(String key);

}
