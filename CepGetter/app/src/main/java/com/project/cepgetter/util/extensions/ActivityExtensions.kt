package com.project.cepgetter.util.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.shortToast(message: String) =
    runOnUiThread { Toast.makeText(this, message,  Toast.LENGTH_SHORT).show() }

fun AppCompatActivity.longToast(message: String) =
    runOnUiThread { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }


fun AppCompatActivity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}
