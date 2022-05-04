package mx.dev.shell.android.core.usecase

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.core.repository.NotesRepository

class NotesUseCaseImpl constructor(
    private val repository: NotesRepository
) : NotesUseCase {

    override suspend fun loadNotes(): Flow<Result<List<NoteBo>>> {
        return repository.loadNotes()
    }
}