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
