package mx.dev.shell.android.examplenotes.flow.notes.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
        val action = NotesFragmentDirections.actionNotesFragmentToNoteDetailFragment(noteId)
        findNavController().navigate(action)
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

    override fun onResume() {
        super.onResume()
        viewModel.loadNotes()
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
        viewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            notesAdapter.updateNotes(notes)
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.notesFragmentContainer, error, Snackbar.LENGTH_LONG).show()
                viewModel.error.value = null
            }
        }
        viewModel.showProgressbar.observe(viewLifecycleOwner) { show ->
            if (show) {
                binding.notesProgressbar.visibility = View.VISIBLE
            } else {
                binding.notesProgressbar.visibility = View.GONE
            }
        }
        viewModel.showEmptyList.observe(viewLifecycleOwner) { show ->
            if (show) {
                binding.notesEmptyTxt.visibility = View.VISIBLE
                binding.notesNotesRec.visibility = View.GONE
            } else {
                binding.notesEmptyTxt.visibility = View.GONE
                binding.notesNotesRec.visibility = View.VISIBLE
            }
        }
    }
}
