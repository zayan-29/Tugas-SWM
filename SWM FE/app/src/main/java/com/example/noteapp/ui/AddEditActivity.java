package com.example.noteapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.noteapp.R;
import com.example.noteapp.model.Note;
import com.example.noteapp.repository.NoteRepository;
import com.example.noteapp.repository.NoteRepositoryImpl;

public class AddEditActivity extends AppCompatActivity {
    private EditText editTitle, editContent;
    private Button btnSave;
    private TextView titleHeader;
    private NoteRepository noteRepository;
    private int noteId = -1; // -1 berarti mode tambah baru

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // Inisialisasi views
        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);
        titleHeader = findViewById(R.id.titleHeader);
        noteRepository = new NoteRepositoryImpl();

        // Cek apakah ini mode edit
        Intent intent = getIntent();
        if (intent.hasExtra("note_id")) {
            noteId = intent.getIntExtra("note_id", -1);
            titleHeader.setText("✏️ Edit Catatan");
            loadNoteData(noteId);
        } else {
            titleHeader.setText("✍️ Buat Catatan");
        }

        // Tombol simpan
        btnSave.setOnClickListener(v -> saveNote());
    }

    private void loadNoteData(int id) {
        noteRepository.getNote(id, new NoteRepository.RepositoryCallback<Note>() {
            @Override
            public void onSuccess(Note data) {
                runOnUiThread(() -> {
                    editTitle.setText(data.getTitle());
                    if (data.getContent() != null) {
                        editContent.setText(data.getContent());
                    }
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() ->
                        Toast.makeText(AddEditActivity.this, message, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void saveNote() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();

        // Validasi
        if (title.isEmpty()) {
            editTitle.setError("Judul harus diisi");
            return;
        }

        Note note = new Note(title, content);

        if (noteId == -1) {
            // Tambah baru
            noteRepository.addNote(note, new NoteRepository.RepositoryCallback<Note>() {
                @Override
                public void onSuccess(Note data) {
                    runOnUiThread(() -> {
                        Toast.makeText(AddEditActivity.this, "Catatan tersimpan!", Toast.LENGTH_SHORT).show();
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    });
                }

                @Override
                public void onError(String message) {
                    runOnUiThread(() ->
                            Toast.makeText(AddEditActivity.this, message, Toast.LENGTH_SHORT).show()
                    );
                }
            });
        } else {
            // Edit
            noteRepository.updateNote(noteId, note, new NoteRepository.RepositoryCallback<Note>() {
                @Override
                public void onSuccess(Note data) {
                    runOnUiThread(() -> {
                        Toast.makeText(AddEditActivity.this, "Catatan diperbarui!", Toast.LENGTH_SHORT).show();
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    });
                }

                @Override
                public void onError(String message) {
                    runOnUiThread(() ->
                            Toast.makeText(AddEditActivity.this, message, Toast.LENGTH_SHORT).show()
                    );
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}