package com.rpo.mobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.rpo.mobile.login.Login
import com.rpo.mobile.utils.Connectivity
import com.rpo.mobile.utils.Utils
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            try {
                if (Connectivity().isConnected(this@SplashScreen)) {
                    val i = Intent(this@SplashScreen, Login::class.java)
                    startActivity(i)
                    // close this activity
                    finish()
                } else {
                    Utils().showSnackbar(constarint_splash, getString(R.string.nonetwork))
                }
            } catch (e: Exception) {
                Utils().showSnackbar(constarint_splash, getString(R.string.nonetwork))
            }
        }, SPLASH_TIME_OUT.toLong())


    }


    private val awesomeOnClickListener = View.OnClickListener { retryButtonClicked() }

    private fun retryButtonClicked() {
        Toast.makeText(this@SplashScreen, "Retry button clicked... ", Toast.LENGTH_SHORT).show()
    }

}
