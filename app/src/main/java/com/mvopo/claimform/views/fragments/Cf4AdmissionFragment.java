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

public class Cf4AdmissionFragment extends Fragment {

    private FloatingActionButton nextFab;
    private View view;

    private Cf4SignSympFragment cssf = new Cf4SignSympFragment();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_cf4_admission, container, false);
            nextFab = view.findViewById(R.id.admission_next_fab);

            nextFab.setOnClickListener(view1 -> {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, cssf).addToBackStack(null).commit();
            });
        }

        return view;
    }
}
