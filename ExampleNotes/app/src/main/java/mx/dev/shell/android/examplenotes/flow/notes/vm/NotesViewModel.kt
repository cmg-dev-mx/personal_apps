package mx.dev.shell.android.examplenotes.flow.notes.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.core.usecase.NotesUseCase
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val useCase: NotesUseCase
) : ViewModel() {

    val notes = MutableLiveData<List<NoteBo>>()
    val error = MutableLiveData<String>()
    val showProgressbar = MutableLiveData<Boolean>()
    val showEmptyList = MutableLiveData<Boolean>()

    fun loadNotes() {
        viewModelScope.launch {
            showProgressbar.postValue(true)
            useCase.loadNotes()
                .onEach {
                    showProgressbar.postValue(false)
                }
                .collect {
                    if (it.isSuccess) {
                        notes.postValue(it.getOrNull().orEmpty())
                        showEmptyList.postValue(it.getOrNull().orEmpty().isEmpty())
                    } else {
                        error.postValue(it.exceptionOrNull()?.message ?: "")
                    }
                }
        }
    }
}
