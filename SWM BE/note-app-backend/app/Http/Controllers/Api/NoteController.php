<?php
namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Services\NoteServiceInterface;
use Illuminate\Http\Request;

class NoteController extends Controller
{
    protected $noteService;

    public function __construct(NoteServiceInterface $noteService)
    {
        $this->noteService = $noteService;
    }

    public function index()
    {
        return response()->json($this->noteService->getAllNotes());
    }

    public function store(Request $request)
    {
        $validated = $request->validate([
            'title' => 'required|string|max:255',
            'content' => 'nullable|string',
        ]);
        $note = $this->noteService->createNote($validated);
        return response()->json($note, 201);
    }

    public function show($id)
    {
        return response()->json($this->noteService->getNoteById($id));
    }

    public function update(Request $request, $id)
    {
        $validated = $request->validate([
            'title' => 'sometimes|required|string|max:255',
            'content' => 'nullable|string',
        ]);
        $note = $this->noteService->updateNote($id, $validated);
        return response()->json($note);
    }

    public function destroy($id)
    {
        $this->noteService->deleteNote($id);
        return response()->json(null, 204);
    }
}