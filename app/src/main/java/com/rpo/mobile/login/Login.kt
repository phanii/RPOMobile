package com.rpo.mobile.login

import android.content.Context
import android.content.Intent
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
import com.rpo.mobile.MainActivity
import com.rpo.mobile.R
import com.rpo.mobile.modal.User
import com.rpo.mobile.utils.ApiBuilder
import com.rpo.mobile.utils.Utils
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : AppCompatActivity() {
    companion object {
        var apiservice = ApiBuilder.create()
    }

    val TAG = Login::class.java.simpleName
    var isPasswordShow: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        /**
         * password visibility
         */
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
                    else -> {
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
                gsonresulttext.text = Gson().toJson(User(username.text.toString(), password.text.toString()))
                Toast.makeText(this, getString(R.string.success_login), Toast.LENGTH_SHORT).show()
                //finish()
                //  startActivity(Intent(this@Login, MainActivity::class.java))

                //createUser(User(username.text.toString(), password.text.toString()))
                validateUser(User(username.text.toString(), password.text.toString()))
            } catch (e: Exception) {
                Log.d(TAG, "Exception: ${e.stackTrace}")
            }
        }
    }

    /**
     * validate the user with the existing list if the user is validated/existed then open the next screen otherwise display the error messsage
     */
    private fun validateUser(user: User) {
        val call_validateUser = apiservice.getValidateUser()
        call_validateUser.enqueue(object : Callback<List<com.rpo.mobile.modal.user.User>> {
            override fun onFailure(call: Call<List<com.rpo.mobile.modal.user.User>>, t: Throwable) {
                Log.d(TAG, "onFailure: \n ${t.localizedMessage}")
            }

            override fun onResponse(call: Call<List<com.rpo.mobile.modal.user.User>>, response: Response<List<com.rpo.mobile.modal.user.User>>) {
                Log.d(TAG, "onResponse : \n ${response.body()?.get(0)?.username}")
                if (response.isSuccessful) {

                    if (response.body()?.get(0)?.id!! > 0) {
                        Toast.makeText(this@Login, getString(R.string.success_login), Toast.LENGTH_SHORT).show()
                    }
                } else {

                }
            }

        })
        /**
         * close the locin screen and oopen the main activity for further ..
         */
        finish()
        startActivity(Intent(this@Login, MainActivity::class.java))

    }

    private fun createUser(user: User) {
        val call_userCreate = apiservice.toCreateA_User(user)
        call_userCreate.enqueue(object : Callback<com.rpo.mobile.modal.user.User> {
            override fun onFailure(call: Call<com.rpo.mobile.modal.user.User>, t: Throwable) {
                Log.d(TAG, "onFailure: \n ${t.localizedMessage}")

            }

            override fun onResponse(call: Call<com.rpo.mobile.modal.user.User>, response: Response<com.rpo.mobile.modal.user.User>) {
                Log.d(TAG, "onResponse : \n $response")
                if (response.isSuccessful) {
                    if (response.body()?.id!! > 0) {
                        Toast.makeText(this@Login, getString(R.string.success_login), Toast.LENGTH_SHORT).show()
                    }
                } else {

                }
            }

        })
    }

    /**
    validate method to validate the user input before going to further screens
     **/
    private fun validate(email: String, password: String): Boolean {

        // Reset errors.

        if (email.isEmpty() && password.isEmpty()) {
            error_message.text = getString(R.string.logindetails_incorrect)
            error_message.visibility = View.VISIBLE
            return false
        } else if (email.isEmpty()) {
            error_message.text = getString(R.string.required_email)
            error_message.visibility = View.VISIBLE
            return false
        } else if (!Utils().isEmailValid(username.text.toString())) {
            error_message.text = getString(R.string.valid_email)
            error_message.visibility = View.VISIBLE
            return false
        }

        if (password.isEmpty()) {
            error_message.text = getString(R.string.required_password)
            error_message.visibility = View.VISIBLE
            return false
        } else if (!Utils().isPasswordValid(password)) {
            error_message.text = getString(R.string.password_chars_min6)
            error_message.visibility = View.VISIBLE
            return false
        }

        return true
    }

    /**
     * check the network status for every before network call
     */
    private fun checkNetworkStatus(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

}
