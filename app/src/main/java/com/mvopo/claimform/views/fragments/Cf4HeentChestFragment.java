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

public class Cf4HeentChestFragment extends Fragment {

    private StatusViewScroller statusViewScroller;
    private FloatingActionButton nextFab;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_cf4_heent_chest, container, false);
            statusViewScroller = view.findViewById(R.id.cf4_step_status);
            nextFab = view.findViewById(R.id.heent_chest_next_fab);

            statusViewScroller.getStatusView().scrollTo(1000, 0);

            nextFab.setOnClickListener(view1 -> {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new Cf4HCIFragment()).addToBackStack(null).commit();
            });
        }

        return view;
    }
}
