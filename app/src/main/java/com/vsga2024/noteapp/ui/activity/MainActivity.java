package com.vsga2024.noteapp.ui.activity;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.vsga2024.noteapp.adapter.NoteAdapter;
import com.vsga2024.noteapp.data.db.NoteReaderDbHelper;
import com.vsga2024.noteapp.data.model.Note;
import com.vsga2024.noteapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NoteAdapter adapter;
    List<Note> noteList = new ArrayList<Note>();
    NoteReaderDbHelper dbHelper = new NoteReaderDbHelper(this);
    AlertDialog.Builder dialog;

    Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new NoteAdapter(MainActivity.this, noteList);
        binding.noteListView.setAdapter(adapter);
        binding.noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final int idx = noteList.get(i).getId();
                final String title = noteList.get(i).getTitle();
                final String content = noteList.get(i).getContent();

                final CharSequence[] dialogItem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                                intent.putExtra("id", idx);
                                intent.putExtra("title", title);
                                intent.putExtra("content", content);
                                startActivity(intent);
                                break;
                            case 1:
                                dbHelper.deleteNote(idx);
                                noteList.clear();
                                getNotes();
                                break;
                        }
                    }
                }).show();
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra("id", "");
                intent.putExtra("title", "");
                intent.putExtra("content", "");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteList.clear();
        getNotes();
    }

    private void getNotes() {
        ArrayList<HashMap<String, String>> row = dbHelper.getAllNotes();

        for(int i = 0; i < row.size(); i++) {
            int id = Integer.parseInt(row.get(i).get("id"));
            String title = row.get(i).get("title");
            String content = row.get(i).get("content");

            Note note = new Note();

            note.setId(id);
            note.setTitle(title);
            note.setContent(content);

            noteList.add(note);
        }
        adapter.notifyDataSetChanged();
    }


}