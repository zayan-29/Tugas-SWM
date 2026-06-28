<?php
namespace Tests\Feature;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;
use App\Models\Note;

class NoteApiTest extends TestCase
{
    use RefreshDatabase;

    public function test_can_list_notes()
    {
        Note::factory()->count(3)->create();

        $response = $this->getJson('/api/notes');

        $response->assertStatus(200)
                 ->assertJsonCount(3);
    }

    public function test_can_create_note()
    {
        $data = ['title' => 'Catatan Baru', 'content' => 'Isi catatan'];

        $response = $this->postJson('/api/notes', $data);

        $response->assertStatus(201)
                 ->assertJsonFragment($data);
        $this->assertDatabaseHas('notes', $data);
    }

    public function test_can_view_note()
    {
        $note = Note::factory()->create();

        $response = $this->getJson("/api/notes/{$note->id}");

        $response->assertStatus(200)
                 ->assertJsonFragment(['title' => $note->title]);
    }

    public function test_can_update_note()
    {
        $note = Note::factory()->create();
        $update = ['title' => 'Judul Diperbarui'];

        $response = $this->putJson("/api/notes/{$note->id}", $update);

        $response->assertStatus(200)
                 ->assertJsonFragment($update);
        $this->assertDatabaseHas('notes', $update);
    }

    public function test_can_delete_note()
    {
        $note = Note::factory()->create();

        $response = $this->deleteJson("/api/notes/{$note->id}");

        $response->assertStatus(204);
        $this->assertDatabaseMissing('notes', ['id' => $note->id]);
    }
}