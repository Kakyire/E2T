package com.kakyireinc.e2t;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Kakyire LastBorn on 3/2/2019.
 */

public class FragmentsAdapter extends FragmentPagerAdapter {


    ArrayList<Fragment> mFragment=new ArrayList<>();
    ArrayList<String> mTitles=new ArrayList<>();


    public FragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }


//    public  void addFragment(Fragment fragment,)
}
