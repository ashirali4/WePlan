package com.example.weplan.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.kalert.KAlertDialog;
import com.example.weplan.Adapters.Adapter;
import com.example.weplan.Adapters.Servicehomeadapter;
import com.example.weplan.Classes.FirebaseHelper;
import com.example.weplan.Classes.Services;
import com.example.weplan.Classes.servicelist;
import com.example.weplan.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Location.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Location#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Location extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
  //  ArrayList<Services> templist;
    FirebaseHelper helper;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2; public RecyclerView recyclerView;
    public RecyclerView recyclerVieww;

    KAlertDialog pDialog;

    // Array list for recycler view data source
    ArrayList<Services> source;
    ArrayList<servicelist> sources;
    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerView.LayoutManager RecyclerViewLayoutManagerr;
    LinearLayoutManager HorizontalLayout,HorizontalLayoutt;
    // adapter class object
    Adapter adapter;
    Servicehomeadapter serviceadapter;

    // Linear Layout Manager

View view;


    private OnFragmentInteractionListener mListener;

    public Location() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Location.
     */
    // TODO: Rename and change types and number of parameters
    public static Location newInstance(String param1, String param2) {
        Location fragment = new Location();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_location, container, false);
        pDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#4E67FD"));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerVieww = view.findViewById(R.id.servicesslist);
        RecyclerViewLayoutManager
                = new LinearLayoutManager(
                view.getContext());

        // Set LayoutManager on Recycler View
        recyclerView.setLayoutManager(
                RecyclerViewLayoutManager);
        recyclerVieww.setLayoutManager(
                RecyclerViewLayoutManagerr);
        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList();

        // calling constructor of adapter
        // with source list as a parameter



    serviceadapter=new Servicehomeadapter(sources);
        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout
                = new LinearLayoutManager(
                view.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        HorizontalLayoutt
                = new LinearLayoutManager(
                view.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerVieww.setLayoutManager(HorizontalLayoutt);
        // Set adapter on recycler view
        pDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.hide();
                recyclerView.setAdapter(adapter);
                recyclerVieww.setAdapter(serviceadapter);
            }
        }, 5000);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void AddItemsToRecyclerViewArrayList()


    {
        sources=new ArrayList<servicelist>();
        servicelist s_list=new servicelist();
        s_list.sname="Food";
        s_list.logolink="https://pngimage.net/wp-content/uploads/2018/06/food-png-icon-6.png";
        servicelist s_list1=new servicelist();
        s_list1.sname="Photography";
        s_list1.logolink="https://www.pngkit.com/png/full/50-500826_open-camera-circle-icon.png";
        sources.add(s_list1);
        sources.add(s_list);
        servicelist s2=new servicelist();
        s2.sname="Venue";
        s2.logolink="https://i.ya-webdesign.com/images/red-marker-circle-png-15.png";
        sources.add(s2);
        // Adding items to ArrayList
        source = new ArrayList<>();
      //  Services service=new Services();
     //   service.id="ashir";
        helper = new FirebaseHelper();
        helper.getlist(new FirebaseHelper.Callback() {
            @Override
            public void onFailure(Exception e) {
            }
            @Override
            public void onSuccess(ArrayList<Services> arrayList) {
                source=arrayList;
                adapter = new Adapter(arrayList);
            }
        });


    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
