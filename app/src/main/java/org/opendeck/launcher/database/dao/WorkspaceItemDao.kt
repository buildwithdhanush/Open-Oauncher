package org.opendeck.launcher.database.dao

import org.opendeck.launcher.database.WorkspaceItem
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.List

@Dao
interface WorkspaceItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: WorkspaceItem)

    @Query("SELECT * FROM workspace_items WHERE pageId = :pageId ORDER BY zIndex DESC")
    suspend fun getByPageId(pageId: Int): List<WorkspaceItem>

    @Query("SELECT * FROM workspace_items WHERE id = :id")
    suspend fun getById(id: Int): WorkspaceItem?

    @Query("DELETE FROM workspace_items WHERE pageId = :pageId")
    suspend fun deleteByPageId(pageId: Int)

    @Query("DELETE FROM workspace_items")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg items: WorkspaceItem)
}