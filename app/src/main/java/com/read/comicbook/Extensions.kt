package com.read.comicbook

import android.content.Context
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.read.comicbook.db.ComicBookDatabase

fun Context.getDb(): ComicBookDatabase? {
    return (applicationContext as MyApp).getDb()
}

fun View.showSnackBar(msg: String){
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
}

fun EditText.data(): String{
    return text?.toString()?:""
}