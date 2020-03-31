package com.mvopo.claimform.views.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mvopo.claimform.R;
import com.mvopo.claimform.helper.ImageAdapter;
import com.mvopo.claimform.helper.NoteAdapter;
import com.mvopo.claimform.helper.VSAdapter;
import com.mvopo.claimform.models.Note;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PatientDetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private GridView resultGv;
    private RecyclerView notesRv;
    private ViewPager vitalSignPager;

    private ImageView backIvBtn, calendarIvBtn;

    private ArrayList<Note> notes = new ArrayList<>();

    private NoteAdapter noteAdapter;
    private VSAdapter vsAdapter;

    private FloatingActionMenu fabMenu;
    private FloatingActionButton addNoteFab, addVitalSignFab, addProcedureFab;

    private EditText dateEdtx;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        String[] drNames = {
                "Dr. Zero",
                "Dr. Ra",
                "Dr. Zero",
                "Dr. Quack",
                "Dr. Iamsick"
        };

        String[] noteBody = {
                "Regular checking of patient for atleast once an hour",
                "Daily intake of medicine at 3PM with 500ml dosage",
                "This is a sample note",
                "The quick brown fox jumps over a lazy dog",
                "Ang nakayatak nayatakan"
        };

        String dateTime = getContext().getString(R.string.datetime_adm_value);
        for (int i = 0; i < 5; i++) {
            Note note = new Note();
            note.setDrname(drNames[i]);
            note.setNote(noteBody[i]);
            note.setDate(dateTime);

            notes.add(note);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_detail, container, false);

        backIvBtn = view.findViewById(R.id.back_iv);
        calendarIvBtn = view.findViewById(R.id.calendar_iv);
        resultGv = view.findViewById(R.id.patient_result_container);
        notesRv = view.findViewById(R.id.note_recycler);
        vitalSignPager = view.findViewById(R.id.vs_pager);

        fabMenu = view.findViewById(R.id.fab_menu);
        addNoteFab = view.findViewById(R.id.add_note_fab);
        addVitalSignFab = view.findViewById(R.id.add_vital_sign_fab);
        addProcedureFab = view.findViewById(R.id.add_procedure_fab);

        addNoteFab.setOnClickListener(view1 -> {
            showAddNoteDialog();
            fabMenu.close(true);
        });

        addVitalSignFab.setOnClickListener(view1 -> {
            showAddVitalsDialog();
            fabMenu.close(true);
        });

        addProcedureFab.setOnClickListener(view1 -> {
            showAddProcedureDialog();
            fabMenu.close(true);
        });


        resultGv.setAdapter(new ImageAdapter(getContext()));

        resultGv.setOnItemClickListener((adapterView, view12, i, l) -> {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), view12.getId());

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

            File f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "test.jpg");
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Uri path = Uri.fromFile(f);

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri imageUri = FileProvider.getUriForFile(
                    getContext(),
                    getContext().getApplicationContext()
                            .getPackageName() + ".provider", new File(path.getPath()));
            intent.setDataAndType(imageUri, "image/*");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        });

        vsAdapter = new VSAdapter(getContext());
        vitalSignPager.setAdapter(vsAdapter);
        vitalSignPager.setClipToPadding(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        notesRv.setLayoutManager(layoutManager);

        noteAdapter = new NoteAdapter(getContext(), notes);
        notesRv.setAdapter(noteAdapter);

        adjustGridHeight();

        backIvBtn.setOnClickListener(view1 -> getActivity().getSupportFragmentManager()
                .popBackStackImmediate());
        calendarIvBtn.setOnClickListener(view1 -> {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, new PatientCalendarFragment()).addToBackStack(null).commit();
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    public void showAddNoteDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.add_note_dialog, null);
        EditText dateTimeEdtx = dialogView.findViewById(R.id.note_datetime_edtx);
        EditText noteEdtx = dialogView.findViewById(R.id.note_body_edtx);
        Button addBtn = dialogView.findViewById(R.id.note_add_btn);

        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm a", Locale.getDefault()).format(new Date());
        dateTimeEdtx.setText(timeStamp);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();

        addBtn.setOnClickListener(view -> {
            String noteBody = noteEdtx.getText().toString().trim();
            if (noteBody.isEmpty()) {
                Toast.makeText(getContext(), "Please add some note content.", Toast.LENGTH_SHORT).show();
                return;
            }

            Note note = new Note();
            note.setDrname("Dr. Zero");
            note.setNote(noteBody);
            note.setDate(timeStamp);

            notes.add(0, note);
            noteAdapter.notifyDataSetChanged();

            dialog.dismiss();
            Toast.makeText(getContext(), "Note successfully added.", Toast.LENGTH_SHORT).show();
        });
    }

    public void showAddVitalsDialog() {
        EditText dateEdtx, tempEdtx, bpSysEdtx, bpDiasEdtx, respRateEdtx, pulseRateEdtx,
                heightEdtx, weightEdtx, painScoreEdtx, o2SatEdtx;
        Button addVitalsBtn;

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.add_vitals_dialog, null);
        dateEdtx = dialogView.findViewById(R.id.vitals_date_edtx);
        tempEdtx = dialogView.findViewById(R.id.vitals_temp_edtx);
        bpSysEdtx = dialogView.findViewById(R.id.vitals_systolic_edtx);
        bpDiasEdtx = dialogView.findViewById(R.id.vitals_diastolic_edtx);
        respRateEdtx = dialogView.findViewById(R.id.vitals_resp_rate_edtx);
        pulseRateEdtx = dialogView.findViewById(R.id.vitals_pulse_rate_edtx);
        heightEdtx = dialogView.findViewById(R.id.vitals_height_edtx);
        weightEdtx = dialogView.findViewById(R.id.vitals_weight_edtx);
        painScoreEdtx = dialogView.findViewById(R.id.vitals_pain_score_edtx);
        o2SatEdtx = dialogView.findViewById(R.id.vitals_o2_edtx);
        addVitalsBtn = dialogView.findViewById(R.id.vitals_add_btn);

        String timeStamp = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        dateEdtx.setText(timeStamp);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();

        addVitalsBtn.setOnClickListener(view -> {
            String date, temp, bpSys, bpDias, respRate, pulseRate, height, weight, painScore, o2Sat;
            date = dateEdtx.getText().toString().trim();
            temp = tempEdtx.getText().toString().trim();
            bpSys = bpSysEdtx.getText().toString().trim();
            bpDias = bpDiasEdtx.getText().toString().trim();
            respRate = respRateEdtx.getText().toString().trim();
            pulseRate = pulseRateEdtx.getText().toString().trim();
            height = heightEdtx.getText().toString().trim();
            weight = weightEdtx.getText().toString().trim();
            painScore = painScoreEdtx.getText().toString().trim();
            o2Sat = o2SatEdtx.getText().toString().trim();

            if (temp.isEmpty()) {
                tempEdtx.setError("Required");
                tempEdtx.requestFocus();
            } else if (bpSys.isEmpty()) {
                bpSysEdtx.setError("Required");
                bpSysEdtx.requestFocus();
            } else if (bpDias.isEmpty()) {
                bpDiasEdtx.setError("Required");
                bpDiasEdtx.requestFocus();
            } else if (respRate.isEmpty()) {
                respRateEdtx.setError("Required");
                respRateEdtx.requestFocus();
            } else if (pulseRate.isEmpty()) {
                pulseRateEdtx.setError("Required");
                pulseRateEdtx.requestFocus();
            } else if (height.isEmpty()) {
                heightEdtx.setError("Required");
                heightEdtx.requestFocus();
            } else if (weight.isEmpty()) {
                weightEdtx.setError("Required");
                weightEdtx.requestFocus();
            } else if (painScore.isEmpty()) {
                painScoreEdtx.setError("Required");
                painScoreEdtx.requestFocus();
            } else if (o2Sat.isEmpty()) {
                o2SatEdtx.setError("Required");
                o2SatEdtx.requestFocus();
            }

            try {
                JSONObject newVitals = new JSONObject();
                newVitals.accumulate("date", date);
                newVitals.accumulate("temp", temp);
                newVitals.accumulate("bp_sys", bpSys);
                newVitals.accumulate("bp_dias", bpDias);
                newVitals.accumulate("resp_rate", respRate);
                newVitals.accumulate("pulse_rate", pulseRate);
                newVitals.accumulate("height", height);
                newVitals.accumulate("weight", weight);

                float bmi = Float.parseFloat(weight) / Float.parseFloat(height) / Float.parseFloat(height) * 10000;

                newVitals.accumulate("bmi", String.valueOf(bmi).substring(0,2));
                newVitals.accumulate("pain_score", painScore);
                newVitals.accumulate("o2_sat", o2Sat);

                vsAdapter = new VSAdapter(getContext(), newVitals);
                vitalSignPager.setAdapter(vsAdapter);

                dialog.dismiss();
                Toast.makeText(getContext(), "Vital signs successfully added.", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void showAddProcedureDialog(){
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.add_procedure_dialog, null);
        dateEdtx = dialogView.findViewById(R.id.procedure_date_edtx);

        String timeStamp = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        dateEdtx.setText(timeStamp);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();

        dateEdtx.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    PatientDetailFragment.this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dpd.show(getActivity().getSupportFragmentManager(), "");
        });
    }

    public void adjustGridHeight() {
        ListAdapter listAdapter = resultGv.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, resultGv);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        if (items > 3) {
            rows = items / 3;
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = resultGv.getLayoutParams();
        params.height = totalHeight;
        resultGv.setLayoutParams(params);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = String.format(Locale.getDefault(),"%02d", (monthOfYear + 1)) +
                "/" + String.format(Locale.getDefault(),"%02d", dayOfMonth) +
                "/" + year;

        dateEdtx.setText(date);
    }
}
