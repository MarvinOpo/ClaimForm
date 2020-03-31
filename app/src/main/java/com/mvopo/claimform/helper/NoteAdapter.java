package com.mvopo.claimform.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mvopo.claimform.R;
import com.mvopo.claimform.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private int[] colors;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.notes = notes;

        colors = context.getResources().getIntArray(R.array.color_backgrounds);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        NoteViewHolder holder = new NoteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);

        holder.doctorTv.setText(note.getDrname());
        holder.noteTv.setText(note.getNote());
        holder.dateTv.setText(note.getDate());

        holder.noteContainerCl.setBackgroundColor(colors[position % colors.length]);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView doctorTv, noteTv, dateTv;
        ConstraintLayout noteContainerCl;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorTv = itemView.findViewById(R.id.note_dr_tv);
            noteTv = itemView.findViewById(R.id.note_body_tv);
            dateTv = itemView.findViewById(R.id.note_date_tv);

            noteContainerCl = itemView.findViewById(R.id.note_container);
        }
    }
}
