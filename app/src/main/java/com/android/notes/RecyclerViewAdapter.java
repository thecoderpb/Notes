package com.android.notes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.android.notes.MainActivity.recyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<NotesDB> notesDBList;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView timeStamp, noteTv;
        ImageView deleteReminderImage;
        ImageView editReminderImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            timeStamp = itemView.findViewById(R.id.timeStampTV);
            noteTv = itemView.findViewById(R.id.noteCV);
            deleteReminderImage = itemView.findViewById(R.id.deleteNote);
            editReminderImage = itemView.findViewById(R.id.editNote);
        }
    }

    public RecyclerViewAdapter(Context context, List<NotesDB> notesDBList) {

        this.context = context;
        this.notesDBList = notesDBList;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);

        myViewHolder.deleteReminderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.deleteReminder(view);
            }
        });
        myViewHolder.editReminderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(view);
                String value = notesDBList.get(position).getNote();
                Intent intent = new Intent(context,EditActivity.class);
                intent.putExtra("editVal",value);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });

        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int listPosition) {

        NotesDB notesDB =  notesDBList.get(listPosition);

        holder.timeStamp.setText(formatDate(notesDB.getTimestamp()));
        holder.noteTv.setText(notesDB.getNote());

    }

    @Override
    public int getItemCount() {
        return notesDBList.size(    );
    }

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}
