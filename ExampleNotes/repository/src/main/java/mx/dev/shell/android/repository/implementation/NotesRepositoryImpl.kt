package mx.dev.shell.android.repository.implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.core.repository.NotesRepository
import mx.dev.shell.android.db.model.NoteDo
import mx.dev.shell.android.db.source.NoteDataSource
import mx.dev.shell.android.repository.mapper.NoteMapper
import mx.dev.shell.android.repository.mapper.NotesMapper
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val source: NoteDataSource,
    private val notesMapper: NotesMapper,
    private val noteMapper: NoteMapper
) : NotesRepository {

    override suspend fun loadNotes(): Flow<Result<List<NoteBo>>> {
        return source.queryNotes().map {
            if (it.isSuccess) {
                try {
                    Result.success(notesMapper.invoke(it.getOrNull().orEmpty()))
                } catch (e: Exception) {
                    Result.failure(e)
                }
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    }

    override suspend fun queryNote(noteId: Int): Flow<Result<NoteBo>> {
        return source.queryNote(noteId).map {
            if (it.isSuccess) {
                try {
                    Result.success(noteMapper.invoke(it.getOrNull()?: NoteDo()))
                } catch (e: Exception) {
                    Result.failure(e)
                }
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    }
}