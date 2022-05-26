package mx.dev.shell.android.repository.mapper

import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.db.model.NoteDo
import javax.inject.Inject

class NoteMapper @Inject constructor() : Function1<NoteDo, NoteBo> {

    override fun invoke(p1: NoteDo) = NoteBo(
        p1.id,
        p1.creationDate,
        p1.title,
        p1.description
    )
}