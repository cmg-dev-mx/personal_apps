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

    @Test
    fun loadNotesFromRepository() = runTest {
        val useCase = NotesUseCaseImpl(repository)
        useCase.loadNotes()
        verify(repository, times(1)).loadNotes()
    }

    @Test
    fun emitNotesFromRepository() = runTest {
        `when`(repository.loadNotes()).thenReturn(
            flow { emit(Result.success(expected)) }
        )
        val useCase = NotesUseCaseImpl(repository)
        assertEquals(expected, useCase.loadNotes().first().getOrNull()!!)
    }
}