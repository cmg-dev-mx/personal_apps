package mx.dev.shell.android.core.usecase

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.core.model.NoteBo

interface NotesUseCase {
    suspend fun loadNotes(): Flow<Result<List<NoteBo>>>
}