package com.android.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static com.android.notes.MainActivity.adapter;
import static com.android.notes.MainActivity.db;

public class EditActivity extends AppCompatActivity {

    EditText editText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = findViewById(R.id.reminderEdit);

        InputMethodManager imm;
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText,InputMethodManager.SHOW_FORCED);

        Intent intent = getIntent();
        String value = intent.getStringExtra("editVal");
        editText.setText(value);
        position = intent.getIntExtra("position",-1);
    }

    @Override
    public void onBackPressed() {
        String newVal = editText.getText().toString();
        NotesDB notesDB = MainActivity.notesList.get(position);
        notesDB.setNote(newVal);
        db.updateNote(notesDB);
        adapter.notifyItemChanged(position);

        super.onBackPressed();
    }
}
