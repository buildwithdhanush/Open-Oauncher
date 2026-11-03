package org.opendeck.launcher

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.view.WindowInsetsControllerCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import org.opendeck.launcher.database.AppDatabase
import org.opendeck.launcher.setup.SetupActivity
import org.opendeck.launcher.onboarding.OnboardingActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up window insets for edge-to-edge support: FR-WS-014
        val insetsController = window.insetsController
        if (insetsController != null) {
            insetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_SYSTEM_BARS_BY_DEFAULT
        }

        setContentView(R.layout.activity_main)

        // Initialize Room database: FR-HOME-004, FR-HOME-005
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "opendesk-db"
        ).apply {
            fallbackToDestructiveMigration = false
        }.build()

        // Handle default-Home onboarding: FR-HOME-003
        // If OpenDeck is not yet set as default Home, show onboarding
        checkAndHandleOnboarding(database)

        // Restore last saved workspace: FR-HOME-004
        // Load workspace from Room database
        lifecycleScope.launch {
            // Workspace loading will be implemented in M1
            // For now, just verify database is accessible
            Log.d("MainActivity", "Room database initialized successfully")
        }
    }

    private fun checkAndHandleOnboarding(db: AppDatabase) {
        // FR-HOME-003: First launch must explain how to select OpenDeck as default Home
        // Check if this is the first launch by verifying workspace data exists
        val workspaceCount = db.workspaceDao().countPages()

        if (workspaceCount == 0) {
            // First launch - show onboarding
            // But only if OpenDeck is not already the default Home
            if (!isDefaultHome()) {
                showOnboarding()
            }
        }
    }

    private fun isDefaultHome(): Boolean {
        // Check if OpenDeck is the default Home launcher
        // This requires querying the system's role/DEFAULT_HOME setting
        // Android provides role APIs but they are available only on newer API levels
        try {
            val pkgManager = packageManager
            val homeIntent = android.content.Intent(android.content.Intent.ACTION_MAIN)
            homeIntent.addCategory(android.intent.category.HOME)
            homeIntent.addCategory(android.intent.category.DEFAULT)
            val resolved = pkgManager.resolveActivity(homeIntent, 0)
            return resolved?.packageName == packageName
        } catch (e: Exception) {
            return false
        }
    }

    private fun showOnboarding() {
        // FR-HOME-003: Show first-launch onboarding
        // Explain that OpenDeck is a Home launcher and offer to set as default
        val intent = androidx.core.content.IntentCompat.makeBaseIntent(
            android.content.Intent(this, OnboardingActivity::class.java)
        )
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}