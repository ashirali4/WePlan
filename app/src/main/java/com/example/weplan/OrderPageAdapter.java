package com.example.weplan;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.weplan.Fragments.OrganizerListFragment;
import com.example.weplan.Fragments.ServiceslistFragment;


public class OrderPageAdapter extends FragmentStateAdapter {


    public OrderPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OrganizerListFragment();

              case 1:
            return new ServiceslistFragment();

            default:
                return new OrganizerListFragment();
        }



    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
