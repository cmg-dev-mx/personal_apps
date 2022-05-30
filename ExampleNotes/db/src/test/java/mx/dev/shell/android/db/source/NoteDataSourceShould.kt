package mx.dev.shell.android.db.source

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import mx.dev.shell.android.db.dao.NoteDao
import mx.dev.shell.android.db.model.NoteDo
import mx.dev.shell.android.db.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class NoteDataSourceShould: BaseUnitTest() {

    private val dao = mock(NoteDao::class.java)
    private val expected = mock(List::class.java) as List<NoteDo>
    private val errorExpected = RuntimeException("Something went wrong")

    private val noteId = 1
    private val noteExpected = mock(NoteDo::class.java)

    @Test
    fun queryNotesFromSource() = runTest {
        val source = NoteDataSourceImpl(dao)
        source.queryNotes()
        verify(dao, times(1)).getAllNotes()
    }

    @Test
    fun emitNotesFromDao() = runTest {
        val source = mockSuccessfulCase()
        assertEquals(expected, source.queryNotes().first().getOrNull()!!)
    }

    @Test
    fun emitErrorFromDao() = runTest {
        val source = mockFailureCase()
        assertEquals(errorExpected, source.queryNotes().first().exceptionOrNull()!!)
    }

    @Test
    fun queryNoteFromSource() = runTest {
        val source = NoteDataSourceImpl(dao)
        source.queryNote(noteId)
        verify(dao, times(1)).getNote(noteId)
    }

    @Test
    fun emitNoteFromDao() = runTest {
        val source = mockSuccessfulCase()
        assertEquals(noteExpected, source.queryNote(noteId).first().getOrNull()!!)
    }

    @Test
    fun emitErrorFromDaoInQueryNote() = runTest {
        val source = mockFailureCase()
        assertEquals(errorExpected, source.queryNote(noteId).first().exceptionOrNull()!!)
    }

    private suspend fun mockSuccessfulCase(): NoteDataSourceImpl {
        `when`(dao.getAllNotes()).thenReturn(expected)
        `when`(dao.getNote(noteId)).thenReturn(noteExpected)
        return NoteDataSourceImpl(dao)
    }

    private suspend fun mockFailureCase(): NoteDataSourceImpl {
        `when`(dao.getAllNotes()).thenThrow(errorExpected)
        `when`(dao.getNote(noteId)).thenThrow(errorExpected)
        return NoteDataSourceImpl(dao)
    }
}