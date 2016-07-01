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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 精选 tab
 */
public class PostFragment extends Fragment {

    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.container)
    ViewPager mViewPager;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private PagerAdapter mPagerAdapter;

    private List<Fragment> mFragments;
    private List<String> mTitles;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PostFragment.
     */
    public static PostFragment newInstance() {
        PostFragment fragment = new PostFragment();
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
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mFragments == null) {
            mTitles = new ArrayList<>();
            mFragments = new ArrayList<>();
            mTitles.add("推荐");
            mFragments.add(PostListFragment.newInstance(null));

            mTitles.add("大学生");
            mFragments.add(TopicFragment.newInstance("大学生"));

            mTitles.add("学习");
            mFragments.add(TopicFragment.newInstance("学习"));

            mTitles.add("生活");
            mFragments.add(TopicFragment.newInstance("生活"));

            mTitles.add("感情");
            mFragments.add(TopicFragment.newInstance("感情"));

            mTitles.add("社会");
            mFragments.add(TopicFragment.newInstance("社会"));

            mTitles.add("职业");
            mFragments.add(TopicFragment.newInstance("职业"));


        }

        FragmentManager fm = getChildFragmentManager();
        mPagerAdapter = new PagerAdapter(fm);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(getPageChangeListener());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
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
     * one of the sections/mTabLayout/pages.
     */
    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

    }


}
