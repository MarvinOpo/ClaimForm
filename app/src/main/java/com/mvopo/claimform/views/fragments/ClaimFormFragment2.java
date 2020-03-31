package com.mvopo.claimform.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.clans.fab.FloatingActionButton;
import com.mvopo.claimform.R;

import params.com.stepview.StatusViewScroller;

public class ClaimFormFragment2 extends Fragment {

    private StatusViewScroller stepSvs;
    private FloatingActionButton nextFab;

    private int ctr = 0;

    private FragmentManager fm;
    private FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_claim_from2, container, false);
//        nextFab = view.findViewById(R.id.cf4_next_fab);

        fm = getActivity().getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.cf4_fragment_container, new Cf4HCIFragment()).addToBackStack(null).commit();

        ctr = 1;

        nextFab.setOnClickListener(view1 -> {

        });

        return view;
    }

    public void nextStep(int step){
        stepSvs.getStatusView().setCurrentCount(step);
        stepSvs.scrollToStep(step);

        switch (step){
            case 2:
                ft = fm.beginTransaction();
                ft.replace(R.id.cf4_fragment_container, new Cf4AdmissionFragment()).addToBackStack(null).commit();
                break;
        }
    }


}
