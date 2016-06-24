package com.lanjiaai.kzhihu.presenter;

import com.lanjiaai.kzhihu.model.entity.TopUser;
import com.lanjiaai.kzhihu.model.mImp.ApiImp;
import com.lanjiaai.kzhihu.model.mInterface.ApiInterface;
import com.lanjiaai.kzhihu.ui.viewInterface.TopUserListViewInterface;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Jack on 2016/3/23.
 */
public class TopUserListPresenter extends MVPPresenter<TopUserListViewInterface> {

    private int page = 1;
    private static final int pageCount = 20;

    ApiInterface apiInterface;
    String topType;
    List<TopUser> topUsers;


    public TopUserListPresenter(TopUserListViewInterface viewInterface) {
        super(viewInterface);
        apiInterface = new ApiImp();
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {

    }


    public void loadTopUserData(String topType) {
        this.topType = topType;
        apiInterface.getTopUser(topType, page, pageCount)
                .subscribe(new Subscriber<List<TopUser>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TopUser> topUsers) {
                        viewInterface.createUserListView(topUsers);
                    }
                });

    }

}
