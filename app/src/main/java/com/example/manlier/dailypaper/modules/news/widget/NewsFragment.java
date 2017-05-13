package com.example.manlier.dailypaper.modules.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manlier.dailypaper.R;
import com.example.manlier.dailypaper.commons.NewsType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manlier on 2017/5/10.
 */

/**
 * 显示新闻的Fragment
 */
public class NewsFragment extends Fragment {

    // tab布局
    private TabLayout tabLayout;

    private NewsFragmentPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        adapter = new NewsFragmentPagerAdapter(getChildFragmentManager());

        // 建立tab视图
        buildViewPager(viewPager, adapter);

        // 当切换tab页面时，重新配置FAB按钮的点击事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((NewsListFragment) adapter.getItem(tab.getPosition())).changeFABAction();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    /**
     * 配置tab视图的页面
     */
    private void buildViewPager(ViewPager viewPager, NewsFragmentPagerAdapter adapter) {


        for (NewsType type : NewsType.values()) {

            // 添加tab导航条并添加新闻标题
            tabLayout.addTab(tabLayout.newTab().setText(type.DESC));
            // 添加相应于tab选项的页面
            adapter.addFragment(NewsListFragment.newInstance(type), type.DESC);
        }
        viewPager.setAdapter(adapter);
        // 与视图调度器建立关系
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 新闻页面调度器的适配器
     */
    private static class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitles = new ArrayList<>();

        NewsFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}
