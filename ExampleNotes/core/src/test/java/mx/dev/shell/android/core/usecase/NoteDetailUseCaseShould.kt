package mx.dev.shell.android.core.usecase

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.core.repository.NotesRepository
import mx.dev.shell.android.core.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class NoteDetailUseCaseShould: BaseUnitTest() {

    private val repository = mock(NotesRepository::class.java)
    private val noteId = 1
    private val expected = mock(NoteBo::class.java)
    private val errorExpected = RuntimeException("Something went wrong!")

    @Test
    fun queryNoteByIdFromRepository() = runTest {
        val uc = NoteDetailUseCaseImpl(repository)
        uc.queryNote(noteId)
        verify(repository, times(1)).queryNote(noteId)
    }

    @Test
    fun emitNoteByIdFromRepository() = runTest {
        val uc = mockSuccessfulCase()
        assertEquals(expected, uc.queryNote(noteId).first().getOrNull()!!)
    }

    @Test
    fun emitErrorFromRepository() = runTest {
        val uc = mockFailureCase()
        assertEquals(errorExpected, uc.queryNote(noteId).first().exceptionOrNull()!!)
    }

    @Test
    fun saveNewNoteFromRepository() = runTest {
        val uc = mockSuccessfulCase()
        uc.saveNote(expected)
        verify(repository, times(1)).saveNote(expected)
    }

    @Test
    fun emitNoteIdFromRepositoryWhenSaveNewNote() = runTest {
        val uc = mockSuccessfulCase()
        assertEquals(noteId, uc.saveNote(expected).first().getOrNull()!!)
    }

    @Test
    fun emitErrorWhenSaveNewNoteFromRepository() = runTest {
        val uc = mockFailureCase()
        assertEquals(errorExpected, uc.saveNote(expected).first().exceptionOrNull()!!)
    }

    private suspend fun mockSuccessfulCase(): NoteDetailUseCaseImpl {
        `when`(repository.queryNote(noteId)).thenReturn(
            flow { emit(Result.success(expected)) }
        )
        `when`(repository.saveNote(expected)).thenReturn(
            flow { emit(Result.success(noteId)) }
        )
        return NoteDetailUseCaseImpl(repository)
    }

    private suspend fun mockFailureCase(): NoteDetailUseCaseImpl {
        `when`(repository.queryNote(noteId)).thenReturn(
            flow { emit(Result.failure(errorExpected)) }
        )
        `when`(repository.saveNote(expected)).thenReturn(
            flow { emit(Result.failure(errorExpected)) }
        )
        return NoteDetailUseCaseImpl(repository)
    }
}