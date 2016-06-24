package com.lanjiaai.kzhihu.presenter;

import com.lanjiaai.kzhihu.model.entity.Answer;
import com.lanjiaai.kzhihu.model.mImp.ApiImp;
import com.lanjiaai.kzhihu.model.mInterface.ApiInterface;
import com.lanjiaai.kzhihu.ui.viewInterface.PostAnswerViewInterface;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by 文杰 on 2015/10/27.
 */
public class PostAnswerPresenter extends MVPPresenter<PostAnswerViewInterface> {

    ApiInterface apiInterface;

    List<Answer> answers = new ArrayList<>();

    public PostAnswerPresenter(PostAnswerViewInterface postAnswerViewInterface) {
        super(postAnswerViewInterface);
        apiInterface = new ApiImp();
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {

    }

    /**
     * 初始化列表
     *
     * @param date
     * @param name
     */
    public void initData(String date, String name) {
        apiInterface.getAnswers(date, name)
                .subscribe(new Subscriber<List<Answer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Answer> list) {
                        answers = list;
                        viewInterface.initList(answers);
                    }
                });
    }

}
