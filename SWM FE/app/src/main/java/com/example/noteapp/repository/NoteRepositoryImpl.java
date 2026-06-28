package com.example.noteapp.repository;

import com.example.noteapp.model.Note;
import com.example.noteapp.network.ApiService;
import com.example.noteapp.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteRepositoryImpl implements NoteRepository {
    private ApiService apiService;

    public NoteRepositoryImpl() {
        apiService = RetrofitClient.getService();
    }

    @Override
    public void getNotes(RepositoryCallback<List<Note>> callback) {
        apiService.getAllNotes().enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Gagal mengambil data catatan");
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getNote(int id, RepositoryCallback<Note> callback) {
        apiService.getNoteById(id).enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Catatan tidak ditemukan");
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void addNote(Note note, RepositoryCallback<Note> callback) {
        apiService.createNote(note).enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Gagal menambah catatan");
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void updateNote(int id, Note note, RepositoryCallback<Note> callback) {
        apiService.updateNote(id, note).enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Gagal mengubah catatan");
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void deleteNote(int id, RepositoryCallback<Void> callback) {
        apiService.deleteNote(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Gagal menghapus catatan");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}