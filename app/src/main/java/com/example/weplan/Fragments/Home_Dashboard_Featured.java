package com.example.weplan.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.developer.kalert.KAlertDialog;
import com.example.weplan.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home_Dashboard_Featured.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home_Dashboard_Featured#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_Dashboard_Featured extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    KAlertDialog pDialog;
  View viewhome;
    Animation fadeIn;
    LinearLayout dialoge,dialoge1;
    AlphaAnimation buttonClick;
    private OnFragmentInteractionListener mListener;

    public Home_Dashboard_Featured() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_Dashboard_Featured.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_Dashboard_Featured newInstance(String param1, String param2) {
        Home_Dashboard_Featured fragment = new Home_Dashboard_Featured();
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
        // Inflate the layout for this fragment
        viewhome= inflater.inflate(R.layout.fragment_home__dashboard__featured, container, false);
          buttonClick = new AlphaAnimation(2F, 0.8F);



         ImageButton button = (ImageButton) viewhome.findViewById(R.id.imageButton2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(buttonClick);

            }
        });




       // fadeIn = new AlphaAnimation(0, 1);
      //  fadeIn.setInterpolator(new DecelerateInterpolator()); //add this

        fadeIn   =    AnimationUtils.loadAnimation(getContext(), R.anim.layoutloading);

        fadeIn.setDuration(2000);
        dialoge   = (LinearLayout)viewhome.findViewById(R.id.weplanassi);
        dialoge.setVisibility(LinearLayout.VISIBLE);

        dialoge1   = (LinearLayout)viewhome.findViewById(R.id.require);
        dialoge1.setVisibility(LinearLayout.VISIBLE);

        pDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#4E67FD"));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.hide();

            }
        }, 1000);
        dialoge.setAnimation(fadeIn);
        dialoge1.setAnimation(fadeIn);
        dialoge.animate();
        dialoge1.animate();
        fadeIn.start();
        return viewhome;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
