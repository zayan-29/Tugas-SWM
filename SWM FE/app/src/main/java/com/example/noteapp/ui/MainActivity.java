package com.example.noteapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.model.Note;
import com.example.noteapp.repository.NoteRepository;
import com.example.noteapp.repository.NoteRepositoryImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(new ArrayList<>(), this::onNoteClicked);
        recyclerView.setAdapter(adapter);

        // Inisialisasi repository
        noteRepository = new NoteRepositoryImpl();

        // Setup FAB
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivity(intent);
        });

        // Load data
        loadNotes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        noteRepository.getNotes(new NoteRepository.RepositoryCallback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> data) {
                runOnUiThread(() -> adapter.setNotes(data));
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void onNoteClicked(Note note) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("note_id", note.getId());
        startActivity(intent);
    }
}