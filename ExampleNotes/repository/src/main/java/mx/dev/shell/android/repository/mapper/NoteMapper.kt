package mx.dev.shell.android.repository.mapper

import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.db.model.NoteDo

class NoteMapper: Function1<List<NoteDo>, List<NoteBo>> {

    override fun invoke(p1: List<NoteDo>): List<NoteBo> {
        return p1.map {
            NoteBo(
                id = it.id,
                creationDate = it.creationDate,
                title = it.title,
                content = it.description
            )
        }
    }
}
