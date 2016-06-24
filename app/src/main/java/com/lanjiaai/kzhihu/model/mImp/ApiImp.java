package com.lanjiaai.kzhihu.model.mImp;

import android.text.TextUtils;

import com.lanjiaai.kzhihu.common.ModelErrorException;
import com.lanjiaai.kzhihu.model.api.ApiServiceFactory;
import com.lanjiaai.kzhihu.model.entity.Answer;
import com.lanjiaai.kzhihu.model.entity.AnswerModel;
import com.lanjiaai.kzhihu.model.entity.Post;
import com.lanjiaai.kzhihu.model.entity.PostsModel;
import com.lanjiaai.kzhihu.model.entity.SearchUser;
import com.lanjiaai.kzhihu.model.entity.SearchUserModel;
import com.lanjiaai.kzhihu.model.entity.TopUser;
import com.lanjiaai.kzhihu.model.entity.TopUserModel;
import com.lanjiaai.kzhihu.model.entity.UserDetail;
import com.lanjiaai.kzhihu.model.mInterface.ApiInterface;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jack on 2016/3/23.
 */
public class ApiImp implements ApiInterface {

    @Override
    public Observable<List<Post>> getPosts() {
        return getPosts("");
    }

    /**
     * 获取 Post
     *
     * @param publishTime 某个时间戳之前的 Post
     */
    @Override
    public Observable<List<Post>> getPosts(String publishTime) {
        return ApiServiceFactory.getApiService().getPosts(publishTime)
                .map(new Func1<PostsModel, List<Post>>() {
                    @Override
                    public List<Post> call(PostsModel postsModel) {
                        return postsModel.getPosts();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Answer>> getAnswers(final String date, final String name) {

        return ApiServiceFactory.getApiService().getPostAnswers(date, name)
                .map(new Func1<AnswerModel, List<Answer>>() {
                    @Override
                    public List<Answer> call(AnswerModel answerModel) {
                        return answerModel.getAnswers();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<TopUser>> getTopUser(String type, int page, int item) {
        return ApiServiceFactory.getApiService().getTopUser(type, page, item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<TopUserModel, List<TopUser>>() {
                    @Override
                    public List<TopUser> call(TopUserModel model) {
                        String error = model.getError();
                        if (!TextUtils.isEmpty(error)) {
                            throw new ModelErrorException(error);
                        }
                        return model.getTopuser();
                    }
                });
    }

    @Override
    public Observable<UserDetail> getUserDetail(String userHash) {
        return ApiServiceFactory.getApiService().getUserDetail(userHash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<UserDetail>() {
                    @Override
                    public void call(UserDetail userDetail) {
                        String error = userDetail.getError();
                        if (!TextUtils.isEmpty(error)) {
                            throw new ModelErrorException(error);
                        }
                    }
                });
    }

    @Override
    public Observable<List<SearchUser>> searchUser(String key) {
        return ApiServiceFactory.getApiService().searchUser(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<SearchUserModel, List<SearchUser>>() {
                    @Override
                    public List<SearchUser> call(SearchUserModel searchUserModel) {
                        if (!TextUtils.isEmpty(searchUserModel.getError())) {
                            throw new ModelErrorException(searchUserModel.getError());
                        }
                        return searchUserModel.getUsers();
                    }
                });
    }


}
