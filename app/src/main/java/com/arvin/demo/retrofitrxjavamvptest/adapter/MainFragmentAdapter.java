package com.arvin.demo.retrofitrxjavamvptest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.arvin.demo.retrofitrxjavamvptest.base.BaseFragment;

import java.util.List;

/**
 * Created by arvin on 2017/5/27.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private List<BaseFragment> fragmentList;

    public MainFragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList, String[] titles) {
        super(fm);

        this.titles = titles;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList == null ? null : fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? null : titles[position];
    }


}
