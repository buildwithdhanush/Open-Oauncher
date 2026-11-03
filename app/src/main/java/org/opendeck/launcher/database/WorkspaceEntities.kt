package org.opendeck.launcher.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.Instant

// Entities based on PRD section 5.5 data model

// M0: Core entities for workspace persistence

@Entity(tableName = "workspace_profiles")
data class WorkspaceProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "Default",
    val schemaVersion: String = "1.0.0",
    val createdAt: Long = Instant.now().epochSecond,
    val updatedAt: Long = Instant.now().epochSecond,
    val defaultPageId: Int = -1
) : Parcelable, Serializable

@Entity(tableName = "workspace_pages")
data class WorkspacePage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val profileId: Int = 0,
    val orderIndex: Int = 0,
    val name: String = "Page 1",
    val gridRows: Int = 3,
    val gridColumns: Int = 3,
    val pageStyleJson: String = "{}",
    val transitionId: Int = 0
) : Parcelable, Serializable

@Entity(tableName = "workspace_items")
data class WorkspaceItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pageId: Int = 0,
    val type: String = "app",       // "app", "folder", "widget", "text", "image", "shape", "spacer", "custom_action"
    val xNorm: Float = 0.0f,         // Normalized x coordinate (0.0-1.0)
    val yNorm: Float = 0.0f,         // Normalized y coordinate (0.0-1.0)
    val widthNorm: Float = 1.0f,     // Normalized width (0.0-1.0)
    val heightNorm: Float = 1.0f,    // Normalized height (0.0-1.0)
    val rotation: Float = 0.0f,      // Degrees
    val zIndex: Int = 0,             // Integer z-order
    val locked: Boolean = false,
    val visible: Boolean = true,
    val styleJson: String = "{}",
    val actionId: Int = 0
) : Parcelable, Serializable

@Entity(tableName = "folders")
data class Folder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "Folder",
    val presentationStyleJson: String = "{}",
    val openAnimation: String = "scale"
) : Parcelable, Serializable

@Entity(tableName = "folder_entries")
data class FolderEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val folderId: Int = 0,
    val targetType: String = "app",     // "app", "shortcut"
    val packageName: String? = null,
    val componentName: String? = null,
    val shortcutId: String? = null,
    val orderIndex: Int = 0,
    val visible: Boolean = true
) : Parcelable, Serializable