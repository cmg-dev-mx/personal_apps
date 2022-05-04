package mx.dev.shell.android.db.source

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.db.model.NoteDo

interface NoteDataSource {
    suspend fun queryNotes(): Flow<Result<List<NoteDo>>>

}
