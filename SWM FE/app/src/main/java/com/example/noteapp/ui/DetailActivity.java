package com.example.noteapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noteapp.R;
import com.example.noteapp.model.Note;
import com.example.noteapp.repository.NoteRepository;
import com.example.noteapp.repository.NoteRepositoryImpl;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DetailActivity extends AppCompatActivity {
    private TextView textTitle, textContent;
    private Button btnEdit, btnDelete;
    private NoteRepository noteRepository;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Inisialisasi views
        textTitle = findViewById(R.id.textDetailTitle);
        textContent = findViewById(R.id.textDetailContent);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        noteRepository = new NoteRepositoryImpl();

        // Ambil note_id dari intent
        noteId = getIntent().getIntExtra("note_id", -1);
        if (noteId == -1) {
            Toast.makeText(this, "ID catatan tidak valid", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load detail
        loadNoteDetail();

        // Tombol edit
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, AddEditActivity.class);
            intent.putExtra("note_id", noteId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Tombol hapus dengan dialog konfirmasi
        btnDelete.setOnClickListener(v -> showDeleteConfirmation());
    }

    private void loadNoteDetail() {
        noteRepository.getNote(noteId, new NoteRepository.RepositoryCallback<Note>() {
            @Override
            public void onSuccess(Note data) {
                runOnUiThread(() -> {
                    textTitle.setText(data.getTitle());
                    if (data.getContent() != null && !data.getContent().isEmpty()) {
                        textContent.setText(data.getContent());
                    } else {
                        textContent.setText("Tidak ada isi catatan");
                    }
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(DetailActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        });
    }

    private void showDeleteConfirmation() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("🗑️ Hapus Catatan");
        builder.setMessage("Yakin ingin menghapus catatan ini?\nTindakan ini tidak dapat dibatalkan.");
        builder.setPositiveButton("Ya, Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteNote();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Styling tombol dialog
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
    }

    private void deleteNote() {
        noteRepository.deleteNote(noteId, new NoteRepository.RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                runOnUiThread(() -> {
                    Toast.makeText(DetailActivity.this, "✅ Catatan berhasil dihapus!", Toast.LENGTH_SHORT).show();
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() ->
                        Toast.makeText(DetailActivity.this, "❌ " + message, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}