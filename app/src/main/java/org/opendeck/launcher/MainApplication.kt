package org.opendeck.launcher

import android.app.Application
import android.content.Context
import org.opendeck.launcher.datastore.DataStoreRepository

class MainApplication : Application() {

    @Volatile
    private var appContext: Context? = null

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        // Initialize DataStore repository
        DataStoreRepository.preferencesDataStore
        DataStoreRepository.setFirstLaunch(true)
    }

    fun getContext(): Context {
        return appContext ?: applicationContext
    }
}