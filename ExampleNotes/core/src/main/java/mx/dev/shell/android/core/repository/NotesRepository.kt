package mx.dev.shell.android.core.repository

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.core.model.NoteBo

interface NotesRepository {
    suspend fun loadNotes(): Flow<Result<List<NoteBo>>>
}
