package ru.geekbrains.pictureapp

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun onCLickListenerFab(v: View, message: String) =
    Toast.makeText(v.context, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }

fun moveFabToCenter(context: Context?, appBar: BottomAppBar, fab: FloatingActionButton) {
    appBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
    fab.setImageDrawable(context?.let { c -> ContextCompat.getDrawable(c, R.drawable.ic_favourite) })
    fab.setOnClickListener { view -> onCLickListenerFab(view, "Added to favourites") }
    // remove expandable menu from fab
}

fun moveFabToEnd(context: Context?, appBar: BottomAppBar, fab: FloatingActionButton) {
    appBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
    fab.setImageDrawable(context?.let { c -> ContextCompat.getDrawable(c, R.drawable.ic_plus_fab) })
    fab.setOnClickListener { view -> onCLickListenerFab(view, "Expandable menu") }
    // add expandable menu to fab
}