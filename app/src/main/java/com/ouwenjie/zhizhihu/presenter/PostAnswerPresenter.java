package com.ouwenjie.zhizhihu.presenter;

import com.ouwenjie.zhizhihu.model.entity.Answer;
import com.ouwenjie.zhizhihu.model.mImp.ApiImp;
import com.ouwenjie.zhizhihu.model.mInterface.ApiInterface;
import com.ouwenjie.zhizhihu.ui.viewInterface.PostAnswerViewInterface;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by 文杰 on 2015/10/27.
 */
public class PostAnswerPresenter extends MVPPresenter<PostAnswerViewInterface> {

    private ApiInterface mApiInterface;

    private List<Answer> mAnswers = new ArrayList<>();

    public PostAnswerPresenter(PostAnswerViewInterface postAnswerViewInterface) {
        super(postAnswerViewInterface);
        mApiInterface = new ApiImp();
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {
        mCompositeSubscription.clear();
    }

    /**
     * 初始化列表
     *
     * @param date
     * @param name
     */
    public void initData(String date, String name) {
        Subscription sub = mApiInterface.getAnswers(date, name)
                .subscribe(new Subscriber<List<Answer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        doRxError(e);
                    }

                    @Override
                    public void onNext(List<Answer> list) {
                        mAnswers = list;
                        mViewInterface.initList(mAnswers);
                    }
                });
        mCompositeSubscription.add(sub);
    }

}
