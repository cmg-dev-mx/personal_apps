package mx.dev.shell.android.examplenotes.flow.note.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.core.usecase.NoteDetailUseCase
import javax.inject.Inject

class NoteDetailViewModel @Inject constructor(
    private val useCase: NoteDetailUseCase
): ViewModel() {

    val note = MutableLiveData<NoteBo>()
    val errorMessage = MutableLiveData<String>()
    val showProgressBar = MutableLiveData<Boolean>()
    val noteSaved = MutableLiveData<Boolean>()

    fun queryNote(noteId: Int) {
        viewModelScope.launch {
            showProgressBar.postValue(true)
            useCase.queryNote(noteId)
                .onEach {
                    showProgressBar.postValue(false)
                }.collect {
                if (it.isSuccess) {
                    note.postValue(it.getOrNull()?:NoteBo())
                } else {
                    errorMessage.postValue(it.exceptionOrNull()?.message?:"")
                }
            }
        }
    }

    fun saveNote(note: NoteBo) {
        viewModelScope.launch {
            showProgressBar.postValue(true)
            useCase.saveNote(note)
                .onEach {
                    showProgressBar.postValue(false)
                }.collect {
                    if (it.isSuccess) {
                        noteSaved.postValue(true)
                    } else {
                        errorMessage.postValue(it.exceptionOrNull()?.message?:"")
                    }
                }
        }
    }
}