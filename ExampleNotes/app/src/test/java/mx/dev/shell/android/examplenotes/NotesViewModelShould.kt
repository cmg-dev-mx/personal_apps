package mx.dev.shell.android.examplenotes

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import mx.dev.shell.android.core.usecase.NotesUseCase
import mx.dev.shell.android.examplenotes.flow.notes.vm.NotesViewModel
import mx.dev.shell.android.examplenotes.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class NotesViewModelShould: BaseUnitTest() {

    private val useCase = mock(NotesUseCase::class.java)

    @Test
    fun loadNotesFromUseCase() = runTest {
        val viewModel = NotesViewModel(useCase)

        viewModel.loadNotes()
        verify(useCase, times(1)).loadNotes()
    }
}