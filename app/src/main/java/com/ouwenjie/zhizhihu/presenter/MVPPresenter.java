/*
 * Copyright (C) 2015 Saúl Molinero.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ouwenjie.zhizhihu.presenter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.common.ModelErrorException;
import com.ouwenjie.zhizhihu.ui.viewInterface.MVPView;
import com.ouwenjie.zhizhihu.utils.NetworkUtil;

import rx.subscriptions.CompositeSubscription;

/**
 * Interface that represents a MVPPresenter in the model view presenter Pattern
 * defines methods to manage the Activity / Fragment lifecycle
 */
public abstract class MVPPresenter<T extends MVPView> {

    protected T mViewInterface;
    protected Context mContext;

    protected CompositeSubscription mCompositeSubscription;

    public MVPPresenter(T viewInterface) {
        this.mViewInterface = viewInterface;
        mContext = viewInterface.getContext();
        mCompositeSubscription = new CompositeSubscription();
    }

    /**
     * Called when the presenter is initialized
     */
    public abstract void create();

    /**
     * Called when the presenter is stop,
     * i.e when an activity * or a fragment finishes
     */
    public abstract void destroy();

    public T getAttachedView() {
        return mViewInterface;
    }

    protected void doRxError(Throwable e) {
        Logger.t("doRxError=> ").e(e.getMessage());
        if (e instanceof ModelErrorException) {
            // 网络请求成功，但 code != 1，则显示后台的 message
            mViewInterface.toast(e.getMessage());
        } else {
            // 网络错误
            if (!NetworkUtil.isNetworkAvailable(mContext)) {
                mViewInterface.toast(mViewInterface.getContext().getString(R.string.label_network_not_available));
            } else {
                // 其它错误
                mViewInterface.toast(mViewInterface.getContext().getString(R.string.label_operation_failed));
            }
        }
    }

}
