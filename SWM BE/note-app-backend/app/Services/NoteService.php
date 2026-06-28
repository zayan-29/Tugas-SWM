<?php
namespace App\Services;

use App\Repositories\NoteRepositoryInterface;

class NoteService implements NoteServiceInterface
{
    protected $noteRepository;

    public function __construct(NoteRepositoryInterface $noteRepository)
    {
        $this->noteRepository = $noteRepository;
    }

    public function getAllNotes()
    {
        return $this->noteRepository->all();
    }

    public function getNoteById($id)
    {
        return $this->noteRepository->find($id);
    }

    public function createNote(array $data)
    {
        return $this->noteRepository->create($data);
    }

    public function updateNote($id, array $data)
    {
        return $this->noteRepository->update($id, $data);
    }

    public function deleteNote($id)
    {
        return $this->noteRepository->delete($id);
    }
}