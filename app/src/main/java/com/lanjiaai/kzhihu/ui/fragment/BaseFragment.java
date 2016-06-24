package com.lanjiaai.kzhihu.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.lanjiaai.kzhihu.App;
import com.lanjiaai.kzhihu.ui.view.ProgressHelper;

/**
 * Created by Jack on 2015/11/17.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void toast(String msg) {
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private ProgressHelper progressHelper;

    public void showProgress() {
        showProgress(null);
    }

    public void showProgress(String msg) {
        if (progressHelper == null) {
            progressHelper = new ProgressHelper(getActivity());
        }
        progressHelper.showProgress(msg);
    }

    public void hideProgress() {
        progressHelper.hideProgress();
    }
}
