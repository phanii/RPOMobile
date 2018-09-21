package com.rpo.mobile.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.onDrawableClickListener
import com.rpo.mobile.R
import com.rpo.mobile.utils.Utils
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

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
    }


}
