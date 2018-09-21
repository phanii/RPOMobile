package com.rpo.mobile.utils

import android.content.Context
import android.widget.Toast

class Utils {
    fun showToast(msg: String, ctx: Context) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
    }

    fun Any.shorttoast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

}