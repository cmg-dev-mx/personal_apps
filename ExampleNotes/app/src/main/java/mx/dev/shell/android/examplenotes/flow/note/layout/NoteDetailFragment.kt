package mx.dev.shell.android.examplenotes.flow.note.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.examplenotes.R
import mx.dev.shell.android.examplenotes.databinding.FragmentNoteDetailBinding
import mx.dev.shell.android.examplenotes.flow.note.vm.NoteDetailViewModel
import mx.dev.shell.android.examplenotes.flow.note.vm.NoteDetailViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: NoteDetailViewModelFactory

    private val args: NoteDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentNoteDetailBinding
    private lateinit var viewModel: NoteDetailViewModel

    private lateinit var note: NoteBo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)

        setupView()
        setupViewModel()
        setupObservers()

        viewModel.queryNote(args.noteId)

        return binding.root
    }

    private fun setupView() {
        binding.apply {
            noteDetailSaveBtn.setOnClickListener {
                note.title = noteDetailTitleTil.editText?.text.toString()
                note.content = noteDetailContentTil.editText?.text.toString()
                note.creationDate = System.currentTimeMillis()
                viewModel.saveNote(note)
            }

            noteDetailDeleteBtn.setOnClickListener {
                Snackbar.make(noteDetailContainer, "In development", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(NoteDetailViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.note.observe(viewLifecycleOwner) {
            note = it
            binding.apply {
                noteDetailTitleTil.editText?.setText(it.title)
                noteDetailContentTil.editText?.setText(it.content)
                if (it.id != 0) {
                    noteDetailSaveBtn.text = getString(R.string.noteDetail_update_button)
                }
            }
        }
        viewModel.showProgressBar.observe(viewLifecycleOwner) {
            binding.noteDetailProgressbar.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(binding.noteDetailContainer, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.noteSaved.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().onBackPressed()
            }
        }
    }
}