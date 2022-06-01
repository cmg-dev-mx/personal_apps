package mx.dev.shell.android.repository.mapper

import mx.dev.shell.android.core.model.NoteBo
import mx.dev.shell.android.db.model.NoteDo
import javax.inject.Inject

class NoteMapper @Inject constructor() {

    fun fromDoToBo(p1: NoteDo) = NoteBo(
        p1.id,
        p1.creationDate,
        p1.title,
        p1.description
    )

    fun fromBoToDo(p1: NoteBo) = NoteDo(
        p1.creationDate?:0L,
        p1.content?:"",
        p1.id?:0,
        p1.title?:""
    )

    fun fromListDoToListBo(p1: List<NoteDo>) = p1.map { fromDoToBo(it) }
}