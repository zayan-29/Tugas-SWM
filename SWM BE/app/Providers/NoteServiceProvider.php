<?php
namespace App\Providers;

use Illuminate\Support\ServiceProvider;
use App\Repositories\NoteRepositoryInterface;
use App\Repositories\NoteRepository;
use App\Services\NoteServiceInterface;
use App\Services\NoteService;

class NoteServiceProvider extends ServiceProvider
{
    public function register(): void
    {
        $this->app->bind(NoteRepositoryInterface::class, NoteRepository::class);
        $this->app->bind(NoteServiceInterface::class, NoteService::class);
    }

    public function boot(): void
    {
        //
    }
}