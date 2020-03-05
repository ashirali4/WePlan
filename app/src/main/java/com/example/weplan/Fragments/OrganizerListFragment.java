package com.example.weplan.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.weplan.Classes.FirebaseHelper;
import com.example.weplan.Classes.Services;
import com.example.weplan.Fragments.dummy.DummyContent;
import com.example.weplan.R;

import java.util.ArrayList;

public class OrganizerListFragment extends Fragment {
    FirebaseHelper helper;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    ShimmerRecyclerView recyclerView;
    ArrayList<Services> templist;
    private OrganizerListFragment.OnListFragmentInteractionListener mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organizerlist_list, container, false);
         recyclerView = (ShimmerRecyclerView) view.findViewById(R.id.shimmer_recycler_view);
        recyclerView.showShimmerAdapter();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (ShimmerRecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }


            helper = new FirebaseHelper();
            helper.getlist(new FirebaseHelper.Callback() {




                @Override
                public void onFailure(Exception e) {

                }

                @Override
                public void onSuccess(ArrayList<Services> arrayList) {
                    templist=arrayList;
                    recyclerView.setAdapter(new MyOrganizerListRecyclerViewAdapter(templist,mListener));

                }


            });

        }
        return view;

    }
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ServiceslistFragment.OnListFragmentInteractionListener) {
            mListener = (OrganizerListFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction();
    }
}