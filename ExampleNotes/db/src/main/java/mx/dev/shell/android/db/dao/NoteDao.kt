package mx.dev.shell.android.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.dev.shell.android.db.model.NoteDo

@Dao
interface NoteDao {

    @Query("SELECT * from note")
    suspend fun getAllNotes(): List<NoteDo>

    @Query("SELECT * from note WHERE id = :noteId")
    suspend fun getNote(noteId: Int): NoteDo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteExpected: NoteDo): Long
}
