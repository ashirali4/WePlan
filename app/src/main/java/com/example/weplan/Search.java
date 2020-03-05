package com.example.weplan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.weplan.Fragments.OrganizerListFragment;
import com.example.weplan.Fragments.ServiceslistFragment;
import com.example.weplan.Fragments.dummy.DummyContent;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class Search extends AppCompatActivity implements ServiceslistFragment.OnListFragmentInteractionListener, OrganizerListFragment.OnListFragmentInteractionListener {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);




        ViewPager2 viewPager2=findViewById(R.id.viewpager);
        viewPager2.setAdapter(new OrderPageAdapter(this));
        TabLayout tableLayout=findViewById(R.id.tablelayoutforsearch);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(
                tableLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Orginzers");
                        tab.setIcon(R.drawable.ic_people_black_24dp);
                        break;
                    case 1:
                        tab.setText("Services");
                        tab.setIcon(R.drawable.ic_playlist_play_black_24dp);
                        BadgeDrawable bd=tab.getOrCreateBadge();
                        bd.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
                        bd.setVisible(true);
                        bd.setNumber(100);
                        break;

                }
            }
        }
        );
        tabLayoutMediator.attach();

    }



    public void onListFragmentInteraction() {

    }
}