package mx.dev.shell.android.core.repository

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.core.model.NoteBo

interface NotesRepository {
    suspend fun loadNotes(): Flow<Result<List<NoteBo>>>
    suspend fun queryNote(noteId: Int): Flow<Result<NoteBo>>
    suspend fun saveNote(newNote: NoteBo): Flow<Result<Int>>
    suspend fun deleteNote(noteId: Int): Flow<Result<Int>>
}
