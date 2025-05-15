package it.dtech.buzzbrief

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay of 3 seconds before navigating to the main screen
        Handler(Looper.getMainLooper()).postDelayed({
            // Start the main activity
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // Close the splash activity
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}
