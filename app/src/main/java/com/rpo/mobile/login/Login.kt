package com.rpo.mobile.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.onDrawableClickListener
import com.rpo.mobile.R
import com.rpo.mobile.utils.Utils
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {
    val TAG = Login::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        password.setDrawableClickListener(object : onDrawableClickListener {
            override fun onClick(target: DrawablePosition) {
                when (target) {
                    DrawablePosition.RIGHT -> {
                        Utils().showToast("Question Mark clicked !!", this@Login)
                    }
                }
            }

        })
        btn_user_login.setOnClickListener { performLogin() }
    }

    private fun performLogin() {
        if (validate(username.text.toString(), password.text.toString())) {
            /* error_message.text = "Username or Password is incorrect,\n" +
                     "please try again."
             error_message.visibility = View.VISIBLE
             Log.d(TAG, "${error_message.text.toString()}: ")*/
            error_message.visibility = View.GONE
            Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show()
        }
    }


    private fun validate(email: String, password: String): Boolean {

        // Reset errors.

        if (email.isEmpty() && password.isEmpty()) {
            error_message.text = "Username or Password is incorrect,\n" +
                    "please try again."
            error_message.visibility = View.VISIBLE
            return false
        } else if (email.isEmpty()) {
            error_message.text = "Email is required"
            error_message.visibility = View.VISIBLE
            return false
        } else if (!Utils().isEmailValid(username.text.toString())) {
            error_message.text = "Enter a valid email"
            error_message.visibility = View.VISIBLE
            return false
        }

        if (password.isEmpty()) {
            error_message.text = "Password is required"
            error_message.visibility = View.VISIBLE
            return false
        } else if (!Utils().isPasswordValid(password)) {
            error_message.text = "Password must contain at least 6 characters"
            error_message.visibility = View.VISIBLE
            return false
        }

        return true
    }

}
