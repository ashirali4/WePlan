package com.example.weplan;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAccess extends FragmentPagerAdapter {


    public TabsAccess(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            FragmentChats fragmentChats=new FragmentChats();
            return  fragmentChats;

        }
        else if(position==1)
        {
            FragmentOthers fragmentOthers=new FragmentOthers();
            return  fragmentOthers;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0)
        {
            return "Chats";

        }
        else if(position==1)
        {
            return "Others";

        }
        return super.getPageTitle(position);

    }
}
