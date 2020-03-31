package com.mvopo.claimform.views.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mvopo.claimform.R;
import com.mvopo.claimform.helper.CheckBoxGroup;

public class ClaimFormFragment extends Fragment {

    private TextView hciLabel, admLabel, referLabel, signSympLabel,
            phyExamLabel, vitalsLabel, heentLabel, chestLungsLabel, cvsLabel, abdomenLabel, guLabel,
            skinLabel, neuroLabel, digitalLabel;

    private ConstraintLayout hciCl, admissionCl, referredCl, vitalsCl;
    private CheckBoxGroup signSympCbg, phyExamCbg, heentCbg, chestLungsCbg,
            cvsCbg, abdomenCbg, guCbg, skinCbg, neuroCbg, digitalCbg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_claim_form, container, false);

        ScrollView scrollView = view.findViewById(R.id.form_scroll_view);
        ConstraintLayout formContainer = view.findViewById(R.id.form_container);

        hciLabel = view.findViewById(R.id.hci_info_label);
        admLabel = view.findViewById(R.id.admission_info_label);
        referLabel = view.findViewById(R.id.referred_label);
        signSympLabel = view.findViewById(R.id.sign_symtoms_label);
        phyExamLabel = view.findViewById(R.id.physical_exam_label);
        vitalsLabel = view.findViewById(R.id.vitals_label);
        heentLabel = view.findViewById(R.id.heent_label);
        chestLungsLabel = view.findViewById(R.id.chest_lungs_label);
        cvsLabel = view.findViewById(R.id.cvs_label);
        abdomenLabel = view.findViewById(R.id.abdomen_label);
        guLabel = view.findViewById(R.id.guie_label);
        skinLabel = view.findViewById(R.id.skin_extremeties_label);
        neuroLabel = view.findViewById(R.id.neuro_label);
        digitalLabel = view.findViewById(R.id.digital_label);

        hciCl = view.findViewById(R.id.hci_info_container);
        admissionCl = view.findViewById(R.id.admission_info_container);
        referredCl = view.findViewById(R.id.referred_info_container);
        vitalsCl = view.findViewById(R.id.vitals_info_container);

        signSympCbg = view.findViewById(R.id.sign_symptoms_cbg);
        phyExamCbg = view.findViewById(R.id.physical_exam_cbg);
        heentCbg = view.findViewById(R.id.heent_cbg);
        chestLungsCbg = view.findViewById(R.id.chest_lungs_cbg);
        cvsCbg = view.findViewById(R.id.cvs_cbg);
        abdomenCbg = view.findViewById(R.id.abdomen_cbg);
        guCbg = view.findViewById(R.id.guie_cbg);
        skinCbg = view.findViewById(R.id.skin_extremities_cbg);
        neuroCbg = view.findViewById(R.id.neuro_cbg);
        digitalCbg = view.findViewById(R.id.digital_cbg);

        View.OnClickListener containerListener = (view1 -> {
            hideContainers();

            switch (view1.getId()) {
                case R.id.hci_info_label:
                    hciCl.setVisibility(View.VISIBLE);
                    break;
                case R.id.admission_info_label:
                    admissionCl.setVisibility(View.VISIBLE);
                    break;
                case R.id.referred_label:
                    referredCl.setVisibility(View.VISIBLE);
                    break;
                case R.id.vitals_label:
                    vitalsCl.setVisibility(View.VISIBLE);
                    break;
                case R.id.sign_symtoms_label:
                    signSympCbg.setVisibility(View.VISIBLE);
                    break;
                case R.id.physical_exam_label:
                    phyExamCbg.setVisibility(View.VISIBLE);
                    break;
                case R.id.heent_label:
                    heentCbg.setVisibility(View.VISIBLE);
                    break;
                case R.id.chest_lungs_label:
                    chestLungsCbg.setVisibility(View.VISIBLE);
                    break;
                case R.id.cvs_label:
                    cvsCbg.setVisibility(View.VISIBLE);
                    break;
                case R.id.abdomen_label:
                    abdomenCbg.setVisibility(View.VISIBLE);
                    break;
                case R.id.guie_label:
                    guCbg.setVisibility(View.VISIBLE);
                    break;
                case R.id.skin_extremeties_label:
                    skinCbg.setVisibility(View.VISIBLE);
                    break;
                case R.id.neuro_label:
                    neuroCbg.setVisibility(View.VISIBLE);
                    break;
                case R.id.digital_label:
                    digitalCbg.setVisibility(View.VISIBLE);
                    break;
            }

            new Handler().postDelayed(() -> {
                Rect offsetViewBounds = new Rect();

                view1.getDrawingRect(offsetViewBounds);
                formContainer.offsetDescendantRectToMyCoords(view1, offsetViewBounds);
                int relativeTop = offsetViewBounds.top;

                scrollView.smoothScrollTo(0, relativeTop);
            }, 200);
        });

        hciLabel.setOnClickListener(containerListener);
        admLabel.setOnClickListener(containerListener);
        referLabel.setOnClickListener(containerListener);
        vitalsLabel.setOnClickListener(containerListener);

        signSympLabel.setOnClickListener(containerListener);
        phyExamLabel.setOnClickListener(containerListener);
        heentLabel.setOnClickListener(containerListener);
        chestLungsLabel.setOnClickListener(containerListener);
        cvsLabel.setOnClickListener(containerListener);
        abdomenLabel.setOnClickListener(containerListener);
        guLabel.setOnClickListener(containerListener);
        skinLabel.setOnClickListener(containerListener);
        neuroLabel.setOnClickListener(containerListener);
        digitalLabel.setOnClickListener(containerListener);

        return view;
    }

    public void hideContainers() {
        hciCl.setVisibility(View.GONE);
        admissionCl.setVisibility(View.GONE);
        referredCl.setVisibility(View.GONE);
        vitalsCl.setVisibility(View.GONE);
        signSympCbg.setVisibility(View.GONE);
        phyExamCbg.setVisibility(View.GONE);
        heentCbg.setVisibility(View.GONE);
        chestLungsCbg.setVisibility(View.GONE);
        cvsCbg.setVisibility(View.GONE);
        abdomenCbg.setVisibility(View.GONE);
        guCbg.setVisibility(View.GONE);
        skinCbg.setVisibility(View.GONE);
        neuroCbg.setVisibility(View.GONE);
        digitalCbg.setVisibility(View.GONE);
    }
}
