package org.opendeck.launcher.database.dao

import org.opendeck.launcher.database.WorkspacePage
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.List

@Dao
interface WorkspacePageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(page: WorkspacePage)

    @Query("SELECT * FROM workspace_pages WHERE profileId = :profileId ORDER BY orderIndex")
    suspend fun getByProfileId(profileId: Int): List<WorkspacePage>

    @Query("SELECT * FROM workspace_pages WHERE id = :id")
    suspend fun getById(id: Int): WorkspacePage?

    @Query("DELETE FROM workspace_pages")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pages: WorkspacePage)
}