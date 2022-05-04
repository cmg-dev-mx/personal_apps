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
import mx.dev.shell.android.repository.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class NotesRepositoryShould: BaseUnitTest() {

    private val source = mock(NoteDataSource::class.java)
    private val mapper = mock(NoteMapper::class.java)

    private val expected = mock(List::class.java) as List<NoteBo>
    private val noteListDo = mock(List::class.java) as List<NoteDo>
    private val errorExpected = RuntimeException("Something went wrong")

    @Test
    fun loadNotesFromSource() = runTest {
        val repository = NotesRepositoryImpl(source, mapper)
        repository.loadNotes()
        verify(source, times(1)).queryNotes()
    }

    @Test
    fun emitMappedNotesFromSource() = runTest {
        val repository = mockSuccesfulCase()
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
        val repository = mockSuccesfulCase()
        repository.loadNotes().first()
        verify(mapper, times(1)).invoke(noteListDo)
    }

    private suspend fun mockSuccesfulCase(): NotesRepositoryImpl {
        `when`(source.queryNotes()).thenReturn(
            flow {
                emit(Result.success(noteListDo))
            }
        )
        `when`(mapper.invoke(noteListDo)).thenReturn(expected)

        return NotesRepositoryImpl(source, mapper)
    }

    private suspend fun mockFailureCase(): NotesRepositoryImpl {
        `when`(source.queryNotes()).thenReturn(
            flow {
                emit(Result.failure(errorExpected))
            }
        )

        return NotesRepositoryImpl(source, mapper)
    }

    private suspend fun mockFailureMapperCase(): NotesRepositoryImpl {
        `when`(source.queryNotes()).thenReturn(
            flow {
                emit(Result.success(noteListDo))
            }
        )
        `when`(mapper.invoke(noteListDo)).thenThrow(errorExpected)

        return NotesRepositoryImpl(source, mapper)
    }
}