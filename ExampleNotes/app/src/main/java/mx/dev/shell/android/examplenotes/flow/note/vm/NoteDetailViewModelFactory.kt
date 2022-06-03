package mx.dev.shell.android.examplenotes.flow.note.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.dev.shell.android.core.usecase.NoteDetailUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class NoteDetailViewModelFactory @Inject constructor(
    private val useCase: NoteDetailUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteDetailViewModel(useCase) as T
    }
}