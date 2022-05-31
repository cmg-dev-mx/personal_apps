package mx.dev.shell.android.core.usecase

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.core.repository.NotesRepository
import javax.inject.Inject

class NoteDetailUseCaseImpl @Inject constructor(
    private val repository: NotesRepository
) : NoteDetailUseCase {

    override suspend fun queryNote(noteId: Int) =
        repository.queryNote(noteId)

    override suspend fun saveNote(note: NoteBo): Flow<Result<Int>> {
        TODO("Not yet implemented")
    }
}