package mx.dev.shell.android.examplenotes.flow.note.vm

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.core.usecase.NoteDetailUseCase
import mx.dev.shell.android.examplenotes.utils.BaseUnitTest
import mx.dev.shell.android.examplenotes.utils.captureValues
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class NoteDetailViewModelShould : BaseUnitTest() {

    private val useCase = mock(NoteDetailUseCase::class.java)
    private val noteId = 1
    private val expected = mock(NoteBo::class.java)
    private val exceptionExpected = RuntimeException("Something went wrong!")

    @Test
    fun queryNoteDetailFromUseCase() = runTest {
        val vm = mockSuccessfulCase()
        vm.queryNote(noteId)
        verify(useCase, times(1)).queryNote(noteId)
    }

    @Test
    fun emitNoteFromUseCase() = runTest {
        val vm = mockSuccessfulCase()
        vm.queryNote(noteId)
        vm.note.captureValues {
            assertEquals(expected, values.first())
        }
    }

    @Test
    fun emitErrorFromUseCase() = runTest {
        val vm = mockFailureCase()
        vm.queryNote(noteId)
        vm.errorMessage.captureValues {
            assertEquals(exceptionExpected.message, values.first())
        }
    }

    @Test
    fun showProgressBarWhenQueryNote() = runTest {
        val vm = mockSuccessfulCase()
        vm.showProgressBar.captureValues {
            vm.queryNote(noteId)
            assertEquals(true, values.first())
        }
    }

    @Test
    fun hideProgressBarWhenFinishQueryNote() = runTest {
        val vm = mockSuccessfulCase()
        vm.showProgressBar.captureValues {
            vm.queryNote(noteId)
            assertEquals(false, values.last())
        }
    }

    private suspend fun mockSuccessfulCase(): NoteDetailViewModel {
        `when`(useCase.queryNote(noteId)).thenReturn(
            flow {
                emit(Result.success(expected))
            }
        )
        return NoteDetailViewModel(useCase)
    }

    private suspend fun mockFailureCase(): NoteDetailViewModel {
        `when`(useCase.queryNote(noteId)).thenReturn(
            flow {
                emit(Result.failure(exceptionExpected))
            }
        )
        return NoteDetailViewModel(useCase)
    }
}