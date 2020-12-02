package me.fayaz07.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
    }
}