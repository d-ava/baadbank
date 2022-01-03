package com.example.baadbank.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackbar(txt: String){
    Snackbar.make(this, txt, Snackbar.LENGTH_SHORT).show()
}