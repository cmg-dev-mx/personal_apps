package mx.dev.shell.android.examplenotes.flow.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.examplenotes.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter(
    private val list: ArrayList<NoteBo>,
    private val itemSelectedListener: (Int) -> Unit
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(list[position], itemSelectedListener)

    override fun getItemCount() = list.size

    fun updateNotes(newList: List<NoteBo>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(noteBo: NoteBo, itemSelectedListener: (Int) -> Unit) {
            binding.itemNoteTitle.text = noteBo.title
            binding.itemNoteDescription.text = noteBo.content
            binding.itemNoteCreationDate.text = noteBo.creationDate?.toFormattedDate()
            binding.itemNoteContainer.setOnClickListener {
                itemSelectedListener(noteBo.id?:0)
            }
        }

    }
}

fun Long.toFormattedDate(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(this)
}