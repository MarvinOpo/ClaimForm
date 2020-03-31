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

public class Cf4PhysicalExamFragment extends Fragment {

    private StatusViewScroller statusViewScroller;
    private FloatingActionButton nextFab;
    private View view;

    Cf4HeentChestFragment chcf = new Cf4HeentChestFragment();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_cf4_physical_exam, container, false);
            statusViewScroller = view.findViewById(R.id.cf4_step_status);
            nextFab = view.findViewById(R.id.physical_exam_next_fab);

            statusViewScroller.getStatusView().scrollTo(800, 0);
            nextFab.setOnClickListener(view1 -> {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, chcf).addToBackStack(null).commit();
            });
        }

        return view;
    }
}
