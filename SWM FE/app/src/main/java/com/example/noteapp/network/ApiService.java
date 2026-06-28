package com.example.noteapp.network;

import com.example.noteapp.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("notes")
    Call<List<Note>> getAllNotes();

    @POST("notes")
    Call<Note> createNote(@Body Note note);

    @GET("notes/{id}")
    Call<Note> getNoteById(@Path("id") int id);

    @PUT("notes/{id}")
    Call<Note> updateNote(@Path("id") int id, @Body Note note);

    @DELETE("notes/{id}")
    Call<Void> deleteNote(@Path("id") int id);
}