package mx.dev.shell.android.db.base

import androidx.room.Database
import androidx.room.RoomDatabase
import mx.dev.shell.android.db.dao.NoteDao
import mx.dev.shell.android.db.model.NoteDo

@Database(
    entities = [
        NoteDo::class
    ],
    version = NoteDataBase.DATABASE_VERSION,
    exportSchema = false
)
abstract class NoteDataBase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "note_db"
        const val DATABASE_VERSION = 1
    }
}