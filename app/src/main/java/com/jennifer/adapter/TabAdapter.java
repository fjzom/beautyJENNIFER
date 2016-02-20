package com.jennifer.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jennifer.fragment.TabFragment;

/**
 * Created by Alex on 15/01/2016.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private String [] TAB_NAME = {"PRIVADAS", "PUBLICAS", "INVITACIONES"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return TabFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_NAME[position];
    }
}