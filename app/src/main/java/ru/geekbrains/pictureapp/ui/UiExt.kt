package ru.geekbrains.pictureapp.ui

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


fun Context.toast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
    setGravity(Gravity.BOTTOM, 0, 250)
    show()
}

fun Context.toast(message: Int) = Toast.makeText(this, this.getString(message), Toast.LENGTH_SHORT).apply {
    setGravity(Gravity.BOTTOM, 0, 250)
    show()
}


/* wandering between Fragments */
inline fun <reified F: Fragment> createFragment(context: Context?, bundle: Bundle?): Fragment =
        F::class.java.newInstance().apply { arguments = bundle }

inline fun <reified F: Fragment> FragmentManager.addFragment(context: Context, bundle: Bundle, containerRes: Int) =
        beginTransaction()
                .add(containerRes, createFragment<F>(context, bundle))
                .addToBackStack(null)
                .commit()