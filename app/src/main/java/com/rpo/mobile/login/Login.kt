package com.rpo.mobile.login

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.onDrawableClickListener
import com.rpo.mobile.R
import com.rpo.mobile.modal.User
import com.rpo.mobile.utils.Utils
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {
    val TAG = Login::class.java.simpleName
    var isPasswordShow: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        password.setDrawableClickListener(object : onDrawableClickListener {
            override fun onClick(target: DrawablePosition) {
                when (target) {
                    DrawablePosition.RIGHT -> {
                        when (isPasswordShow) {
                            true -> {
                                isPasswordShow = false
                                password.transformationMethod = PasswordTransformationMethod.getInstance()
                            }
                            false -> {
                                isPasswordShow = true
                                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                            }
                        }
                    }
                }
            }

        })
        /**
         * User login Button Action Listerner
         */
        btn_user_login.setOnClickListener { performLogin() }
    }

    /**
     * To Perform the login action button .
     */
    private fun performLogin() {
        if (validate(username.text.toString(), password.text.toString())) {
            try {
                error_message.visibility = View.GONE
                Log.d(TAG, "Gson: ${Gson().toJson(User(username.text.toString(), password.text.toString()))}")
                gsonresult.text = Gson().toJson(User(username.text.toString(), password.text.toString()))
                Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show()
                //finish()
                //  startActivity(Intent(this@Login, MainActivity::class.java))
            } catch (e: Exception) {
                Log.d(TAG, "Exception: ${e.stackTrace}")
            }
        }
    }

    /*
    validate method to validate the user input before going to further screens
     */
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

    private fun checkNetworkStatus(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

}
