package mx.dev.shell.android.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteDo(
    @ColumnInfo(name = "creation_date")
    var creationDate: Long,
    @ColumnInfo(name = "description")
    var description: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String
)
