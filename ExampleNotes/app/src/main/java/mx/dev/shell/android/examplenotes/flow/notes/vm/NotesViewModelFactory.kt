package mx.dev.shell.android.examplenotes.flow.notes.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.dev.shell.android.core.usecase.NotesUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory @Inject constructor(
    private val notesUseCase: NotesUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesViewModel(notesUseCase) as T
    }
}