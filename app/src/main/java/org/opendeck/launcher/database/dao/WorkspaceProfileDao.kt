package org.opendeck.launcher.database.dao

import org.opendeck.launcher.database.WorkspaceProfile
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WorkspaceProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: WorkspaceProfile)

    @Query("SELECT * FROM workspace_profiles")
    suspend fun getAll(): List<WorkspaceProfile>

    @Query("SELECT * FROM workspace_profiles WHERE id = :id")
    suspend fun getById(id: Int): WorkspaceProfile?

    @Query("DELETE FROM workspace_profiles")
    suspend fun deleteAll()
}