package mx.dev.shell.android.examplenotes.flow.notes.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mx.dev.shell.android.examplenotes.databinding.FragmentNotesBinding
import mx.dev.shell.android.examplenotes.flow.notes.adapter.NotesAdapter
import mx.dev.shell.android.examplenotes.flow.notes.vm.NotesViewModel
import mx.dev.shell.android.examplenotes.flow.notes.vm.NotesViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class NotesFragment : Fragment() {

    @Inject
    lateinit var factory: NotesViewModelFactory

    private lateinit var viewModel: NotesViewModel
    private lateinit var binding: FragmentNotesBinding

    private val notesAdapter = NotesAdapter(arrayListOf()) { noteId ->
        // TODO Not yet implemented
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)

        setupView()
        setupViewModel()
        observeViewModel()

        return binding.root
    }

    private fun setupView() {
        binding.notesNotesRec.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesAdapter
            setHasFixedSize(true)
        }

        binding.notesAddBtn.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToNoteDetailFragment(0)
            findNavController().navigate(action)
        }
    }

    private fun setupViewModel() {

    }

    private fun observeViewModel() {

    }
}
