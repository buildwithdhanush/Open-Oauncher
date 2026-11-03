package org.opendeck.launcher.onboarding

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.core.content.IntentCompat
import org.opendeck.launcher.R
import org.opendeck.launcher.setup.SetupActivity

class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_layout)

        findViewById<View>(R.id.btn_skip).setOnClickListener {
            finish()
        }

        findViewById<View>(R.id.btn_choose_home).setOnClickListener {
            val intent = IntentCompat.makeBaseIntent(
                android.content.Intent(this, SetupActivity::class.java)
            )
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}