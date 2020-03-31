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

public class Cf4HCIFragment extends Fragment {

    private FloatingActionButton nextFab;
    private Cf4AdmissionFragment caf = new Cf4AdmissionFragment();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cf4_hci, container, false);
        nextFab = view.findViewById(R.id.hci_next_fab);

        nextFab.setOnClickListener(view1 -> {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, caf).addToBackStack(null).commit();
        });
        return view;
    }
}
