package ru.geekbrains.pictureapp

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.geekbrains.pictureapp.ui.toast

fun onCLickListenerFab(v: View, message: Int) =
    v.context.toast(message)

fun moveFabToCenter(context: Context?, appBar: BottomAppBar, fab: FloatingActionButton) {
    appBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
    fab.setImageDrawable(context?.let { c -> ContextCompat.getDrawable(c, R.drawable.ic_favourite) })
    fab.setOnClickListener { view -> onCLickListenerFab(view, R.string.temp_added_to_fav) }
    // remove expandable menu from fab
}

fun moveFabToEnd(context: Context?, appBar: BottomAppBar, fab: FloatingActionButton) {
    appBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
    fab.setImageDrawable(context?.let { c -> ContextCompat.getDrawable(c, R.drawable.ic_plus_fab) })
    fab.setOnClickListener { view -> onCLickListenerFab(view, R.string.temp_expandable_menu) }
    // add expandable menu to fab
}