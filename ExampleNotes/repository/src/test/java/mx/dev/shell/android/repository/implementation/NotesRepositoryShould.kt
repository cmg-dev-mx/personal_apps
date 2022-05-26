package mx.dev.shell.android.repository.implementation

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.db.model.NoteDo
import mx.dev.shell.android.db.source.NoteDataSource
import mx.dev.shell.android.repository.mapper.NoteMapper
import mx.dev.shell.android.repository.mapper.NotesMapper
import mx.dev.shell.android.repository.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class NotesRepositoryShould: BaseUnitTest() {

    private val source = mock(NoteDataSource::class.java)
    private val notesMapper = mock(NotesMapper::class.java)
    private val noteMapper = mock(NoteMapper::class.java)

    private val expected = mock(List::class.java) as List<NoteBo>
    private val noteListDo = mock(List::class.java) as List<NoteDo>
    private val errorExpected = RuntimeException("Something went wrong")

    private val noteId = 1
    private val noteDo = mock(NoteDo::class.java)
    private val noteExpected = mock(NoteBo::class.java)

    @Test
    fun loadNotesFromSource() = runTest {
        val repository = mockSuccessfulCase()
        repository.loadNotes()
        verify(source, times(1)).queryNotes()
    }

    @Test
    fun emitMappedNotesFromSource() = runTest {
        val repository = mockSuccessfulCase()
        assertEquals(expected, repository.loadNotes().first().getOrNull())
    }

    @Test
    fun emitErrorFromSource() = runTest {
        val repository = mockFailureCase()
        assertEquals(errorExpected, repository.loadNotes().first().exceptionOrNull())
    }

    @Test
    fun emitErrorFromMapper() = runTest {
        val repository = mockFailureMapperCase()
        assertEquals(errorExpected, repository.loadNotes().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runTest {
        val repository = mockSuccessfulCase()
        repository.loadNotes().first()
        verify(notesMapper, times(1)).invoke(noteListDo)
    }

    @Test
    fun queryNoteDetailFromSource() = runTest {
        val repository = mockSuccessfulCase()
        repository.queryNote(noteId)
        verify(source, times(1)).queryNote(noteId)
    }

    @Test
    fun emitMappedNoteDetailFromSource() = runTest {
        val repository = mockSuccessfulCase()
        assertEquals(noteExpected, repository.queryNote(noteId).first().getOrNull())
    }

    @Test
    fun emitErrorFromDetailMapper() = runTest {
        val repository = mockFailureMapperDetailCase()
        assertEquals(errorExpected, repository.queryNote(noteId).first().exceptionOrNull())
    }

    private suspend fun mockSuccessfulCase(): NotesRepositoryImpl {
        `when`(source.queryNotes()).thenReturn(
            flow {
                emit(Result.success(noteListDo))
            }
        )
        `when`(notesMapper.invoke(noteListDo)).thenReturn(expected)

        `when`(source.queryNote(noteId)).thenReturn(
            flow {
                emit(Result.success(noteDo))
            }
        )
        `when`(noteMapper.invoke(noteDo)).thenReturn(noteExpected)

        return NotesRepositoryImpl(source, notesMapper, noteMapper)
    }

    private suspend fun mockFailureCase(): NotesRepositoryImpl {
        `when`(source.queryNotes()).thenReturn(
            flow {
                emit(Result.failure(errorExpected))
            }
        )

        return NotesRepositoryImpl(source, notesMapper, noteMapper)
    }

    private suspend fun mockFailureMapperCase(): NotesRepositoryImpl {
        `when`(source.queryNotes()).thenReturn(
            flow {
                emit(Result.success(noteListDo))
            }
        )
        `when`(notesMapper.invoke(noteListDo)).thenThrow(errorExpected)

        return NotesRepositoryImpl(source, notesMapper, noteMapper)
    }

    private suspend fun mockFailureMapperDetailCase(): NotesRepositoryImpl {
        `when`(source.queryNote(noteId)).thenReturn(
            flow {
                emit(Result.success(noteDo))
            }
        )
        `when`(noteMapper.invoke(noteDo)).thenThrow(errorExpected)

        return NotesRepositoryImpl(source, notesMapper, noteMapper)
    }
}