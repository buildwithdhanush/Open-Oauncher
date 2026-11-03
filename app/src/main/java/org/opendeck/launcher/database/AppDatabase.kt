package org.opendeck.launcher.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.migration.Migration
import androidx.sqlite.db.SupportDatabase
import java.lang.reflect.Field

// M0: Core entities for workspace persistence
// No migrations at initial schema (fallbackToDestructiveMigration = false if needed)

@Database(entities = [
    WorkspaceProfile::class,
    WorkspacePage::class,
    WorkspaceItem::class,
    Folder::class,
    FolderEntry::class
], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workspaceProfileDao(): WorkspaceProfileDao
    abstract fun workspacePageDao(): WorkspacePageDao
    abstract fun workspaceItemDao(): WorkspaceItemDao
    abstract fun folderDao(): FolderDao
    abstract fun folderEntryDao(): FolderEntryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Initialize on first access; use lazy holder pattern
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "opendesk-db"
                ) {
                    fallbackToDestructiveMigration = false
                }.build()
                INSTANCE = database
                database
            }
        }
    }
}