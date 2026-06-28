package com.example.noteapp.repository;

import com.example.noteapp.model.Note;

import java.util.List;

public interface NoteRepository {
    void getNotes(RepositoryCallback<List<Note>> callback);
    void getNote(int id, RepositoryCallback<Note> callback);
    void addNote(Note note, RepositoryCallback<Note> callback);
    void updateNote(int id, Note note, RepositoryCallback<Note> callback);
    void deleteNote(int id, RepositoryCallback<Void> callback);

    // Interface callback generic
    interface RepositoryCallback<T> {
        void onSuccess(T data);
        void onError(String message);
    }
}