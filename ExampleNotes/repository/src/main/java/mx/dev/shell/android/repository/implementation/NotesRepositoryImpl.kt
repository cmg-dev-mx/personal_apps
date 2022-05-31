package mx.dev.shell.android.repository.implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.core.repository.NotesRepository
import mx.dev.shell.android.db.model.NoteDo
import mx.dev.shell.android.db.source.NoteDataSource
import mx.dev.shell.android.repository.mapper.NoteMapper
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val source: NoteDataSource,
    private val noteMapper: NoteMapper
) : NotesRepository {

    override suspend fun loadNotes(): Flow<Result<List<NoteBo>>> {
        return source.queryNotes().map {
            if (it.isSuccess) {
                try {
                    Result.success(noteMapper.fromListDoToListBo(it.getOrNull().orEmpty()))
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
                    Result.success(noteMapper.fromDoToBo(it.getOrNull()?: NoteDo()))
                } catch (e: Exception) {
                    Result.failure(e)
                }
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    }

    override suspend fun saveNote(newNote: NoteBo): Flow<Result<Int>> {
        return try {
            val noteDo = noteMapper.fromBoToDo(newNote)
            source.saveNote(noteDo)
                .map {
                    if (it.isSuccess) {
                        Result.success(it.getOrNull()?.id?:0)
                    } else {
                        Result.failure(it.exceptionOrNull()!!)
                    }
                }
        } catch (e: Exception) {
            flow { emit(Result.failure(e)) }
        }
    }
}