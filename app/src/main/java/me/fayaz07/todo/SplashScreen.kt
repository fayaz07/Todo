package me.fayaz07.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import me.fayaz07.todo.ui.Home
import me.fayaz07.todo.utils.AppConfig

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // hiding appbar
        supportActionBar?.hide()

        // running post handler
        Handler(Looper.getMainLooper()).postDelayed(
            {
                afterSplash()
            },
            AppConfig.SPLASH_DURATION,
        )
    }

    private fun afterSplash() {
        startActivity(Intent(this, Home::class.java))
    }
}