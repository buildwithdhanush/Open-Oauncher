package org.opendeck.launcher.datastore

import android.content.Context
import androidx.datastore.core.dataStore
import androidx.datastore.datastore.DataStore
import org.opendeck.launcher.MainApplication

object DataStoreRepository {

    private const val PREFERENCES_FILE = "opendesk_preferences"

    @Volatile
    private var dataStore: DataStore<Preferences>? = null

    val preferencesDataStore: DataStore<Preferences>
        get() = dataStore ?: synchronized(this) {
            dataStore = dataStore(
                context = MainApplication.getContext(),
                fileName = PREFERENCES_FILE,
                defaultValue = Preferences.DefaultInstance,
                serializer = PreferencesSerializer
            )
            dataStore!!
        }

    fun setFirstLaunch(value: Boolean) {
        preferencesDataStore.edit { preferences ->
            preferences.isFirstLaunch = value
        }
    }

    fun isFirstLaunch(): Boolean {
        return preferencesDataStore.value?.isFirstLaunch ?: true
    }

    fun setAppVersionCode(code: Int) {
        preferencesDataStore.edit { preferences ->
            preferences.appVersionCode = code
        }
    }

    fun appVersionCode(): Int {
        return preferencesDataStore.value?.appVersionCode ?: 0
    }

    // App category sorting preference
    fun setAppSortOrder(order: String) {
        preferencesDataStore.edit { preferences ->
            preferences.appSortOrder = order
        }
    }

    fun appSortOrder(): String {
        return preferencesDataStore.value?.appSortOrder ?: "alphabetical"
    }

    // Widget auto-resize preference
    fun setWidgetAutoResize(enabled: Boolean) {
        preferencesDataStore.edit { preferences ->
            preferences.widgetAutoResize = enabled
        }
    }

    fun widgetAutoResize(): Boolean {
        return preferencesDataStore.value?.widgetAutoResize ?: false
    }

    // Theme preference
    fun setSelectedThemeId(themeId: String?) {
        preferencesDataStore.edit { preferences ->
            preferences.selectedThemeId = themeId
        }
    }

    fun selectedThemeId(): String? {
        return preferencesDataStore.value?.selectedThemeId
    }

    // Wallpaper/image URI preference
    fun setWallpaperUri(uri: android.net.Uri?) {
        preferencesDataStore.edit { preferences ->
            preferences.wallpaperUri = uri
        }
    }

    fun wallpaperUri(): android.net.Uri? {
        return preferencesDataStore.value?.wallpaperUri
    }

    // Preferences data class for DataStore
    // M0: Core preferences only; expand as features are added
    data class Preferences(
        val isFirstLaunch: Boolean = true,
        val appVersionCode: Int = 0,
        val appSortOrder: String = "alphabetical",
        val widgetAutoResize: Boolean = false,
        val selectedThemeId: String? = null,
        val wallpaperUri: android.net.Uri? = null
    ) {
        object DefaultInstance : Preferences()
    }
}