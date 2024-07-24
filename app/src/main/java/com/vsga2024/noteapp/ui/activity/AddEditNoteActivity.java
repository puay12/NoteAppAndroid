package com.vsga2024.noteapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.vsga2024.noteapp.data.db.NoteReaderDbHelper;
import com.vsga2024.noteapp.databinding.ActivityAddEditNoteBinding;

public class AddEditNoteActivity extends AppCompatActivity {
    ActivityAddEditNoteBinding binding;
    NoteReaderDbHelper dbHelper = new NoteReaderDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int id = getIntent().getIntExtra("id", -1);
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        if (id == -1) {
            binding.addListTitle.setText("Tambah Catatan");
        } else {
            binding.addListTitle.setText("Edit Catatan");
            binding.noteTitleInput.setText(title);
            binding.noteInput.setText(content);
        }

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (id == -1) {
                        save();
                    } else {
                        edit(id);
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    private void save() {
        String title = binding.noteTitleInput.getText().toString();
        String content = binding.noteInput.getText().toString();

        if(checkData(title, content)) {
            boolean result = dbHelper.addNote(title, content);
            if (result) {
                Toast.makeText(this, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddEditNoteActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Terdapat Kesalahan", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Judul dan Konten tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void edit(int id) {
        String title = binding.noteTitleInput.getText().toString();
        String content = binding.noteInput.getText().toString();

        if(checkData(title, content)) {
            boolean result = dbHelper.updateNote(id, title, content);
            if (result) {
                Toast.makeText(this, "Data berhasil diupdate!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddEditNoteActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Terdapat Kesalahan", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Judul dan Konten tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkData(String title, String content) {
        if (title.isEmpty() || content.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}