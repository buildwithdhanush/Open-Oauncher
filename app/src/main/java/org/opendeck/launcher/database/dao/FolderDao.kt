package org.opendeck.launcher.database.dao

import org.opendeck.launcher.database.Folder
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.List

@Dao
interface FolderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: Folder)

    @Query("SELECT * FROM folders")
    suspend fun getAll(): List<Folder>

    @Query("SELECT * FROM folders WHERE id = :id")
    suspend fun getById(id: Int): Folder?

    @Query("DELETE FROM folders")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg folders: Folder)
}