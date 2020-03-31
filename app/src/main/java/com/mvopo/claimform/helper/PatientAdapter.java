package com.mvopo.claimform.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.mvopo.claimform.R;
import com.mvopo.claimform.models.Patient;
import com.mvopo.claimform.views.MainActivity;
import com.mvopo.claimform.views.fragments.Cf4HCIFragment;
import com.mvopo.claimform.views.fragments.PatientDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private List<Patient> patients;

    public PatientAdapter(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        PatientViewHolder holder = new PatientViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient patient = patients.get(position);

        holder.idTv.setText(patient.getId());

        String name = patient.getFname() + " " + patient.getMname() + ". " + patient.getLname();
        holder.nameTv.setText(name);

        String genderAge = patient.getGender() + " | " + patients.get(position).getBdate();
        holder.genderAgeTv.setText(genderAge);

        holder.viewMoreTv.setOnClickListener(view -> {
            TextView viewBtn =  ((TextView) view);
            String action = viewBtn.getText().toString();

            if(action.equalsIgnoreCase(view.getContext().getString(R.string.view_more))) {
                holder.detailsLayout.setVisibility(View.VISIBLE);
                viewBtn.setText(view.getContext().getString(R.string.view_less));
            }else{
                holder.detailsLayout.setVisibility(View.GONE);
                viewBtn.setText(view.getContext().getString(R.string.view_more));
            }
        });

        holder.visitBtn.setOnClickListener(view -> {
            FragmentTransaction ft = ((MainActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, new PatientDetailFragment()).addToBackStack(null).commit();
        });

        holder.claimFormBtn.setOnClickListener(view -> {
            FragmentTransaction ft = ((MainActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, new Cf4HCIFragment()).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder{
        TextView idTv, nameTv, genderAgeTv, viewMoreTv;
        ConstraintLayout detailsLayout;
        Button visitBtn, claimFormBtn;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            idTv = itemView.findViewById(R.id.patient_id_tv);
            nameTv = itemView.findViewById(R.id.patient_name_tv);
            genderAgeTv = itemView.findViewById(R.id.patient_gender_age_tv);
            viewMoreTv = itemView.findViewById(R.id.patient_view_more_tv);

            detailsLayout = itemView.findViewById(R.id.detail_container_layout);
            visitBtn = itemView.findViewById(R.id.visit_btn);
            claimFormBtn = itemView.findViewById(R.id.claim_form_btn);
        }
    }
}
