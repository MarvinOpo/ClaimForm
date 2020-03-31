package com.mvopo.claimform.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mvopo.claimform.R;
import com.mvopo.claimform.helper.PatientAdapter;
import com.mvopo.claimform.models.Patient;

import java.util.ArrayList;

public class PatientListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientAdapter adapter;

    private ArrayList<Patient> patients = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        ArrayList<String> names = new ArrayList<>();
        names.add("John X Doe Male 25yrs");
        names.add("Jane F Go Female 22yrs");
        names.add("Jake O Rivera Male 31yrs");

        for(int i = 0; i < 3; i++){
            Patient patient = new Patient();
            patient.setId("2019070501070"+i);
            patient.setFname(names.get(i).split(" ")[0]);
            patient.setMname(names.get(i).split(" ")[1]);
            patient.setLname(names.get(i).split(" ")[2]);
            patient.setGender(names.get(i).split(" ")[3]);
            patient.setBdate(names.get(i).split(" ")[4] + " old");
            patients.add(patient);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_layout, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PatientAdapter(patients);

        recyclerView.setAdapter(adapter);

        return view;
    }
}
