package ru.geekbrains.pictureapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geekbrains.pictureapp.ui.pod.PODFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container_main_activity, PODFragment.newInstance())
                    .commitNow()
        }
    }
}