package com.rpo.mobile.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast

class Utils {
    fun showToast(msg: String, ctx: Context) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
    }

    fun Any.shorttoast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    fun isEmailValid(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    //Check password with minimum requirement here(it should be minimum 6 characters)
    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }
}