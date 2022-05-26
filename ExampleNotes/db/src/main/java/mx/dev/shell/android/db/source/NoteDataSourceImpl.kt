package mx.dev.shell.android.db.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mx.dev.shell.android.db.dao.NoteDao
import mx.dev.shell.android.db.model.NoteDo
import javax.inject.Inject

class NoteDataSourceImpl @Inject constructor(
    private val dao: NoteDao
) : NoteDataSource {

    override suspend fun queryNotes(): Flow<Result<List<NoteDo>>> = try {
        val notes = dao.getAllNotes()
        flow { emit(Result.success(notes)) }
    } catch (e: Exception) {
        flow { emit(Result.failure(e)) }
    }

    override suspend fun queryNote(noteId: Int): Flow<Result<NoteDo>> {
        TODO("Not yet implemented")
    }
}