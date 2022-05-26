package mx.dev.shell.android.repository.mapper

import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.db.model.NoteDo
import javax.inject.Inject

class NotesMapper @Inject constructor(
    private val mapper: NoteMapper
) : Function1<List<NoteDo>, List<NoteBo>> {

    override fun invoke(p1: List<NoteDo>) = p1.map {
        mapper.invoke(it)
    }
}
