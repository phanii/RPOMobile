package com.rpo.mobile

import android.app.Application
import android.content.Context
import com.pixplicity.easyprefs.library.Prefs


class RPOApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Prefs.Builder().setContext(this)
                .setMode(Context.MODE_PRIVATE)
                .setPrefsName(packageName)
                .setUseDefaultSharedPreference(true)
                .build()

    }


}