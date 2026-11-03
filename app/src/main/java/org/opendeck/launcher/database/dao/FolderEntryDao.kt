package org.opendeck.launcher.database.dao

import org.opendeck.launcher.database.FolderEntry
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.List

@Dao
interface FolderEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: FolderEntry)

    @Query("SELECT * FROM folder_entries WHERE folderId = :folderId ORDER BY orderIndex")
    suspend fun getByFolderId(folderId: Int): List<FolderEntry>

    @Query("SELECT * FROM folder_entries WHERE id = :id")
    suspend fun getById(id: Int): FolderEntry?

    @Query("DELETE FROM folder_entries WHERE folderId = :folderId")
    suspend fun deleteByFolderId(folderId: Int)

    @Query("DELETE FROM folder_entries")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg entries: FolderEntry)
}