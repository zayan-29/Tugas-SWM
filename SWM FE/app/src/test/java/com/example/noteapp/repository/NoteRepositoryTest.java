package com.example.noteapp.repository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.example.noteapp.model.Note;
import com.example.noteapp.network.ApiService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteRepositoryTest {
    @Mock
    ApiService mockApiService;

    private NoteRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        repository = new NoteRepositoryImpl();

        // Inject mock ApiService ke repository
        java.lang.reflect.Field field = NoteRepositoryImpl.class.getDeclaredField("apiService");
        field.setAccessible(true);
        field.set(repository, mockApiService);
    }

    @Test
    public void getNotes_success() {
        // Data dummy
        List<Note> mockNotes = Arrays.asList(
                new Note("Title1", "Content1"),
                new Note("Title2", "Content2")
        );

        Call<List<Note>> call = mock(Call.class);
        when(mockApiService.getAllNotes()).thenReturn(call);

        doAnswer(invocation -> {
            Callback<List<Note>> callback = invocation.getArgument(0);
            callback.onResponse(call, Response.success(mockNotes));
            return null;
        }).when(call).enqueue(any());

        repository.getNotes(new NoteRepository.RepositoryCallback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> data) {
                assertEquals(2, data.size());
                assertEquals("Title1", data.get(0).getTitle());
            }

            @Override
            public void onError(String message) {
                fail("Seharusnya sukses: " + message);
            }
        });
    }
}