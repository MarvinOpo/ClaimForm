package com.mvopo.claimform.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.clans.fab.FloatingActionButton;
import com.mvopo.claimform.R;

import params.com.stepview.StatusViewScroller;

public class Cf4SignSympFragment extends Fragment {

    private StatusViewScroller statusViewScroller;
    private FloatingActionButton nextFab;
    private View view;

    private  Cf4PhysicalExamFragment cpef = new Cf4PhysicalExamFragment();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_cf4_sign_symp, container, false);
            statusViewScroller = view.findViewById(R.id.cf4_step_status);
            nextFab = view.findViewById(R.id.sign_symp_next_fab);

            statusViewScroller.getStatusView().scrollTo(400, 0);

            nextFab.setOnClickListener(view1 -> {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, cpef).addToBackStack(null).commit();
            });
        }

        return view;
    }
}
