package mx.dev.shell.android.core.usecase

import mx.dev.shell.android.core.repository.NotesRepository
import javax.inject.Inject

class NotesUseCaseImpl @Inject constructor(
    private val repository: NotesRepository
) : NotesUseCase {

    override suspend fun loadNotes() = repository.loadNotes()
}