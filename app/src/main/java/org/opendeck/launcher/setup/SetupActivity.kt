package org.opendeck.launcher.setup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.opendeck.launcher.R

class SetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setup_layout)
    }
}