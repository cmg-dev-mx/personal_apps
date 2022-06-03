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

    @Test
    fun insertNoteFromSource() = runTest {
        val source = mockSuccessfulCase()
        source.saveNote(noteExpected)
        verify(dao, times(1)).insertNote(noteExpected)
    }

    @Test
    fun emitNoteFromSourceInSaveNote() = runTest {
        val source = mockSuccessfulCase()
        assertEquals(noteExpected, source.saveNote(noteExpected).first().getOrNull()!!)
    }

    @Test
    fun emitErrorFromSourceInSaveNote() = runTest {
        val source = mockFailureCase()
        assertEquals(errorExpected, source.saveNote(noteExpected).first().exceptionOrNull()!!)
    }

    @Test
    fun deleteNoteFromSource() = runTest {
        val source = mockSuccessfulCase()
        source.deleteNote(noteId)
        verify(dao, times(1)).deleteNote(noteId)
    }

    @Test
    fun emitNoteIdFromSourceInDeleteNote() = runTest {
        val source = mockSuccessfulCase()
        assertEquals(noteId, source.deleteNote(noteId).first().getOrNull()!!)
    }

    @Test
    fun emitErrorFromSourceInDeleteNote() = runTest {
        val source = mockFailureCase()
        assertEquals(errorExpected, source.deleteNote(noteId).first().exceptionOrNull()!!)
    }

    private suspend fun mockSuccessfulCase(): NoteDataSourceImpl {
        `when`(dao.getAllNotes()).thenReturn(expected)
        `when`(dao.getNote(noteId)).thenReturn(noteExpected)
        `when`(dao.insertNote(noteExpected)).thenReturn(0)
        `when`(dao.deleteNote(noteId)).thenReturn(noteId)
        return NoteDataSourceImpl(dao)
    }

    private suspend fun mockFailureCase(): NoteDataSourceImpl {
        `when`(dao.getAllNotes()).thenThrow(errorExpected)
        `when`(dao.getNote(noteId)).thenThrow(errorExpected)
        `when`(dao.insertNote(noteExpected)).thenThrow(errorExpected)
        `when`(dao.deleteNote(noteId)).thenThrow(errorExpected)
        return NoteDataSourceImpl(dao)
    }
}