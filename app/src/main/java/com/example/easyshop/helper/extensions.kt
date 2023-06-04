package com.example.easyshop.helper

import android.view.View
import android.widget.EditText
import android.widget.Toast

fun EditText.required() {

    this.setOnFocusChangeListener { v, hasFocus ->

        if (!hasFocus) {
             if (this.text.isEmpty()) {
                this.error = "Esta campo es obligatorio"

            } else {
                this.clearFocus()

            }
        }
    }
}

fun MutableList<View>.IsValid(): Boolean {
    var isValido = true;

    this.forEach {
        it.requestFocus()
        it.clearFocus()
        if (it is EditText) {
            if (it.error != null ) isValido = false
        }
    }
    return isValido;
}