package com.ouwenjie.zhizhihu.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.model.api.TopUserType;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 大V  tab
 */
public class TopUserFragment extends Fragment {

    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.container)
    ViewPager container;
    @Bind(R.id.coordinator)
    CoordinatorLayout coordinator;

    private PagerAdapter pagerAdapter;
    private FragmentManager fm;

    private List<Fragment> fragments;
    private List<String> titles;

    public TopUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PostFragment.
     */
    public static TopUserFragment newInstance() {
        TopUserFragment fragment = new TopUserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_user, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (fragments == null) {
            titles = new ArrayList<>();
            fragments = new ArrayList<>();
            fragments.add(TopUserListFragment.newInstance(TopUserType.agreeiw));
            fragments.add(TopUserListFragment.newInstance(TopUserType.followeriw));
            fragments.add(TopUserListFragment.newInstance(TopUserType.agree));
            fragments.add(TopUserListFragment.newInstance(TopUserType.follower));
            fragments.add(TopUserListFragment.newInstance(TopUserType.ask));
            fragments.add(TopUserListFragment.newInstance(TopUserType.answer));
            fragments.add(TopUserListFragment.newInstance(TopUserType.post));
            fragments.add(TopUserListFragment.newInstance(TopUserType.thanks));
            fragments.add(TopUserListFragment.newInstance(TopUserType.fav));

            titles.add("赞同飙升");
            titles.add("粉丝飙升");
            titles.add("最多赞");
            titles.add("最多粉");
            titles.add("问题最多");
            titles.add("懂得最多");
            titles.add("专栏榜");
            titles.add("收到感谢");
            titles.add("人人收藏");

        }

        fm = getChildFragmentManager();
        pagerAdapter = new PagerAdapter(fm);
        container.setAdapter(pagerAdapter);
        container.setOffscreenPageLimit(5);
        container.addOnPageChangeListener(getPageChangeListener());
        tabs.setupWithViewPager(container);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private ViewPager.OnPageChangeListener getPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

    }


}
