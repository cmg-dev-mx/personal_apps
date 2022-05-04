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
class NotesUseCaseShould: BaseUnitTest() {

    private val repository = mock(NotesRepository::class.java)
    private val expected = mock(List::class.java) as List<NoteBo>
    private val errorExpected = RuntimeException("Something went wrong")

    @Test
    fun loadNotesFromRepository() = runTest {
        val useCase = NotesUseCaseImpl(repository)
        useCase.loadNotes()
        verify(repository, times(1)).loadNotes()
    }

    @Test
    fun emitNotesFromRepository() = runTest {
        val useCase = mockSuccesfulCase()
        assertEquals(expected, useCase.loadNotes().first().getOrNull()!!)
    }

    @Test
    fun emitErrorFromRepository() = runTest {
        val useCase = mockFailureCase()
        assertEquals(errorExpected, useCase.loadNotes().first().exceptionOrNull()!!)
    }

    private suspend fun mockFailureCase(): NotesUseCaseImpl {
        `when`(repository.loadNotes()).thenReturn(
            flow { emit(Result.failure(errorExpected)) }
        )
        return NotesUseCaseImpl(repository)
    }

    private suspend fun mockSuccesfulCase(): NotesUseCaseImpl {
        `when`(repository.loadNotes()).thenReturn(
            flow { emit(Result.success(expected)) }
        )
        return NotesUseCaseImpl(repository)
    }
}