package com.ouwenjie.zhizhihu.model.api;

import com.ouwenjie.zhizhihu.model.entity.AnswerModel;
import com.ouwenjie.zhizhihu.model.entity.CheckNewModel;
import com.ouwenjie.zhizhihu.model.entity.PostsModel;
import com.ouwenjie.zhizhihu.model.entity.SearchUserModel;
import com.ouwenjie.zhizhihu.model.entity.TopUserModel;
import com.ouwenjie.zhizhihu.model.entity.UserDetail;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @link http://www.kanzhihu.com/api-document
 * <p/>
 * Created by 文杰 on 2015/10/18.
 */
public interface ApiService {

    /**
     * 获取最新的 Post 列表
     *
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("getposts")
    Observable<PostsModel> getPosts();

    /**
     * 获取一个时间戳之前的 Post
     *
     * @param publishTime 时间戳
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("getposts/{publishTime}")
    Observable<PostsModel> getPosts(@Path("publishTime") String publishTime);

    /**
     * 检查「看知乎」首页在指定时间之后有没有更新
     *
     * @param publishTime 时间戳
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("checknew/{publishTime}")
    Observable<CheckNewModel> checkNew(@Path("publishTime") String publishTime);

    /**
     * 获取单篇 Post 的答案列表
     *
     * @param date
     * @param category
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("getpostanswers/{date}/{category}")
    Observable<AnswerModel> getPostAnswers(@Path("date") String date, @Path("category") String category);

    /**
     * 获取单个用户的详细数据
     *
     * @param userHash
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("userdetail2/{hash}")
    Observable<UserDetail> getUserDetail(@Path("hash") String userHash);

    /**
     * 获取 top 3 用户
     *
     * @param type
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("topuser/{type}/3")
    Observable<TopUserModel> getTop1User(@Path("type") String type);


    /**
     * 获取 top 用户
     *
     * @param type
     * @param page
     * @param item
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("topuser/{type}/{page}/{item}")
    Observable<TopUserModel> getTopUser(
            @Path("type") String type,
            @Path("page") int page,
            @Path("item") int item
    );

    /**
     * 搜索用户
     *
     * @param key
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("searchuser/{key}")
    Observable<SearchUserModel> searchUser(@Path("key") String key);

}
