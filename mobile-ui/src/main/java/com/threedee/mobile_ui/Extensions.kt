package com.threedee.mobile_ui

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun Activity.showSnackbar(
    message: String,
    length: Int = Snackbar.LENGTH_LONG,
    actionString: String = "",
    `listener`: View.OnClickListener? = null,
    view: View? = null
) {
    val snackbar = Snackbar.make(view ?: findViewById(android.R.id.content), message, length)

    if (actionString.isNotEmpty()) {
        snackbar.setAction(actionString, `listener`)
    }
    snackbar.show()
}

/**hides the soft keyboard in activity*/
fun Activity.hideKeyboard() {
    val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    // Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus
    // If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun TextInputEditText.validate(
    message: String,
    validator: (String) -> Boolean,
    parent: TextInputLayout,
    helperMessage: String = ""
) {
    this.afterTextChanged {
        parent.error = if (validator(it)) null else message
    }
    parent.error = if (validator(this.text.toString())) null else message
}

fun String.isValidMobileNumber(): Boolean = this.isNotEmpty() && this.length == 11
fun String.isValidFullName(): Boolean = this.isNotEmpty() && this.length > 3
fun String.isValidConfirmPassword(password: String) = this.isNotEmpty() && this == password
fun String.isValidPassword(): Boolean = this.isNotEmpty() && this.length > 6

fun String.isValidEmail(): Boolean =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
