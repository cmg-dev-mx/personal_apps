package mx.dev.shell.android.examplenotes.flow.notes.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.dev.shell.android.core.usecase.NotesUseCase

class NotesViewModel constructor(
    private val useCase: NotesUseCase
): ViewModel() {

    fun loadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.loadNotes()
        }
    }
}