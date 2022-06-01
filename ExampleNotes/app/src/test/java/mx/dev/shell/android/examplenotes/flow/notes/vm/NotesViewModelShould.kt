package mx.dev.shell.android.examplenotes.flow.notes.vm

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.core.usecase.NotesUseCase
import mx.dev.shell.android.examplenotes.utils.BaseUnitTest
import mx.dev.shell.android.examplenotes.utils.captureValues
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class NotesViewModelShould: BaseUnitTest() {

    private val useCase = mock(NotesUseCase::class.java)
    private val expectedNotes = mock(List::class.java) as List<NoteBo>
    private val expectedException = RuntimeException("Something went wrong")

    @Test
    fun loadNotesFromUseCase() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loadNotes()
        verify(useCase, times(1)).loadNotes()
    }

    @Test
    fun emmitNotesFromUseCase() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.notes.captureValues {
            viewModel.loadNotes()
            assertEquals(expectedNotes, values.first())
        }
    }

    @Test
    fun emmitErrorFromUseCase() = runTest {
        val viewModel = mockFailureCase()
        viewModel.error.captureValues {
            viewModel.loadNotes()
            assertEquals(expectedException.message, values.first())
        }
    }

    @Test
    fun showProgressbarWhenLoadNotes() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.showProgressbar.captureValues {
            viewModel.loadNotes()
            assertEquals(true, values.first())
        }
    }

    @Test
    fun hideProgressbarWhenNotesLoaded() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.showProgressbar.captureValues {
            viewModel.loadNotes()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun showEmptyMessageWhenUseCaseEmmitEmpty() = runTest {
        val viewModel = mockEmptyCase()
        viewModel.showEmptyList.captureValues {
            viewModel.loadNotes()
            assertEquals(true, values.first())
        }
    }

    @Test
    fun showListWhenUseCaseEmmitNotEmptyList() = runTest {
        val viewModel = mockNotEmptyCase()
        viewModel.showEmptyList.captureValues {
            viewModel.loadNotes()
            assertEquals(false, values.first())
        }
    }

    private suspend fun mockSuccessfulCase(): NotesViewModel {
        `when`(useCase.loadNotes()).thenReturn(
            flow {
                emit(Result.success(expectedNotes))
            }
        )
        return NotesViewModel(useCase)
    }

    private suspend fun mockFailureCase(): NotesViewModel {
        `when`(useCase.loadNotes()).thenReturn(
            flow {
                emit(Result.failure(expectedException))
            }
        )
        return NotesViewModel(useCase)
    }

    private suspend fun mockEmptyCase(): NotesViewModel {
        `when`(useCase.loadNotes()).thenReturn(
            flow {
                emit(Result.success(listOf()))
            }
        )
        return NotesViewModel(useCase)
    }

    private suspend fun mockNotEmptyCase(): NotesViewModel {
        `when`(useCase.loadNotes()).thenReturn(
            flow {
                emit(Result.success(listOf(NoteBo())))
            }
        )
        return NotesViewModel(useCase)
    }
}