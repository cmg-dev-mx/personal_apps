package mx.dev.shell.android.repository.implementation

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

    override suspend fun loadNotes() =
        source.queryNotes().map {
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

    override suspend fun queryNote(noteId: Int) =
        source.queryNote(noteId).map {
            if (it.isSuccess) {
                try {
                    Result.success(noteMapper.fromDoToBo(it.getOrNull() ?: NoteDo(0L, "", 0, "")))
                } catch (e: Exception) {
                    Result.failure(e)
                }
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }

    override suspend fun saveNote(newNote: NoteBo) =
        try {
            val noteDo = noteMapper.fromBoToDo(newNote)
            source.saveNote(noteDo)
                .map {
                    if (it.isSuccess) {
                        Result.success(it.getOrNull()?.id ?: 0)
                    } else {
                        Result.failure(it.exceptionOrNull()!!)
                    }
                }
        } catch (e: Exception) {
            flow { emit(Result.failure(e)) }
        }
}