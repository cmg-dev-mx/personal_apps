package mx.dev.shell.android.repository.implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.core.repository.NotesRepository
import mx.dev.shell.android.db.source.NoteDataSource
import mx.dev.shell.android.repository.mapper.NoteMapper

class NotesRepositoryImpl constructor(
    private val source: NoteDataSource,
    private val mapper: NoteMapper
) : NotesRepository {

    override suspend fun loadNotes(): Flow<Result<List<NoteBo>>> {
        return source.queryNotes().map {
            if (it.isSuccess) {
                try {
                    Result.success(mapper.invoke(it.getOrNull().orEmpty()))
                } catch (e: Exception) {
                    Result.failure(e)
                }
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    }
}