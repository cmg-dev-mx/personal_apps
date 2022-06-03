package mx.dev.shell.android.core.usecase

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.core.model.NoteBo

interface NoteDetailUseCase {
    suspend fun queryNote(noteId: Int): Flow<Result<NoteBo>>
    suspend fun saveNote(note: NoteBo): Flow<Result<Int>>
    suspend fun deleteNote(noteId: Int): Flow<Result<Int>>
}
