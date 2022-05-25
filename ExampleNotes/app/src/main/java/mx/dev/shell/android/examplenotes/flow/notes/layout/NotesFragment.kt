package mx.dev.shell.android.examplenotes.flow.notes.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import mx.dev.shell.android.examplenotes.R
import mx.dev.shell.android.examplenotes.flow.notes.vm.NotesViewModel
import mx.dev.shell.android.examplenotes.flow.notes.vm.NotesViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class NotesFragment : Fragment() {

    @Inject
    lateinit var factory: NotesViewModelFactory

    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }
}
