package com.example.asus.mynotebook.view.pages.mainpager;

import android.app.Activity;


import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewParent;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.presenter.mainpager.BlankFragment;
import com.example.asus.mynotebook.view.interfaces.BasePager;

import java.util.ArrayList;
import java.util.List;

public class NewMainPager extends BasePager {

    private final FragmentManager mFragmentmanager;
    private ViewPager newviewPager;
    private TabLayout tab_layout;

    public NewMainPager(Activity activity, FragmentManager fragmentManager) {
        super(activity);
        mFragmentmanager = fragmentManager;
    }

    @Override
    public void initData() {
        View view = View.inflate(mactivity, R.layout.pager_newmain, null);
        frame_Content_Layout.addView(view);
        tab_layout = view.findViewById(R.id.newtab_layout);
        newviewPager = view.findViewById(R.id.newviewpager);
        newviewPager.setOffscreenPageLimit(3);
        setupViewPager(newviewPager);
        tab_layout.addTab(tab_layout.newTab().setText("数学"));
        tab_layout.addTab(tab_layout.newTab().setText("语文"));
        tab_layout.addTab(tab_layout.newTab().setText("英语"));
        tab_layout.addTab(tab_layout.newTab().setText("物理"));
        tab_layout.addTab(tab_layout.newTab().setText("化学"));
        tab_layout.addTab(tab_layout.newTab().setText("生物"));
        tab_layout.addTab(tab_layout.newTab().setText("历史"));
        tab_layout.addTab(tab_layout.newTab().setText("地理"));
        tab_layout.addTab(tab_layout.newTab().setText("政治"));
        tab_layout.setupWithViewPager(newviewPager);

    }

    private void setupViewPager(ViewPager newviewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter((mFragmentmanager));

        adapter.addFragment(new BlankFragment("数学"),"数学");
        adapter.addFragment(new BlankFragment("语文"),"语文");
        adapter.addFragment(new BlankFragment("英语"),"英语");
        adapter.addFragment(new BlankFragment("物理"),"物理");
        adapter.addFragment(new BlankFragment("化学"),"化学");
        adapter.addFragment(new BlankFragment("生物"),"生物");
        adapter.addFragment(new BlankFragment("历史"),"历史");
        adapter.addFragment(new BlankFragment("地理"),"地理");
        adapter.addFragment(new BlankFragment("政治"),"政治");

        newviewPager.setAdapter(adapter);
    }
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
